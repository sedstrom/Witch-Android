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
import javax.lang.model.util.Elements;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.binding.OnBindPropertySetter;
import se.snylt.witch.processor.utils.AnnotationUtils;
import se.snylt.witch.processor.utils.ClassUtils;
import se.snylt.witch.processor.utils.FileWriter;
import se.snylt.witch.processor.utils.Logger;
import se.snylt.witch.processor.utils.PropertyUtils;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.ViewBinder;
import se.snylt.witch.processor.viewbinder.getbinder.GetBinderComposition;
import se.snylt.witch.processor.viewbinder.getbinder.GetMethodBinder;
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
        SupportedAnnotations.Data.name,
        SupportedAnnotations.BindData.name,
        SupportedAnnotations.Bind.name,
        SupportedAnnotations.BindWhen.name
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WitchProcessor extends AbstractProcessor {

    private TypeUtils typeUtils;

    private FileWriter fileWriter;

    private Logger logger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = new TypeUtils(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
        fileWriter = new FileWriter(processingEnv.getFiler());
        logger = new Logger(processingEnv.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<Element, List<ViewBinder.Builder>> viewBinders = new HashMap<>();
        collectAllTargets(viewBinders, roundEnv, SupportedAnnotations.ALL_BIND_VIEW);
        processBindData(viewBinders, roundEnv);
        processBind(viewBinders, roundEnv);
        processData(viewBinders, roundEnv);
        processBindWhens(viewBinders, roundEnv);
        writeFiles(viewBinders);
        return true;
    }

    private void collectAllTargets(Map<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv,
                                   SupportedAnnotations.HasViewId... annotations) {

        // Add all targets
        for (SupportedAnnotations.HasViewId hasViewIdAnnotation : annotations) {
            for (Element targetMember: roundEnv.getElementsAnnotatedWith(hasViewIdAnnotation.getClazz())) {
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
                        if (!hasViewBinderForProperty(targetMember, targetViewBinders)) {

                            int viewId = viewIdAnnotation.getViewId(targetMember);
                            String propertyName = PropertyUtils.getPropertyName(targetMember);
                            TypeName viewHolderTypeName = ClassUtils.getBindingViewHolderName(target);
                            TypeName targetTypeName = ClassUtils.getElementClassName(target);

                            ViewBinder.Builder builder = new ViewBinder.Builder(targetTypeName)
                                    .setViewId(viewId)
                                    .setPropertyName(propertyName)
                                    .setGetView(new GetViewHolderView(viewHolderTypeName, propertyName))
                                    .setSetView(new SetViewHolderView(viewHolderTypeName, propertyName))
                                    .setGetBinder(new GetBinderComposition(targetTypeName))
                                    .setIsDirty(new IsDirtyIfNotEquals(targetTypeName));

                            viewBinders.add(builder);
                        }
                    }
                }
            }
        }
    }

    private void processBindData(Map<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {
        for (Element bindData: roundEnv.getElementsAnnotatedWith(BindData.class)) {
            ViewBinder.Builder binder = getViewBinderForProperty(bindData, viewBinders);

            // Bind
            String property = bindData.getAnnotation(BindData.class).set();
            TypeName viewType = AnnotationUtils.getBindDataViewType(bindData);
            TypeName valueType = typeUtils.getValueType(bindData);
            OnBind onBind = new OnBindPropertySetter(property, viewType, valueType);
            binder.addOnBind(onBind);

            // Data
            addGetValue(bindData, viewBinders);
        }
    }

    private void processData(Map<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv) {
        for (Element data: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.Data.class)) {
            addGetValue(data, targetViewBinders);
        }
    }

    private void processBind(Map<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {
        for (Element bind: roundEnv.getElementsAnnotatedWith(Bind.class)) {
            ViewBinder.Builder viewBinder = getViewBinderForProperty(bind, viewBinders);
            String propertyName = PropertyUtils.getPropertyName(bind);
            TypeName targetTypeName = viewBinder.getTargetTypeName();
            TypeName viewTypeName = typeUtils.getPropertyMethodViewType(bind);
            TypeName valueTypeName = typeUtils.getPropertyMethodValueType(bind);
            viewBinder.setGetBinder(new GetMethodBinder(targetTypeName, viewTypeName, valueTypeName, propertyName));
        }
    }

    private void processBindWhens(HashMap<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv) {
        for (Element bindWhen: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindWhen.class)) {
            String bindWhenValue =  bindWhen.getAnnotation(se.snylt.witch.annotations.BindWhen.class).value();
            switch (bindWhenValue) {
                case BindWhen.ALWAYS: {
                    ViewBinder.Builder binder = getViewBinderForProperty(bindWhen, targetViewBinders);
                    binder.setIsDirty(new IsDirtyAlways(binder.getTargetTypeName()));
                    continue;
                }
                case BindWhen.NOT_EQUALS: {
                    ViewBinder.Builder binder = getViewBinderForProperty(bindWhen, targetViewBinders);
                    binder.setIsDirty(new IsDirtyIfNotEquals(binder.getTargetTypeName()));
                    continue;
                }
                case BindWhen.NOT_SAME: {
                    ViewBinder.Builder binder = getViewBinderForProperty(bindWhen, targetViewBinders);
                    binder.setIsDirty(new IsDirtyIfNotSame(binder.getTargetTypeName()));
                }
            }
        }
    }

    private void addGetValue(Element data, Map<Element, List<ViewBinder.Builder>> targetViewBinders) {
        ViewBinder.Builder binder = getViewBinderForProperty(data, targetViewBinders);
        PropertyAccessor valueAccessor = PropertyUtils.getPropertyAccessor(data);
        TypeName valueTypeName = typeUtils.getValueType(data);
        TypeName targetTypeName = binder.getTargetTypeName();

        if(typeUtils.isValueContainer(data)) {
            binder.setIsDirty(new IsValueDirty(targetTypeName, valueAccessor))
                    .setGetValue(new GetTargetValueValue(targetTypeName, valueAccessor, valueTypeName));
        } else {
            binder.setGetValue(new GetTargetValue(targetTypeName, valueAccessor, valueTypeName));
        }
    }

    private ViewBinder.Builder getViewBinderForProperty(Element property, Map<Element, List<ViewBinder.Builder>> targetViewBinders) {
        Element target = property.getEnclosingElement();
        List<ViewBinder.Builder> viewBinders = targetViewBinders.get(target);
        String propertyName = PropertyUtils.getPropertyName(property);
        for (ViewBinder.Builder binder: viewBinders) {
            if (binder.getPropertyName().equals(propertyName)) {
                return binder;
            }
        }
        return null;
    }

    private boolean hasViewBinderForProperty(Element property, Map<Element, List<ViewBinder.Builder>> targetViewBinders) {
        return getViewBinderForProperty(property, targetViewBinders) != null;
    }

    private void writeFiles(HashMap<Element, List<ViewBinder.Builder>> binders) {
        for (Element target : binders.keySet()) {
            fileWriter.writeViewHolder(target, binders);
            fileWriter.writeTargetViewBinder(target, binders);
        }
    }
}
