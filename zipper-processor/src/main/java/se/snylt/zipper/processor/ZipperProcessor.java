package se.snylt.zipper.processor;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.annotations.BindProperty;
import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.OnBind;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
        "se.snylt.zipper.annotations.BindTo",
        "se.snylt.zipper.annotations.OnBind",
        "se.snylt.zipper.annotations.BindProperty"})
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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<Element, List<BindToView>> targets = new HashMap<>();
        addTargets(targets, roundEnv);
        addBindActions(targets, roundEnv);
        buildJava(targets);
        return true;
    }

    private void addTargets(Map<Element, List<BindToView>> targets, RoundEnvironment roundEnv) {
        // Find all targets (parents to fields with @BindTo)
        for (Element value : roundEnv.getElementsAnnotatedWith(BindTo.class)) {
            Element target = value.getEnclosingElement();

            // Prepare target bindings
            if (!targets.containsKey(target)) {
                targets.put(target, new LinkedList<>());
            }

            // Add view id and field to be bound
            List<BindToView> bindToViews = targets.get(target);
            if (!bindToViews.contains(value)) {
                bindToViews.add(new BindToView(getBindToViewId(value), value));
            }
        }
    }

    private void addBindActions(HashMap<Element, List<BindToView>> binders, RoundEnvironment roundEnv) {
        // OnBind
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(OnBind.class)) {
            Element target = bindAction.getEnclosingElement();
            List<BindToView> bindToViews = binders.get(target);

            // Add bind actions to view binding
            for (BindToView bindToView : bindToViews) {
                if (bindToView.equals(bindAction)) {
                    String onBindClass = getOnBindClass(bindAction);
                    bindToView.addBindAction(new OnBindDef(onBindClass));
                }
            }
        }

        // BindProperty
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(BindProperty.class)) {
            Element target = bindAction.getEnclosingElement();
            List<BindToView> bindToViews = binders.get(target);

            // Add bind actions to view binding
            for (BindToView bindToView : bindToViews) {
                if (bindToView.equals(bindAction)) {
                    String bindProperty = getBindProperty(bindAction);
                    bindToView.addBindAction(new BindPropertyDef(bindProperty));
                }
            }
        }
    }

    private void buildJava(HashMap<Element, List<BindToView>> binders) {
        for (Element target : binders.keySet()) {
            ClassName bindingClassName = getBindingClassName(target);
            TypeSpec typeSpec = BinderFactory.toJava(binders.get(target), bindingClassName);
            JavaFile javaFile = JavaFile.builder(bindingClassName.packageName(), typeSpec).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ClassName getBindingClassName(Element target) {
        String className = ClassUtils.getBindingName(target);
        String packageName = ClassUtils.getBindingPackage(target);
        return ClassName.get(packageName, className);
    }

    private String getOnBindClass(Element action){
        TypeMirror bindClass = null;
        try {
            action.getAnnotation(OnBind.class).value();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return bindClass.toString();
    }

    private String getBindProperty(Element target) {
        return target.getAnnotation(BindProperty.class).value();
    }

    private Integer getBindToViewId(Element element) {
        return element.getAnnotation(BindTo.class).value();
    }
}
