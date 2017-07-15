package se.snylt.witch.processor;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.processor.binding.NewInstance;
import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.binding.OnBindPropertySetter;
import se.snylt.witch.processor.binding.OnOnBindGetAdapterView;
import se.snylt.witch.processor.utils.AnnotationUtils;
import se.snylt.witch.processor.utils.ClassUtils;
import se.snylt.witch.processor.utils.FileWriter;
import se.snylt.witch.processor.utils.Logger;
import se.snylt.witch.processor.utils.PropertyUtils;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.ViewBinder;
import se.snylt.witch.processor.viewbinder.getbinder.GetBinderComposition;
import se.snylt.witch.processor.viewbinder.getbinder.GetTargetBinder;
import se.snylt.witch.processor.viewbinder.getvalue.GetTargetValue;
import se.snylt.witch.processor.viewbinder.getvalue.GetTargetValueValue;
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyAlways;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotSame;
import se.snylt.witch.processor.viewbinder.isdirty.IsValueDirty;
import se.snylt.witch.processor.viewbinder.newinstance.NewViewBinderInstance;
import se.snylt.witch.processor.viewbinder.setview.SetViewHolderView;

import static se.snylt.witch.processor.utils.PropertyUtils.bindsValue;

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
        SupportedAnnotations.Binds.name,
        SupportedAnnotations.BindWhen.name
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WitchProcessor extends AbstractProcessor {

    private TypeUtils typeUtils;

    private Elements elementUtils;

    private FileWriter fileWriter;

    private Logger logger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = new TypeUtils(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
        fileWriter = new FileWriter(processingEnv.getFiler());
        logger = new Logger(processingEnv.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<Element, List<ViewBinder.Builder>> viewBinders = new HashMap<>();
        processTargets(viewBinders, roundEnv, SupportedAnnotations.ALL_BIND_VIEW);
        processOnBinds(viewBinders, roundEnv);
        processBindWhens(viewBinders, roundEnv);
        writeFiles(viewBinders);
        return true;
    }

    private void processBindWhens(HashMap<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv) {
        for (Element bindWhen : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindWhen.class)) {
            String bindWhenValue =  bindWhen.getAnnotation(se.snylt.witch.annotations.BindWhen.class).value();
            switch (bindWhenValue) {
                case BindWhen.ALWAYS: {
                    ViewBinder.Builder binder = getOrCreateViewBinder(bindWhen, targetViewBinders);
                    binder.setIsDirty(new IsDirtyAlways(binder.getTargetTypeName()));
                    continue;
                }
                case BindWhen.NOT_EQUALS: {
                    ViewBinder.Builder binder = getOrCreateViewBinder(bindWhen, targetViewBinders);
                    binder.setIsDirty(new IsDirtyIfNotEquals(binder.getTargetTypeName()));
                    continue;
                }
                case BindWhen.NOT_SAME: {
                    ViewBinder.Builder binder = getOrCreateViewBinder(bindWhen, targetViewBinders);
                    binder.setIsDirty(new IsDirtyIfNotSame(binder.getTargetTypeName()));
                    continue;
                }
            }

            throw new WitchException(logger.logError("Illegal value [" + bindWhenValue + "] for @" + BindWhen.class.getSimpleName()));
        }
    }

    private void processTargets(Map<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv,
            SupportedAnnotations.HasViewId... annotations) {

        // Add all targets
        for (SupportedAnnotations.HasViewId hasViewIdAnnotation : annotations) {
            for (Element targetMember : roundEnv.getElementsAnnotatedWith(hasViewIdAnnotation.getClazz())) {
                Element target = targetMember.getEnclosingElement();
                if (!targetViewBinders.containsKey(target)) {
                    targetViewBinders.put(target, new LinkedList<>());
                }
            }
        }

        // Add all view binders in proper order.
        for(Element target: targetViewBinders.keySet()) {
            for(Element targetMember: target.getEnclosedElements()) {
                for(SupportedAnnotations.HasViewId viewIdAnnotation: annotations) {
                    if(targetMember.getAnnotation(viewIdAnnotation.getClazz()) != null) {
                        List<ViewBinder.Builder> viewBinders = targetViewBinders.get(target);
                        if (!viewBinders.contains(targetMember)) {

                            int viewId = viewIdAnnotation.getViewId(targetMember);
                            PropertyAccessor valueAccessor = getPropertyAccessor(targetMember);
                            TypeName viewHolderTypeName = ClassUtils.getBindingViewHolderName(target);
                            TypeName targetTypeName = ClassUtils.getElementClassName(target);
                            TypeName viewTypeName = ClassName.get("android.view", "View");
                            TypeName valueTypeName = typeUtils.getValueType(targetMember);

                            // Defaults
                            ViewBinder.Builder builder = new ViewBinder.Builder(targetTypeName)
                                    .setNewInstance(new NewViewBinderInstance(viewId, viewTypeName, viewHolderTypeName, targetTypeName, valueTypeName))
                                    .setValueAccessor(valueAccessor)
                                    .setGetView(new GetViewHolderView(viewHolderTypeName, valueAccessor))
                                    .setGetValue(new GetTargetValue(targetTypeName, valueAccessor, valueTypeName))
                                    .setSetView(new SetViewHolderView(viewHolderTypeName, valueAccessor))
                                    .setGetBinder(new GetBinderComposition(targetTypeName))
                                    .setIsDirty(new IsDirtyIfNotEquals(targetTypeName));

                            if(typeUtils.isValueContainer(targetMember)) {
                                builder.setIsDirty(new IsValueDirty(targetTypeName, valueAccessor))
                                        .setGetValue(new GetTargetValueValue(targetTypeName, valueAccessor, valueTypeName));
                            }

                            PropertyAccessor binderAccessor;
                            if((binderAccessor = getBinder(roundEnv, targetMember)) != null) {
                                builder.setGetBinder(new GetTargetBinder(targetTypeName, binderAccessor));
                            }

                            viewBinders.add(builder);
                        }
                    }
                }
            }
        }
    }

    private PropertyAccessor getBinder(RoundEnvironment roundEnvironment, Element value) {
        for(Element binder: roundEnvironment.getElementsAnnotatedWith(se.snylt.witch.annotations.Binds.class)) {
            if((bindsValue(value, binder)) && value.getEnclosingElement().equals(binder.getEnclosingElement())) {
                return getPropertyAccessor(binder);
            }
        }
        return null;
    }

    private PropertyAccessor getPropertyAccessor(Element value) {

        PropertyAccessor a = PropertyUtils.getPropertyAccessor(value);

        if(a == null) {
            throw new WitchException(logger.logError("Can't access " + value.getEnclosingElement().getSimpleName() + "." + value.getSimpleName()
                    + ". Make sure value does not have private or protected access."));
        }

        return a;
    }

    private void processOnBinds(HashMap<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {

        // OnBind
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.OnBind.class)) {
            TypeMirror bindMirror = AnnotationUtils.getOnBindTypeMirror(bindAction);
            addOnBind(bindAction, bindMirror, viewBinders);
        }

        // OnBindEach
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.OnBindEach.class)) {
            List<? extends TypeMirror> bindMirrors = AnnotationUtils.getOnBindEachTypeMirrors(bindAction);
            for (TypeMirror bindMirror : bindMirrors) {
                addOnBind(bindAction, bindMirror, viewBinders);
            }
        }

        // BindToView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToView.class).set();
            TypeName viewType = AnnotationUtils.getOnBindToViewClass(bindAction);
            addOnBindProperty(viewBinders, property, viewType, bindAction);
        }

        // BindToTextView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToTextView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToTextView.class).set();
            TypeName viewType = ClassName.get("android.widget", "TextView");
            addOnBindProperty(viewBinders, property, viewType, bindAction);
        }

        // BindToImageView
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.witch.annotations.BindToImageView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToImageView.class).set();
            TypeName viewType = ClassName.get("android.widget", "ImageView");
            addOnBindProperty(viewBinders, property, viewType, bindAction);
        }

        // BindToEditText
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToEditText.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToEditText.class).set();
            TypeName viewType = ClassName.get("android.widget", "EditText");
            addOnBindProperty(viewBinders, property, viewType, bindAction);
        }

        // BindToCompoundButton
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.witch.annotations.BindToCompoundButton.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToCompoundButton.class).set();
            TypeName viewType = ClassName.get("android.widget", "CompoundButton");
            addOnBindProperty(viewBinders, property, viewType, bindAction);
        }

        // BindToRecyclerView
        for (Element bindAction : roundEnv
                .getElementsAnnotatedWith(se.snylt.witch.annotations.BindToRecyclerView.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToRecyclerView.class).set();
            TypeName viewType = ClassName.get("android.support.v7.widget", "RecyclerView");
            TypeName valueType = ClassName.get(typeUtils.getType(bindAction));
            TypeName adapterType = AnnotationUtils.getOnBindToRecyclerViewAdapterClass(bindAction);
            OnBind actionDef = new OnOnBindGetAdapterView(property, viewType, adapterType, valueType);
            addOnBind(bindAction, actionDef, viewBinders);
        }

        // BindToRecyclerView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindToViewPager.class)) {
            String property = bindAction.getAnnotation(se.snylt.witch.annotations.BindToViewPager.class).set();
            TypeName viewType = ClassName.get("android.support.v4.view", "ViewPager");
            TypeName valueType = ClassName.get(typeUtils.getType(bindAction));
            TypeName adapterType = AnnotationUtils.getOnBindToViewPagerAdapterClass(bindAction);
            OnBind actionDef = new OnOnBindGetAdapterView(property, viewType, adapterType, valueType);
            addOnBind(bindAction, actionDef, viewBinders);
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

    private void addOnBind(Element targetMember, TypeMirror bindTypeMirror, HashMap<Element, List<ViewBinder.Builder>> targetViewBinders) {
        boolean match = false;

        TypeName typeName = TypeName.get(bindTypeMirror);
        DeclaredType bindingDeclaredType = typeUtils.types().getDeclaredType(elementUtils.getTypeElement(bindTypeMirror.toString()));
        OnBind onBind = new NewInstance(typeName);

        // OnBind
        TypeMirror onBindTypeMirror = typeUtils.onBindDeclaredType();
        if (typeUtils.types().isSubtype(bindingDeclaredType, onBindTypeMirror)) {
            addOnBind(targetMember, onBind, targetViewBinders);
            match = true;
        }

        if (!match) {
            String stringBuilder = String.valueOf(typeName) +
                    " does not implement required interface. Make sure classes provided in: " +
                    "@" + SupportedAnnotations.OnBind.name + " or " +
                    "@" + SupportedAnnotations.OnBindEach.name +
                    " implements one or more of the following: " +
                    TypeUtils.ON_BIND;

            throw new WitchException(logger.logError(stringBuilder));
        }
    }

    private void addOnBindProperty(HashMap<Element, List<ViewBinder.Builder>> targetViewBinders, String propertyName,
            TypeName viewType, Element targetMember) {
        TypeName valueType = typeUtils.getValueType(targetMember);
        OnBind onBind = new OnBindPropertySetter(propertyName, viewType, valueType);
        addOnBind(targetMember, onBind, targetViewBinders);
    }

    private void addOnBind(Element targetMemeber, OnBind onBind, HashMap<Element, List<ViewBinder.Builder>> targetViewBinders) {
        ViewBinder.Builder viewBinder = getOrCreateViewBinder(targetMemeber, targetViewBinders);
        viewBinder.addOnBind(onBind);
    }

    private ViewBinder.Builder getOrCreateViewBinder(Element targetMember, HashMap<Element, List<ViewBinder.Builder>> targetViewBinders) {
        Element target = targetMember.getEnclosingElement();
        List<ViewBinder.Builder> viewBinders = targetViewBinders.get(target);

        // Add bind actions to view binding
        for (ViewBinder.Builder viewBinder : viewBinders) {
            if (viewBinder.equals(getPropertyAccessor(targetMember))) {
                return viewBinder;
            }
        }

        throw new WitchException(logger.logError(
                "Could not find view defined for: < " + target.getSimpleName() + "." + targetMember.getSimpleName() + " >"
                        + " . Make sure you have used any of the annotations that binds to a view id:"
                        + Arrays.toString(SupportedAnnotations.ALL_BIND_VIEW)));
    }

    private void writeFiles(HashMap<Element, List<ViewBinder.Builder>> binders) {
        for (Element target : binders.keySet()) {
            fileWriter.writeViewHolder(target, binders);
            fileWriter.writeTargetViewBinder(target, binders);
        }
    }
}
