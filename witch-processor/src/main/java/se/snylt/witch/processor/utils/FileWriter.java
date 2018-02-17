package se.snylt.witch.processor.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;

import se.snylt.witch.processor.java.TargetViewBinder;
import se.snylt.witch.processor.java.ViewHolder;
import se.snylt.witch.processor.viewbinder.ViewBinder;

public class FileWriter {

    private final Filer filer;

    public FileWriter(Filer filer) {
        this.filer = filer;
    }

    public void writeTargetViewBinder(Element target, HashMap<Element, List<ViewBinder.Builder>> targetViewBinders) {
        ClassName targetViewBinderClassName = ClassUtils.getTargetViewBinderClassName(target);
        TypeName targetTypeName = TypeName.get(target.asType());
        ClassName viewHolderClassName = ClassUtils.getBindingViewHolderName(target);
        TypeSpec targetViewBinderTypeSpec = new TargetViewBinder(targetViewBinders.get(target), targetViewBinderClassName, targetTypeName, viewHolderClassName).create();
        JavaFile bindingJavaFile = JavaFile.builder(targetViewBinderClassName.packageName(), targetViewBinderTypeSpec).build();
        try {
            bindingJavaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeViewHolder(Element target, HashMap<Element, List<ViewBinder.Builder>> targetViewBinders) {
        ClassName viewHolderClassName = ClassUtils.getBindingViewHolderName(target);
        TypeSpec viewHolderTypeSpec = new ViewHolder(targetViewBinders.get(target), viewHolderClassName).create();
        JavaFile viewHolderJavaFile = JavaFile.builder(viewHolderClassName.packageName(), viewHolderTypeSpec).build();
        try {
            viewHolderJavaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
