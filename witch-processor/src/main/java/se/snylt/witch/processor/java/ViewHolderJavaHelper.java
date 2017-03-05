package se.snylt.witch.processor.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.binding.ViewBindingDef;

import static se.snylt.witch.processor.TypeUtils.ANDROID_VIEW;

public class ViewHolderJavaHelper {

    public static TypeSpec toJava(List<ViewBindingDef> viewActionses, ClassName viewHolderClassName) {
        TypeSpec.Builder viewHolder =
                TypeSpec.classBuilder(viewHolderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (ViewBindingDef viewBindingDef : viewActionses) {
            viewHolder.addField(ANDROID_VIEW, viewBindingDef.value.viewHolderFieldName(), Modifier.PUBLIC);
        }

        return viewHolder.build();
    }
}
