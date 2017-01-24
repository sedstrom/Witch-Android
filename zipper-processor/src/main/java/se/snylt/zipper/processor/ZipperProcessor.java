package se.snylt.zipper.processor;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
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
import javax.tools.Diagnostic;

import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.BindToCompoundButton;
import se.snylt.zipper.annotations.BindToEditText;
import se.snylt.zipper.annotations.BindToImageView;
import se.snylt.zipper.annotations.BindToTextView;
import se.snylt.zipper.annotations.OnBind;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
        "se.snylt.zipper.annotations.BindTo",
        "se.snylt.zipper.annotations.BindToTextView",
        "se.snylt.zipper.annotations.BindToEditText",
        "se.snylt.zipper.annotations.BindToImageView",
        "se.snylt.zipper.annotations.BindToCompoundButton",
        "se.snylt.zipper.annotations.OnBind",
        "se.snylt.zipper.annotations.BindProperty"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ZipperProcessor extends AbstractProcessor {

    private final static Class<? extends Annotation>[] BIND_VIEW_ANNOTATION = new Class[]{
            BindTo.class,
            BindToTextView.class,
            BindToEditText.class,
            BindToImageView.class,
            BindToCompoundButton.class
    };

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

    private void log(String message){
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<Element, List<BindToView>> targets = new HashMap<>();
        addTarget(targets, roundEnv, BIND_VIEW_ANNOTATION);
        addBindActions(targets, roundEnv);
        buildJava(targets);
        return true;
    }

    private void addTarget(Map<Element, List<BindToView>> targets, RoundEnvironment roundEnv, Class<? extends Annotation> ...annotations) {
        for(Class<? extends Annotation> annotation: annotations) {
            for (Element value : roundEnv.getElementsAnnotatedWith(annotation)) {
                Element target = value.getEnclosingElement();

                // Prepare target bindings
                if (!targets.containsKey(target)) {
                    targets.put(target, new LinkedList<>());
                }

                // Add view id and field to be bound
                List<BindToView> bindToViews = targets.get(target);
                if (!bindToViews.contains(value)) {
                    bindToViews.add(new BindToView(getViewId(value, annotation), value));
                }
            }
        }
    }

    private Integer getViewId(Element element, Class<? extends Annotation> annotation) {
        if(annotation == BindTo.class) {
            return element.getAnnotation(BindTo.class).value();
        } else if(annotation == BindToTextView.class) {
            return element.getAnnotation(BindToTextView.class).id();
        } else if(annotation == BindToImageView.class) {
            return element.getAnnotation(BindToImageView.class).id();
        } else if(annotation == BindToEditText.class) {
            return element.getAnnotation(BindToEditText.class).id();
        } else if(annotation == BindToCompoundButton.class) {
            return element.getAnnotation(BindToCompoundButton.class).id();
        }
        return null;
    }

    private void addBindActions(HashMap<Element, List<BindToView>> binders, RoundEnvironment roundEnv) {
        // OnBind
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(OnBind.class)) {
            TypeName onBindClass = getOnBindClass(bindAction);
            BindActionDef actionDef = new OnBindDef(onBindClass);
            addBindAction(bindAction, actionDef, binders);
        }

        // BindToTextView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(BindToTextView.class)) {
            String property = bindAction.getAnnotation(BindToTextView.class).set();
            TypeName viewType  = ClassName.get("android.widget", "TextView");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToImageView
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(BindToImageView.class)) {
            String property = bindAction.getAnnotation(BindToImageView.class).set();
            TypeName viewType  = ClassName.get("android.widget", "ImageView");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToEditText
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(BindToEditText.class)) {
            String property = bindAction.getAnnotation(BindToEditText.class).set();
            TypeName viewType  = ClassName.get("android.widget", "EditText");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }

        // BindToEditText
        for (Element bindAction : roundEnv.getElementsAnnotatedWith(BindToCompoundButton.class)) {
            String property = bindAction.getAnnotation(BindToCompoundButton.class).set();
            TypeName viewType  = ClassName.get("android.widget", "CompoundButton");
            addOnBindViewDef(binders, property, viewType, bindAction);
        }
    }

    private void addOnBindViewDef(HashMap<Element, List<BindToView>> binders, String property, TypeName viewType, Element bindAction) {
        TypeName valueType  = ClassName.get(bindAction.asType());
        BindActionDef actionDef = new OnBindViewDef(property, viewType, valueType);
        addBindAction(bindAction, actionDef, binders);
    }

    private void addBindAction(Element bindAction, BindActionDef bindActionDef, HashMap<Element, List<BindToView>> binders) {
        Element target = bindAction.getEnclosingElement();
        List<BindToView> bindToViews = binders.get(target);

        // Add bind actions to view binding
        for (BindToView bindToView : bindToViews) {
            if (bindToView.equals(bindAction)) {
                bindToView.addBindAction(bindActionDef);
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

    private TypeName getOnBindClass(Element action){
        TypeMirror bindClass = null;
        try {
            action.getAnnotation(OnBind.class).value();
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }
}
