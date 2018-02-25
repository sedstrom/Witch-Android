package se.snylt.witch.processor;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.TypeName;

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
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.binding.OnBindPropertySetter;
import se.snylt.witch.processor.utils.FileUtils;
import se.snylt.witch.processor.utils.FileWriter;
import se.snylt.witch.processor.utils.Logger;
import se.snylt.witch.processor.utils.ProcessorUtils;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.ViewBinder;
import se.snylt.witch.processor.viewbinder.getbinder.GetBinderComposition;
import se.snylt.witch.processor.viewbinder.getbinder.GetMethodBinder;
import se.snylt.witch.processor.viewbinder.getvalue.GetTargetValue;
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyAlways;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotSame;
import se.snylt.witch.processor.viewbinder.setview.SetViewHolderView;
// TOOD
import static se.snylt.witch.processor.utils.TypeUtils.getBindDataViewType;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
        SupportedAnnotations.Data.name,
        SupportedAnnotations.BindData.name,
        SupportedAnnotations.Bind.name,
        SupportedAnnotations.BindWhen.name,
        SupportedAnnotations.BindNull.name
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
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
        processData(viewBinders, roundEnv);
        processBind(viewBinders, roundEnv);
        processBindWhens(viewBinders, roundEnv);
        processBindNull(viewBinders, roundEnv);
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
                            String propertyName = ProcessorUtils.getPropertyName(targetMember);
                            TypeName viewHolderTypeName = FileUtils.getBindingViewHolderName(target);
                            TypeName targetTypeName = FileUtils.getElementClassName(target);

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

                // Bind
                String property = bindData.getAnnotation(BindData.class).set();
                TypeName viewType = getBindDataViewType(bindData);
                TypeName valueType = typeUtils.getValueType(bindData);
                OnBind onBind = new OnBindPropertySetter(property, viewType, valueType);

                try {
                    ViewBinder.Builder binder = getViewBinderForProperty(bindData, viewBinders);
                    binder.addOnBind(onBind);

                    // Data
                    addGetValue(bindData, viewBinders);
                } catch (WitchException e) {
                    logger.logWarn(String.format("No target found for %s.",  property));
                }
        }
    }

    private void processData(Map<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv) {
        for (Element data: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.Data.class)) {
            addGetValue(data, targetViewBinders);
        }
    }

    private void processBind(Map<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {
        for (Element bind: roundEnv.getElementsAnnotatedWith(Bind.class)) {
            String propertyName = ProcessorUtils.getPropertyName(bind);
            try {
                ViewBinder.Builder viewBinder = getViewBinderForProperty(bind, viewBinders);
                TypeName targetTypeName = viewBinder.getTargetTypeName();
                TypeName[] bindMethodTypes = typeUtils.getBindMethodTypeNames(bind);
                TypeName viewTypeName = bindMethodTypes[0];
                TypeName valueTypeName = bindMethodTypes[1];
                viewBinder.setGetBinder(new GetMethodBinder(targetTypeName, viewTypeName, valueTypeName, propertyName));
            } catch (WitchException e) {
                logger.log(e);
            }
        }
    }

    private void processBindWhens(HashMap<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv) {
        try {
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
        } catch (WitchException e) {

        }
    }

    private void processBindNull(HashMap<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {
        try {
        for (Element bindWhen: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindNull.class)) {
            ViewBinder.Builder binder = getViewBinderForProperty(bindWhen, viewBinders);
            binder.getIsDirty().setBindNull(true);
        }
        } catch (WitchException e) {

        }
    }

    private void addGetValue(Element data, Map<Element, List<ViewBinder.Builder>> targetViewBinders) {
        try {
            PropertyAccessor valueAccessor = ProcessorUtils.getPropertyAccessor(data);
            try {
                ViewBinder.Builder binder = getViewBinderForProperty(data, targetViewBinders);
                TypeName valueTypeName = typeUtils.getValueType(data);
                TypeName targetTypeName = binder.getTargetTypeName();
                binder.setGetValue(new GetTargetValue(targetTypeName, valueAccessor, valueTypeName));
            } catch (WitchException e) {
                logger.log(WitchException.noBindForData(data));
            }
        }  catch (WitchException e) {
            logger.log(e);
        }
    }

    private ViewBinder.Builder getViewBinderForProperty(Element property, Map<Element, List<ViewBinder.Builder>> targetViewBinders) throws WitchException {
        Element target = property.getEnclosingElement();
        List<ViewBinder.Builder> viewBinders = targetViewBinders.get(target);
        if(viewBinders == null) {
            throw new WitchException("Target binder for target " + target.getSimpleName() + " not found");
        }
        String propertyName = ProcessorUtils.getPropertyName(property);
        for (ViewBinder.Builder binder: viewBinders) {
            if (binder.getPropertyName().equals(propertyName)) {
                return binder;
            }
        }
        throw new WitchException("Target binder for target " + target.getSimpleName() + " not found");
    }

    private boolean hasViewBinderForProperty(Element property, Map<Element, List<ViewBinder.Builder>> targetViewBinders) {
        try {
            return getViewBinderForProperty(property, targetViewBinders) != null;
        } catch (WitchException e) {
            return false;
        }
    }

    private void writeFiles(HashMap<Element, List<ViewBinder.Builder>> binders) {
        for (Element target : binders.keySet()) {
            try {
                fileWriter.writeTargetViewBinder(target, binders);
            } catch (WitchException e) {
                logger.logError(e.getMessage());
            }
        }
    }
}
