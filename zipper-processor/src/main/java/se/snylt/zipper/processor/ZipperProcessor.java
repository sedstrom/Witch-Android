package se.snylt.zipper.processor;

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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.processor.binding.BindActionDef;
import se.snylt.zipper.processor.binding.NewInstanceDef;
import se.snylt.zipper.processor.binding.OnBindGetAdapterViewDef;
import se.snylt.zipper.processor.binding.OnBindViewDef;
import se.snylt.zipper.processor.binding.ViewBindingDef;
import se.snylt.zipper.processor.java.BinderCreatorJavaHelper;
import se.snylt.zipper.processor.java.ViewHolderJavaHelper;

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

})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ZipperProcessor extends AbstractProcessor {

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

    private void logError(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<Element, List<ViewBindingDef>> targets = new HashMap<>();
        addTarget(targets, roundEnv, SupportedAnnotations.ALL_BIND_VIEW);
        addOnBindActions(targets, roundEnv);
        buildJava(targets);
        return true;
    }


    private void addTarget(Map<Element, List<ViewBindingDef>> targets, RoundEnvironment roundEnv,
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
                    viewActionses.add(new ViewBindingDef(viewId, value));
                }
            }
        }
    }

    private void addOnBindActions(HashMap<Element, List<ViewBindingDef>> binders, RoundEnvironment roundEnv) {

        // OnBind
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.zipper.annotations.OnBind.class)) {
            TypeMirror bindMirror = getOnBindTypeMirror(bindAction);
            addBindTypeMirror(bindAction, bindMirror, binders);
        }

        // OnBindEach
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.zipper.annotations.OnBindEach.class)) {
            List<? extends TypeMirror> bindMirrors = getOnBindEachTypeMirrros(bindAction);
            for (TypeMirror bindMirror : bindMirrors) {
                addBindTypeMirror(bindAction, bindMirror, binders);
            }
        }

        // BindToView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.zipper.annotations.BindToView.class)) {
            String property = bindAction.getAnnotation(se.snylt.zipper.annotations.BindToView.class).set();
            TypeName viewType = getOnBindToViewClass(bindAction);
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToTextView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.zipper.annotations.BindToTextView.class)) {
            String property = bindAction.getAnnotation(se.snylt.zipper.annotations.BindToTextView.class).set();
            TypeName viewType = ClassName.get("android.widget", "TextView");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToImageView
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.zipper.annotations.BindToImageView.class)) {
            String property = bindAction.getAnnotation(se.snylt.zipper.annotations.BindToImageView.class).set();
            TypeName viewType = ClassName.get("android.widget", "ImageView");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToEditText
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.zipper.annotations.BindToEditText.class)) {
            String property = bindAction.getAnnotation(se.snylt.zipper.annotations.BindToEditText.class).set();
            TypeName viewType = ClassName.get("android.widget", "EditText");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToCompoundButton
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.zipper.annotations.BindToCompoundButton.class)) {
            String property = bindAction.getAnnotation(se.snylt.zipper.annotations.BindToCompoundButton.class).set();
            TypeName viewType = ClassName.get("android.widget", "CompoundButton");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToRecyclerView
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.zipper.annotations.BindToRecyclerView.class)) {
            String property = bindAction.getAnnotation(se.snylt.zipper.annotations.BindToRecyclerView.class).set();
            TypeName viewType = ClassName.get("android.support.v7.widget", "RecyclerView");
            TypeName valueType = ClassName.get(bindAction.asType());
            TypeName adapterType = getOnBindToRecyclerViewAdapterClass(bindAction);
            BindActionDef actionDef = new OnBindGetAdapterViewDef(property, viewType, adapterType, valueType);
            addOnBindAction(bindAction, actionDef, binders);
        }

        // BindToRecyclerView
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.zipper.annotations.BindToViewPager.class)) {
            String property = bindAction.getAnnotation(se.snylt.zipper.annotations.BindToViewPager.class).set();
            TypeName viewType = ClassName.get("android.support.v4.view", "ViewPager");
            TypeName valueType = ClassName.get(bindAction.asType());
            TypeName adapterType = getOnBindToViewPagerAdapterClass(bindAction);
            BindActionDef actionDef = new OnBindGetAdapterViewDef(property, viewType, adapterType, valueType);
            addOnBindAction(bindAction, actionDef, binders);
        }

        // TODO
        // BindToAdapterView
        // BindToProgressBar
        // BindToToggleButton
        // BindToCheckedTextView
        // BindToRatingBar
        // BindToTextSwitcher
        // BindToToolBar
        // BindGridLayout
    }

    private void addBindTypeMirror(Element bindAction, TypeMirror bindTypeMirror,
            HashMap<Element, List<ViewBindingDef>> binders) {
        boolean noMatch = true;

        TypeName typeName = TypeName.get(bindTypeMirror);
        BindActionDef actionDef = new NewInstanceDef(typeName);


        // Implements OnPreBind
        String name = TypeUtils.asString(TypeUtils.ON_PRE_BIND_ACTION);
        TypeMirror onPreBind = elementUtils.getTypeElement(name).asType();
        if (typeUtils.isAssignable(bindTypeMirror, onPreBind)) {
            addOnPreBindAction(bindAction, actionDef, binders);
            noMatch = false;
        }

        // Implements OnBind
        name = TypeUtils.asString(TypeUtils.ON_BIND_ACTION);
        TypeMirror onBind = elementUtils.getTypeElement(name).asType();
        if (typeUtils.isAssignable(bindTypeMirror, onBind)) {
            addOnBindAction(bindAction, actionDef, binders);
            noMatch = false;
        }

        // Implements OnPostBind
        name = TypeUtils.asString(TypeUtils.ON_POST_BIND_ACTION);
        TypeMirror onPostBind = elementUtils.getTypeElement(name).asType();
        if (typeUtils.isAssignable(bindTypeMirror, onPostBind)) {
            addOnPostBindAction(bindAction, actionDef, binders);
            noMatch = false;
        }

        if(noMatch) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(typeName + " does not implement required interface. Make sure classes provided in: ")
                    .append("@").append(SupportedAnnotations.OnBind.name).append(" or ")
                    .append("@").append(SupportedAnnotations.OnBindEach.name)
                    .append(" implements one or more of the following: ")
                    .append(TypeUtils.ON_PRE_BIND_ACTION).append(", ").append(TypeUtils.ON_BIND_ACTION).append(" or ")
                    .append(TypeUtils.ON_POST_BIND_ACTION);

            logError(stringBuilder.toString());
        }

    }

    private void addOnBindViewDef(HashMap<Element, List<ViewBindingDef>> binders, String property, TypeName viewType,
            Element bindAction) {
        TypeName valueType = ClassName.get(bindAction.asType());
        BindActionDef actionDef = new OnBindViewDef(property, viewType, valueType);
        addOnBindAction(bindAction, actionDef, binders);
    }

    private void addOnBindAction(Element bindAction, BindActionDef bindActionDef,
            HashMap<Element, List<ViewBindingDef>> binders) {
        ViewBindingDef viewBindingDef = getViewBindingDef(bindAction, binders);
        viewBindingDef.addOnBindAction(bindActionDef);
    }

    private void addOnPreBindAction(Element bindAction, BindActionDef bindActionDef,
            HashMap<Element, List<ViewBindingDef>> binders) {
        ViewBindingDef viewBindingDef = getViewBindingDef(bindAction, binders);
        viewBindingDef.addOnPreBindAction(bindActionDef);
    }

    private void addOnPostBindAction(Element bindAction, BindActionDef bindActionDef,
            HashMap<Element, List<ViewBindingDef>> binders) {
        ViewBindingDef viewBindingDef = getViewBindingDef(bindAction, binders);
        viewBindingDef.addOnPostBindAction(bindActionDef);
    }

    private ViewBindingDef getViewBindingDef(Element bindAction, HashMap<Element, List<ViewBindingDef>> binders) {
        Element target = bindAction.getEnclosingElement();
        List<ViewBindingDef> viewActionses = binders.get(target);

        // Add bind actions to view binding
        for (ViewBindingDef viewBindingDef : viewActionses) {
            if (viewBindingDef.equals(bindAction)) {
                return viewBindingDef;
            }
        }

        logError("Could not find view defined for: < " + target.getSimpleName() + "." + bindAction.getSimpleName() +" >"
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
        ClassName targetClassName = getTargetClassName(target);
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
        String packageName = ClassUtils.getBindingPackage(target);
        return ClassName.get(packageName, className);
    }

    private ClassName getBindingClassName(Element target) {
        String className = ClassUtils.getBindingName(target);
        String packageName = ClassUtils.getBindingPackage(target);
        return ClassName.get(packageName, className);
    }

    private ClassName getTargetClassName(Element target) {
        String className = ClassUtils.getTargetName(target);
        String packageName = ClassUtils.getBindingPackage(target);
        return ClassName.get(packageName, className);
    }

    private TypeName getOnBindToRecyclerViewAdapterClass(Element bindAction) {
        TypeMirror bindClass = null;
        try {
            bindAction.getAnnotation(se.snylt.zipper.annotations.BindToRecyclerView.class).adapter();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }

    private TypeName getOnBindToViewPagerAdapterClass(Element bindAction) {
        TypeMirror bindClass = null;
        try {
            bindAction.getAnnotation(se.snylt.zipper.annotations.BindToViewPager.class).adapter();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }


    private TypeMirror getOnBindTypeMirror(Element action) {
        TypeMirror bindClass = null;
        try {
            action.getAnnotation(se.snylt.zipper.annotations.OnBind.class).value();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return bindClass;
    }

    private List<? extends TypeMirror> getOnBindEachTypeMirrros(Element action) {
        List<? extends TypeMirror> bindClasses = null;
        try {
            action.getAnnotation(se.snylt.zipper.annotations.OnBindEach.class).value();
        } catch (MirroredTypesException mte) {
            bindClasses = mte.getTypeMirrors();
        }
        return bindClasses;
    }

    private TypeName getOnBindToViewClass(Element action) {
        TypeMirror bindClass = null;
        try {
            action.getAnnotation(se.snylt.zipper.annotations.BindToView.class).view();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }
}
