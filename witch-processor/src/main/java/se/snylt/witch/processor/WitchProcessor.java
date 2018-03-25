package se.snylt.witch.processor;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.ArrayList;
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
import se.snylt.witch.processor.viewbinder.getview.GetViewHolderView;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyAlways;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotSame;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyOnce;
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

    private final boolean throwOnError;

    private TypeUtils typeUtils;

    private ProcessorUtils processorUtils;

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
        processorUtils = new ProcessorUtils(typeUtils);
        logger = new Logger(processingEnv.getMessager());
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            targetViewBinders = new ArrayList<>();
            this.roundEnv = roundEnv;
            collectAllTargets(SupportedAnnotations.ALL_BIND_VIEW);
            processBindData();
            processData();
            processBind();
            processBindNull();
            processBindWhens();
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
                        if (!target.hasViewBinderFor(targetMember)) {
                            int viewId = viewIdAnnotation.getViewId(targetMember);
                            String propertyName = ProcessorUtils.getPropertyName(targetMember);
                            TypeName targetTypeName = FileUtils.getElementClassName(target.getElement());

                            ViewBinder.Builder builder =
                                    new ViewBinder.Builder(target.getElement(), targetTypeName)
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
            ViewBinder.Builder binder = getViewBinder(bindData);

            // Bind
            TypeName targetTypeName = binder.getTargetTypeName();
            String property = bindData.getAnnotation(BindData.class).set();
            TypeMirror dataTypeMirror = typeUtils.getBoxedReturnTypeMirror(bindData);
            TypeName viewTypeName = processorUtils.getBindDataViewTypeName(bindData);
            TypeName dataTypeName = typeUtils.getReturnTypeName(bindData);
            BindBindData bind = new BindBindData(property, targetTypeName, viewTypeName, dataTypeName, dataTypeMirror);
            binder.setBind(bind);

            // Get view
            String propertyName = ProcessorUtils.getPropertyName(bindData);
            TypeName viewHolderTypeName = FileUtils.getBindingViewHolderName(binder.getTarget());
            binder.setGetView(new GetViewHolderView(viewTypeName, viewHolderTypeName, propertyName));

            // Set view
            binder.setSetView(new SetViewHolderView(viewTypeName, viewHolderTypeName, propertyName));

            // Data
            addGetData(bindData);
        }
    }

    private void processData() throws WitchException{
        for (Element data: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.Data.class)) {
            addGetData(data);
        }
    }

    private void processBind() throws WitchException {
        for (Element bind: roundEnv.getElementsAnnotatedWith(Bind.class)) {

            // Bind
            String propertyName = ProcessorUtils.getPropertyName(bind);
            ViewBinder.Builder viewBinder = getViewBinder(bind);
            TypeName targetTypeName = viewBinder.getTargetTypeName();
            ProcessorUtils.BindSpec spec = processorUtils.getBindSpec(bind);
            viewBinder.setBind(new BindTargetMethod(bind, targetTypeName, propertyName, spec));

            // Get view
            TypeName viewTypeName = spec.getViewTypeName();
            TypeName viewHolderTypeName = FileUtils.getBindingViewHolderName(viewBinder.getTarget());
            viewBinder.setGetView(new GetViewHolderView(viewTypeName, viewHolderTypeName, propertyName));

            // Set view
            viewBinder.setSetView(new SetViewHolderView(viewTypeName, viewHolderTypeName, propertyName));
        }
    }

    private void processBindWhens() throws WitchException {
        for (Element bindWhen: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindWhen.class)) {
            String bindWhenValue = bindWhen.getAnnotation(se.snylt.witch.annotations.BindWhen.class).value();
            ViewBinder.Builder binder = getViewBinder(bindWhen);
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
        for (Element bindWhen: roundEnv.getElementsAnnotatedWith(se.snylt.witch.annotations.BindNull.class)) {
            ViewBinder.Builder binder = getViewBinder(bindWhen);
            binder.getIsDirty().setBindNull(true);
        }
    }

    private void addGetData(Element data) throws WitchException {
        DataAccessor dataAccessor = ProcessorUtils.getDataAccessor(data);
        TypeMirror dataTypeMirror = typeUtils.getBoxedReturnTypeMirror(data);
        TypeName dataTypeName = typeUtils.getReturnTypeName(data);

        if(!hasViewBinder(data)) {
            throw WitchException.noBindForData(data);
        }

        ViewBinder.Builder binder = getViewBinder(data);
        TypeName targetTypeName = binder.getTargetTypeName();
        binder.setGetData(new GetTargetData(data, targetTypeName, dataAccessor, dataTypeName, dataTypeMirror));
    }

    private TargetViewBinder getTargetViewBinder(Element member) throws WitchException {
        Element target = member.getEnclosingElement();
        for (TargetViewBinder targetViewBinder: targetViewBinders) {
            if (targetViewBinder.getElement().equals(target)) {
                return targetViewBinder;
            }
        }
        throw new WitchException("Target binder for target " + target.getSimpleName() + " not found");
    }

    private boolean hasTargetViewBinder(Element member) {
        try {
            return getTargetViewBinder(member) != null;
        } catch (WitchException e) {
            return false;
        }
    }

    private ViewBinder.Builder getViewBinder(Element member) throws WitchException {
        return getTargetViewBinder(member).getViewBinderFor(member);
    }

    private boolean hasViewBinder(Element member) {
        try {
            return getTargetViewBinder(member).getViewBinderFor(member) != null;
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
