package se.snylt.witch.processor;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
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
import se.snylt.witch.processor.utils.Logger;
import se.snylt.witch.processor.utils.ProcessorUtils;
import se.snylt.witch.processor.utils.TypeUtils;
import se.snylt.witch.processor.dataaccessor.DataAccessor;
import se.snylt.witch.processor.viewbinder.TargetViewBinder;
import se.snylt.witch.processor.viewbinder.ViewBinder;
import se.snylt.witch.processor.viewbinder.bind.BindBindData;
import se.snylt.witch.processor.viewbinder.bind.BindTargetMethod;
import se.snylt.witch.processor.viewbinder.getdata.GetNoData;
import se.snylt.witch.processor.viewbinder.getdata.GetTargetData;
import se.snylt.witch.processor.viewbinder.getview.GetNoView;
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyAlways;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotSame;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyOnce;
import se.snylt.witch.processor.viewbinder.setview.SetNoView;
import se.snylt.witch.processor.viewbinder.setview.SetViewHolderView;

import static se.snylt.witch.processor.utils.ProcessorUtils.getBindDataViewTypeName;
import static se.snylt.witch.processor.utils.ProcessorUtils.getBindMethod;

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

    private final boolean throwOnError;

    private TypeUtils typeUtils;

    private Logger logger;

    private Filer filer;

    private List<TargetViewBinder> targetViewBinders;

    private RoundEnvironment roundEnv;

    public WitchProcessor() {
        this(true);
    }

    WitchProcessor(boolean throwOnError) {
        this.throwOnError = throwOnError;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = new TypeUtils(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
        logger = new Logger(processingEnv.getMessager());
        ProcessorUtils.typeUtils = typeUtils;
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            targetViewBinders = new ArrayList<>();
            this.roundEnv = roundEnv;
            collectAllTargets(SupportedAnnotations.ALL_BIND_VIEW);
            processBindData();
            processBind();
            processBindNull();
            processBindWhens();
            processData();
            writeFiles();
        } catch (WitchException e) {
            logger.log(e);
            if(throwOnError) {
                throw e.toRuntimeException();
            }
        }
        return false;
    }

    private void collectAllTargets(SupportedAnnotations.HasViewId... annotations) {

        // Add all targets
        for (SupportedAnnotations.HasViewId hasViewIdAnnotation : annotations) {
            for (Element targetMember: roundEnv.getElementsAnnotatedWith(hasViewIdAnnotation.getClazz())) {
                if (!hasTargetViewBinder(targetMember)) {
                    Element target = targetMember.getEnclosingElement();
                    ClassName viewHolderClassName = FileUtils.getBindingViewHolderName(target);
                    ClassName targetViewBinderClassName = FileUtils.getTargetViewBinderClassName(target);
                    TypeName targetTypeName = TypeName.get(target.asType());
                    targetViewBinders.add(new TargetViewBinder(
                            target,
                            targetViewBinderClassName,
                            targetTypeName,
                            viewHolderClassName));
                }
            }
        }

        // Add all view binders in proper order.
        for(TargetViewBinder target: targetViewBinders) {
            for(Element targetMember: target.getElement().getEnclosedElements()) {
                for(SupportedAnnotations.HasViewId viewIdAnnotation: annotations) {
                    if(targetMember.getAnnotation(viewIdAnnotation.getClazz()) != null) {
                        if (!target.hasViewBinderForBind(targetMember)) {
                            int viewId = viewIdAnnotation.getViewId(targetMember);
                            String propertyName = ProcessorUtils.getPropertyName(targetMember);
                            TypeName targetTypeName = FileUtils.getElementClassName(target.getElement());

                            ViewBinder.Builder builder =
                                    new ViewBinder.Builder(target.getElement(), targetMember, targetTypeName)
                                    .setViewId(viewId)
                                    .setPropertyName(propertyName)
                                    .setGetData(new GetNoData(targetMember, targetTypeName)); // Default

                            target.addViewBinder(builder);
                        }
                    }
                }
            }
        }
    }

    private void processBindData() throws WitchException {
        for (Element bindData: roundEnv.getElementsAnnotatedWith(BindData.class)) {
            ViewBinder.Builder binder = getViewBinderForBind(bindData);

            // Bind
            TypeName targetTypeName = binder.getTargetTypeName();
            String property = bindData.getAnnotation(BindData.class).set();
            TypeMirror dataTypeMirror = typeUtils.getBoxedReturnTypeMirror(bindData);
            TypeName viewTypeName = getBindDataViewTypeName(bindData);
            TypeName dataTypeName = typeUtils.getReturnTypeName(bindData);
            BindBindData bind = new BindBindData(bindData, property, targetTypeName, viewTypeName, dataTypeName, dataTypeMirror);
            binder.setBind(bind);

            // Get view
            String viewName = binder.getViewHolderViewName();
            TypeName viewHolderTypeName = FileUtils.getBindingViewHolderName(binder.getTargetElement());
            binder.setGetView(new GetViewHolderView(viewTypeName, viewHolderTypeName, viewName));

            // Set view
            binder.setSetView(new SetViewHolderView(viewTypeName, viewHolderTypeName, viewName));

            // Data
            addGetData(bindData, Arrays.asList(binder));
        }
    }

    private void processData() throws WitchException{
        for (Element data: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.Data.class)) {
            List<ViewBinder.Builder> viewBinders;
            try {
                viewBinders = getViewBindersForData(data);
            } catch (WitchException e) {
                viewBinders = new ArrayList<>();
                logger.logWarn("No binding associated with @Data: " + data.getSimpleName());
            }
            addGetData(data, viewBinders);
        }
    }

    private void processBind() throws WitchException {
        for (Element bind: roundEnv.getElementsAnnotatedWith(Bind.class)) {

            // Bind
            ProcessorUtils.BindMethod bindMethod = getBindMethod(bind);
            ViewBinder.Builder viewBinder = getViewBinderForBind(bind);
            TypeName targetTypeName = viewBinder.getTargetTypeName();
            viewBinder.setBind(new BindTargetMethod(bind, targetTypeName, bindMethod));

            // Set & Get view
            TypeName viewHolderTypeName = FileUtils.getBindingViewHolderName(viewBinder.getTargetElement());
            if (bindMethod.hasViewParameter()) {
                String viewName = viewBinder.getViewHolderViewName();
                TypeName viewTypeName = bindMethod.getViewTypeName();
                viewBinder.setGetView(new GetViewHolderView(viewTypeName, viewHolderTypeName, viewName));
                viewBinder.setSetView(new SetViewHolderView(viewTypeName, viewHolderTypeName, viewName));
            } else {
                viewBinder.setGetView(new GetNoView(viewHolderTypeName));
                viewBinder.setSetView(new SetNoView(viewHolderTypeName));
            }
        }
    }

    private void processBindWhens() throws WitchException {
        for (Element bindWhen: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindWhen.class)) {
            String bindWhenValue = bindWhen.getAnnotation(se.snylt.witch.annotations.BindWhen.class).value();

            // Must be used with @Bind or @BindData
            if (!hasViewBinderForBind(bindWhen)) {
                throw WitchException.bindWhenNotCombinedWithBind(bindWhen);
            }

            ViewBinder.Builder binder = getViewBinderForBind(bindWhen);
            if (!binder.getBind().getElement().equals(bindWhen)) {
                throw WitchException.bindWhenNotCombinedWithBind(bindWhen);
            }

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
                case BindWhen.ONCE: {
                    isDirty = new IsDirtyOnce(binder.getTargetTypeName());
                    break;
                }
            }

            if (isDirty == null) {
                throw WitchException.invalidBindWhenValue(bindWhen, bindWhenValue);
            }

            if (binder.isIsDirtySet()) {
                throw WitchException.conflictingBindWhen(bindWhen);
            }

            binder.setIsDirty(isDirty);

        }
    }

    private void processBindNull() throws WitchException {
        for (Element bindNull: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindNull.class)) {

            if (!hasViewBinderForBind(bindNull)) {
                throw WitchException.bindNullNotCombinedWithBind(bindNull);
            }
            ViewBinder.Builder binder = getViewBinderForBind(bindNull);

            if (!binder.getBind().getElement().equals(bindNull)) {
                throw WitchException.bindNullNotCombinedWithBind(bindNull);
            }

            binder.getIsDirty().setBindNull(true);
        }
    }

    private void addGetData(Element data, List<ViewBinder.Builder> binders) throws WitchException {
        DataAccessor dataAccessor = ProcessorUtils.getDataAccessor(data);
        TypeMirror dataTypeMirror = typeUtils.getBoxedReturnTypeMirror(data);
        TypeName dataTypeName = typeUtils.getReturnTypeName(data);

        for(ViewBinder.Builder binder : binders) {
            TypeName targetTypeName = binder.getTargetTypeName();
            binder.setGetData(new GetTargetData(data, targetTypeName, dataAccessor, dataTypeName, dataTypeMirror));
        }
    }

    private TargetViewBinder getTargetViewBinder(Element member) throws WitchException {
        Element target = member.getEnclosingElement();
        for (TargetViewBinder targetViewBinder: targetViewBinders) {
            if (targetViewBinder.getElement().equals(target)) {
                return targetViewBinder;
            }
        }
        throw new WitchException("Target binder for memeber " + member.getSimpleName().toString() + " not found");
    }

    private boolean hasTargetViewBinder(Element member) {
        try {
            return getTargetViewBinder(member) != null;
        } catch (WitchException e) {
            return false;
        }
    }

    private ViewBinder.Builder getViewBinderForBind(Element bind) throws WitchException {
        return getTargetViewBinder(bind).getViewBinderForBind(bind);
    }

    private List<ViewBinder.Builder> getViewBindersForData(Element data) throws WitchException {
        return getTargetViewBinder(data).getViewBindersForData(data);
    }

    private boolean hasViewBinderForBind(Element bind) {
        try {
            return getViewBinderForBind(bind) != null ;
        } catch (WitchException e) {
            return false;
        }
    }

    private boolean hasViewBindersForData(Element data) {
        try {
            return getTargetViewBinder(data).hasViewBindersForData(data);
        } catch (WitchException e) {
            return false;
        }
    }

    private void writeFiles() throws WitchException {
        for (TargetViewBinder target : targetViewBinders) {
            try {
                target.createBinderFactoryJavaFile(typeUtils).writeTo(filer);
                target.createViewHolderJavaFile().writeTo(filer);
            } catch (IOException e) {
                logger.logError(e.getMessage());
            }
        }
    }
}
