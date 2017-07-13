package se.snylt.witch.processor.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.ViewBinder;

import static se.snylt.witch.processor.utils.TypeUtils.ANDROID_VIEW;

public class ViewHolder {

    private final List<ViewBinder.Builder> viewBinders;

    private final ClassName viewHolderClassName;

    public ViewHolder(List<ViewBinder.Builder> viewBinders, ClassName viewHolderClassName) {
        this.viewBinders = viewBinders;
        this.viewHolderClassName = viewHolderClassName;
    }

    public TypeSpec create() {
        TypeSpec.Builder viewHolder =
                TypeSpec.classBuilder(viewHolderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (ViewBinder.Builder viewBinder : viewBinders) {
            viewHolder.addField(ANDROID_VIEW, viewBinder.getValueAccessor().viewHolderFieldName(), Modifier.PUBLIC);
        }

        return viewHolder.build();
    }
}
