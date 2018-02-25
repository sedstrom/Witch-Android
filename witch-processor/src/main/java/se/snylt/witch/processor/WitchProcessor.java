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
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.processor.utils.FileUtils;
import se.snylt.witch.processor.utils.FileWriter;
import se.snylt.witch.processor.utils.Logger;
import se.snylt.witch.processor.utils.ProcessorUtils;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.ViewBinder;
import se.snylt.witch.processor.viewbinder.getbinder.GetBindDataBinder;
import se.snylt.witch.processor.viewbinder.getbinder.GetTargetMethodBinder;
import se.snylt.witch.processor.viewbinder.getdata.GetTargetData;
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyAlways;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotSame;
import se.snylt.witch.processor.viewbinder.setview.SetViewHolderView;

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

    private ProcessorUtils processorUtils;

    private FileWriter fileWriter;

    private Logger logger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = new TypeUtils(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
        processorUtils = new ProcessorUtils(typeUtils);
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
                                    .setSetView(new SetViewHolderView(viewHolderTypeName, propertyName));

                            viewBinders.add(builder);
                        }
                    }
                }
            }
        }
    }

    private void processBindData(Map<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {
        for (Element bindData: roundEnv.getElementsAnnotatedWith(BindData.class)) {
            try {
                ViewBinder.Builder binder = getViewBinderForProperty(bindData, viewBinders);

                // Bind
                String property = bindData.getAnnotation(BindData.class).set();
                TypeMirror dataTypeMirror = typeUtils.getReturnTypeMirror(bindData);
                TypeName viewTypeName = processorUtils.getBindDataViewTypeName(bindData);
                TypeName dataTypeName = typeUtils.getReturnTypeName(bindData);
                GetBindDataBinder getBinder = new GetBindDataBinder(bindData, binder.getTargetTypeName(), property, viewTypeName, dataTypeName, dataTypeMirror);
                binder.setGetBinder(getBinder);

                // Data
                addGetData(bindData, viewBinders);
            } catch (WitchException e) {
                logger.logWarn(e.getMessage());
            }
        }
    }

    private void processData(Map<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv) {
        for (Element data: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.Data.class)) {
            addGetData(data, targetViewBinders);
        }
    }

    private void processBind(Map<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {
        for (Element bind: roundEnv.getElementsAnnotatedWith(Bind.class)) {
            String propertyName = ProcessorUtils.getPropertyName(bind);
            try {
                ViewBinder.Builder viewBinder = getViewBinderForProperty(bind, viewBinders);
                TypeName targetTypeName = viewBinder.getTargetTypeName();
                TypeMirror[] bindMethodTypeMirrors = processorUtils.getBindMethodTypeMirrors(bind);
                TypeName[] bindMethodTypes = processorUtils.getBindMethodTypeNames(bind);
                TypeName viewTypeName = bindMethodTypes[0];
                TypeName dataTypeName = bindMethodTypes[1];
                TypeMirror dataTypeMirror = bindMethodTypeMirrors[1];
                viewBinder.setGetBinder(new GetTargetMethodBinder(bind, targetTypeName, viewTypeName, dataTypeName, dataTypeMirror, propertyName));
            } catch (WitchException e) {
                logger.log(e);
            }
        }
    }

    private void processBindWhens(HashMap<Element, List<ViewBinder.Builder>> targetViewBinders, RoundEnvironment roundEnv) {
        for (Element bindWhen: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindWhen.class)) {
            String bindWhenValue = bindWhen.getAnnotation(se.snylt.witch.annotations.BindWhen.class).value();
            try {
                ViewBinder.Builder binder = getViewBinderForProperty(bindWhen, targetViewBinders);
                IsDirty isDirty = null;
                switch (bindWhenValue) {
                    case BindWhen.ALWAYS: {
                        isDirty = new IsDirtyAlways(binder.getTargetTypeName());
                        break;
                    }
                    case BindWhen.NOT_EQUALS: {
                        isDirty = new IsDirtyIfNotEquals(binder.getTargetTypeName());
                        break;
                    }
                    case BindWhen.NOT_SAME: {
                        isDirty = new IsDirtyIfNotSame(binder.getTargetTypeName());
                        break;
                    }
                }

                if (isDirty == null) {
                    logger.log(WitchException.invalidBindWhenValue(bindWhen, bindWhenValue));
                    continue;
                }

                if (binder.isDirtySet()) {
                    logger.log(WitchException.conflictingBindWhen(bindWhen));
                    continue;
                }

                binder.setIsDirty(isDirty);

            } catch (WitchException e) {
                logger.logWarn(e.getMessage());
            }
        }
    }

    private void processBindNull(HashMap<Element, List<ViewBinder.Builder>> viewBinders, RoundEnvironment roundEnv) {
        for (Element bindWhen: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindNull.class)) {
            try {
                ViewBinder.Builder binder = getViewBinderForProperty(bindWhen, viewBinders);
                binder.getIsDirty().setBindNull(true);
            } catch (WitchException e) {
                logger.logWarn(e.getMessage());
            }
        }

    }

    private void addGetData(Element data, Map<Element, List<ViewBinder.Builder>> targetViewBinders) {
        try {
            PropertyAccessor valueAccessor = ProcessorUtils.getPropertyAccessor(data);
            try {
                ViewBinder.Builder binder = getViewBinderForProperty(data, targetViewBinders);
                TypeMirror dataTypeMirror = typeUtils.getReturnTypeMirror(data);
                TypeName dataTypeName = typeUtils.getReturnTypeName(data);
                TypeName targetTypeName = binder.getTargetTypeName();
                binder.setGetData(new GetTargetData(data, targetTypeName, valueAccessor, dataTypeName, dataTypeMirror));
            } catch (WitchException e) {
                logger.log(WitchException.noBindForData(data));
            }
        } catch (WitchException e) {
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

            // Validate
            for (ViewBinder.Builder builder : binders.get(target)) {
                try {
                    builder.validate(typeUtils);
                } catch (WitchException e) {
                    logger.log(e);
                    return;
                }
            }

            try {
                fileWriter.writeTargetViewBinder(target, binders);
            } catch (WitchException e) {
                logger.log(e);
            } catch (Exception e) {
                // TODO
            }
        }
    }
}
