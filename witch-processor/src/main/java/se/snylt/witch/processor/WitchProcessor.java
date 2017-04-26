package se.snylt.witch.processor;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import se.snylt.witch.processor.binding.NewInstanceDef;
import se.snylt.witch.processor.binding.OnBindDef;
import se.snylt.witch.processor.binding.OnOnBindGetAdapterViewDef;
import se.snylt.witch.processor.binding.OnOnBindViewDef;
import se.snylt.witch.processor.binding.ValueBinderOnBindDef;
import se.snylt.witch.processor.binding.ViewBindingDef;
import se.snylt.witch.processor.java.BinderCreatorJavaHelper;
import se.snylt.witch.processor.java.ViewHolderJavaHelper;
import se.snylt.witch.processor.valueaccessor.FieldAccessor;
import se.snylt.witch.processor.valueaccessor.MethodAccessor;
import se.snylt.witch.processor.valueaccessor.ValueAccessor;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
        SupportedAnnotations.BindTo.name,
        SupportedAnnotations.BindToView.name,
        SupportedAnnotations.BindToTextView.name,
        SupportedAnnotations.BindToEditText.name,
        SupportedAnnotations.BindToCompoundButton.name,
        SupportedAnnotations.BindToImageView.name,
        SupportedAnnotations.BindToRecyclerView.name,
        SupportedAnnotations.BindToViewPager.name,
        SupportedAnnotations.OnBind.name,
        SupportedAnnotations.OnBindEach.name,
        SupportedAnnotations.Mod.name,
        SupportedAnnotations.AlwaysBind.name
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WitchProcessor extends AbstractProcessor {

    private Types typeUtils;

    private Elements elementUtils;

    private Filer filer;

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    private void logNote(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void logManWarn(String message) {
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, message);
    }

    private void logWarn(String message) {
        messager.printMessage(Diagnostic.Kind.WARNING, message);
    }

    private void logAndThrowError(String message) throws WitchException {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
        throw new WitchException(message);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<Element, List<ViewBindingDef>> binders = new HashMap<>();

        addBindingTargets(binders, roundEnv, SupportedAnnotations.ALL_BIND_VIEW);
        addOnBindActions(binders, roundEnv);
        addAlwaysBind(binders, roundEnv);

        // Generate java
        buildJava(binders);

        return true;
    }

    private void addAlwaysBind(HashMap<Element, List<ViewBindingDef>> binders, RoundEnvironment roundEnv) {
        // OnBind
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.AlwaysBind.class)) {
            getViewBindingDef(bindAction, binders).setAlwaysBind(true);
        }
    }

    private void addBindingTargets(Map<Element, List<ViewBindingDef>> targets, RoundEnvironment roundEnv,
            SupportedAnnotations.HasViewId... annotations) {
        for (SupportedAnnotations.HasViewId hasViewIdAnnotation : annotations) {
            for (Element value : roundEnv.getElementsAnnotatedWith(hasViewIdAnnotation.getClazz())) {
                Element target = value.getEnclosingElement();

                // Prepare target bindings
                if (!targets.containsKey(target)) {
                    targets.put(target, new LinkedList<>());
                }

                // Add view id and field to be bound
                List<ViewBindingDef> viewActionses = targets.get(target);
                if (!viewActionses.contains(value)) {
                    int viewId = hasViewIdAnnotation.getViewId(value);
                    ValueAccessor valueAccessor = getValueAccessor(value);
                    viewActionses.add(new ViewBindingDef(viewId, valueAccessor));
                }
            }
        }
    }

    private ValueAccessor getValueAccessor(Element value) {

        if (value.getKind().isField() && notPrivateOrProtected(value)) {
            return new FieldAccessor(value.getSimpleName().toString());
        }

        if (value.getKind() == ElementKind.METHOD && notPrivateOrProtected(value)) {
            return new MethodAccessor(value.getSimpleName().toString());
        }

        logAndThrowError("Can't access " + value.getEnclosingElement().getSimpleName() + "." + value.getSimpleName()
                + ". Make sure value does not have private or protected access.");

        return null;
    }

    private TypeMirror getType(Element value) {
        TypeMirror valueType;
        if(value.getKind() == ElementKind.METHOD) {
            ExecutableElement executableElement = (ExecutableElement) value;
            valueType = executableElement.getReturnType();
        } else {
            valueType = value.asType();
        }
        return valueType;
    }

    private boolean isValueBinder(Element value) {
        TypeMirror valueBinder = TypeUtils.typeMirror(typeUtils, elementUtils, TypeUtils.VALUE_BINDER);
        return typeUtils.isAssignable(getType(value), valueBinder);
    }

    private boolean notPrivateOrProtected(Element e) {
        Set<Modifier> modifiers = e.getModifiers();
        return !modifiers.contains(Modifier.PRIVATE) || !modifiers.contains(Modifier.PROTECTED);
    }

    private void addOnBindActions(HashMap<Element, List<ViewBindingDef>> binders, RoundEnvironment roundEnv) {

        // BindTo with ValueBinder
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindTo.class)) {
            if(isValueBinder(bindAction)) {
                ViewBindingDef viewBindingDef = getViewBindingDef(bindAction, binders);
                TypeMirror elementType = getType(bindAction);
                DeclaredType dt = (DeclaredType) elementType;
                List<? extends TypeMirror> viewAndValueType = dt.getTypeArguments();
                TypeMirror viewType = viewAndValueType.get(0);
                TypeMirror valueType = viewAndValueType.get(1);

                TypeMirror valueBinderType =
                        typeUtils.getDeclaredType(
                                elementUtils.getTypeElement(TypeUtils.VALUE_BINDER.toString()), viewType, valueType);

                TypeMirror binderType =
                        typeUtils.getDeclaredType(
                                elementUtils.getTypeElement(TypeUtils.BINDER.toString()), viewType, valueType);

                ValueBinderOnBindDef valueBindingOnBind = new ValueBinderOnBindDef(
                        TypeName.get(viewType),
                        TypeName.get(valueType),
                        TypeName.get(binderType),
                        TypeName.get(valueBinderType));

                viewBindingDef.addOnBind(valueBindingOnBind);
            }
        }

        // OnBind
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.OnBind.class)) {
            TypeMirror bindMirror = getOnBindTypeMirror(bindAction);
            addBindTypeMirror(bindAction, bindMirror, binders);
        }

        // OnBindEach
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.OnBindEach.class)) {
            List<? extends TypeMirror> bindMirrors = getOnBindEachTypeMirrors(bindAction);
            for (TypeMirror bindMirror : bindMirrors) {
                addBindTypeMirror(bindAction, bindMirror, binders);
            }
        }

        // BindToView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToView.class).set();
            TypeName viewType = getOnBindToViewClass(bindAction);
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToTextView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToTextView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToTextView.class).set();
            TypeName viewType = ClassName.get("android.widget", "TextView");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToImageView
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.witch.annotations.BindToImageView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToImageView.class).set();
            TypeName viewType = ClassName.get("android.widget", "ImageView");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToEditText
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToEditText.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToEditText.class).set();
            TypeName viewType = ClassName.get("android.widget", "EditText");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToCompoundButton
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.witch.annotations.BindToCompoundButton.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToCompoundButton.class).set();
            TypeName viewType = ClassName.get("android.widget", "CompoundButton");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToRecyclerView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToRecyclerView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToRecyclerView.class).set();
            TypeName viewType = ClassName.get("android.support.v7.widget", "RecyclerView");
            TypeName valueType = ClassName.get(getType(bindAction));
            TypeName adapterType = getOnBindToRecyclerViewAdapterClass(bindAction);
            OnBindDef actionDef = new OnOnBindGetAdapterViewDef(property, viewType, adapterType, valueType);
            addOnBindAction(bindAction, actionDef, binders);
        }

        // BindToRecyclerView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToViewPager.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToViewPager.class).set();
            TypeName viewType = ClassName.get("android.support.v4.view", "ViewPager");
            TypeName valueType = ClassName.get(getType(bindAction));
            TypeName adapterType = getOnBindToViewPagerAdapterClass(bindAction);
            OnBindDef actionDef = new OnOnBindGetAdapterViewDef(property, viewType, adapterType, valueType);
            addOnBindAction(bindAction, actionDef, binders);
        }

        // TODO
        // BindToToolBar
        // BindToAdapterView
        // BindToProgressBar
        // BindToToggleButton
        // BindToCheckedTextView
        // BindToRatingBar
        // BindToTextSwitcher
        // BindGridLayout
    }

    private void addBindTypeMirror(Element bindAction, TypeMirror bindTypeMirror,
            HashMap<Element, List<ViewBindingDef>> binders) {
        boolean match = false;

        TypeName typeName = TypeName.get(bindTypeMirror);
        DeclaredType bindingDeclaredType = typeUtils.getDeclaredType(elementUtils.getTypeElement(bindTypeMirror.toString()));
        OnBindDef actionDef = new NewInstanceDef(typeName);

        // OnBind
        TypeMirror onBind = TypeUtils.onBindDeclaredType(typeUtils, elementUtils);
        if (typeUtils.isSubtype(bindingDeclaredType, onBind)) {
            addOnBindAction(bindAction, actionDef, binders);
            match = true;
        }

        if (!match) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(typeName + " does not implement required interface. Make sure classes provided in: ")
                    .append("@").append(SupportedAnnotations.OnBind.name).append(" or ")
                    .append("@").append(SupportedAnnotations.OnBindEach.name)
                    .append(" implements one or more of the following: ")
                    .append(TypeUtils.ON_BIND);

            logAndThrowError(stringBuilder.toString());
        }
    }

    private void addOnBindViewDef(HashMap<Element, List<ViewBindingDef>> binders, String property, TypeName viewType,
            Element bindAction) {

        TypeName valueType = getValueType(bindAction);
        OnBindDef actionDef = new OnOnBindViewDef(property, viewType, valueType);
        addOnBindAction(bindAction, actionDef, binders);
    }

    private TypeName getValueType(Element bindAction) {
        if (bindAction.getKind().isField()) {
            return ClassName.get(bindAction.asType());
        } else if (bindAction.getKind() == ElementKind.METHOD) {
            ExecutableType type = (ExecutableType) bindAction.asType();
            return ClassName.get(type.getReturnType());
        }
        logAndThrowError("Could not get value type for: " + bindAction.getSimpleName());
        return null;
    }

    private void addOnBindAction(Element bindAction, OnBindDef onBindDef, HashMap<Element, List<ViewBindingDef>> binders) {
        ViewBindingDef viewViewBindingDef = getViewBindingDef(bindAction, binders);
        viewViewBindingDef.addOnBind(onBindDef);
    }

    private ViewBindingDef getViewBindingDef(Element bindAction, HashMap<Element, List<ViewBindingDef>> binders) {
        Element target = bindAction.getEnclosingElement();
        List<ViewBindingDef> viewActions = binders.get(target);

        // Add bind actions to view binding
        for (ViewBindingDef viewViewBindingDef : viewActions) {
            if (viewViewBindingDef.equals(getValueAccessor(bindAction))) {
                return viewViewBindingDef;
            }
        }

        logAndThrowError(
                "Could not find view defined for: < " + target.getSimpleName() + "." + bindAction.getSimpleName() + " >"
                        + " . Make sure you have used any of the annotations that binds to a view id:"
                        + Arrays.toString(SupportedAnnotations.ALL_BIND_VIEW));
        return null;
    }

    private void buildJava(HashMap<Element, List<ViewBindingDef>> binders) {
        for (Element target : binders.keySet()) {
            createViewHolder(target, binders);
            createBindingSpec(target, binders);
        }
    }

    private void createBindingSpec(Element target, HashMap<Element, List<ViewBindingDef>> binders) {
        ClassName viewHolderClassName = getBindingViewHolderName(target);
        ClassName bindingClassName = getBindingClassName(target);
        ClassName targetClassName = getElementClassName(target);
        TypeSpec bindingTypeSpec = BinderCreatorJavaHelper
                .toJava(targetClassName, binders.get(target), bindingClassName, viewHolderClassName);
        JavaFile bindingJavaFile = JavaFile.builder(bindingClassName.packageName(), bindingTypeSpec).build();
        try {
            bindingJavaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createViewHolder(Element target, HashMap<Element, List<ViewBindingDef>> binders) {
        ClassName viewHolderClassName = getBindingViewHolderName(target);
        TypeSpec viewHolderTypeSpec = ViewHolderJavaHelper.toJava(binders.get(target), viewHolderClassName);
        JavaFile viewHolderJavaFile = JavaFile.builder(viewHolderClassName.packageName(), viewHolderTypeSpec).build();
        try {
            viewHolderJavaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ClassName getBindingViewHolderName(Element target) {
        String className = ClassUtils.getViewHolderName(target);
        String packageName = ClassUtils.getElementPackage(target);
        return ClassName.get(packageName, className);
    }

    private ClassName getBindingClassName(Element target) {
        String className = ClassUtils.getBinderName(target);
        String packageName = ClassUtils.getElementPackage(target);
        return ClassName.get(packageName, className);
    }

    private ClassName getElementClassName(Element target) {
        String className = ClassUtils.getTargetName(target);
        String packageName = ClassUtils.getElementPackage(target);
        return ClassName.get(packageName, className);
    }

    private TypeName getOnBindToRecyclerViewAdapterClass(Element bindAction) {
        TypeMirror bindClass = null;
        try {
            bindAction.getAnnotation(se.snylt.witch.annotations.BindToRecyclerView.class).adapter();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }

    private TypeName getOnBindToViewPagerAdapterClass(Element bindAction) {
        TypeMirror bindClass = null;
        try {
            bindAction.getAnnotation(se.snylt.witch.annotations.BindToViewPager.class).adapter();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }


    private TypeMirror getOnBindTypeMirror(Element action) {
        TypeMirror bindClass = null;
        try {
            action.getAnnotation(se.snylt.witch.annotations.OnBind.class).value();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return bindClass;
    }

    private List<? extends TypeMirror> getOnBindEachTypeMirrors(Element action) {
        List<? extends TypeMirror> bindClasses = null;
        try {
            action.getAnnotation(se.snylt.witch.annotations.OnBindEach.class).value();
        } catch (MirroredTypesException mte) {
            bindClasses = mte.getTypeMirrors();
        }
        return bindClasses;
    }

    private TypeName getOnBindToViewClass(Element action) {
        TypeMirror bindClass = null;
        try {
            action.getAnnotation(se.snylt.witch.annotations.BindToView.class).view();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }
}
