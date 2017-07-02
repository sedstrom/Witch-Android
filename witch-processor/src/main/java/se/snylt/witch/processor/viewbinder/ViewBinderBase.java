package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;

public abstract class ViewBinderBase extends ViewBinder {

    private final ClassName viewHolderClassName;

    private final ClassName viewClassName = ClassName.get("android.view", "View");

    final int viewId;

    protected ViewBinderBase(ClassName viewHolderClassName, PropertyAccessor accessor, int viewId) {
        this.viewHolderClassName = viewHolderClassName;
        this.valueAccessor = accessor;
        this.viewId = viewId;
    }

    @Override
    MethodSpec getView() {
        MethodSpec getView = MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .returns(viewClassName)
                .addStatement("return (($T)viewHolder).$N", viewHolderClassName, valueAccessor.viewHolderFieldName())
                .build();
        return getView;
    }

    @Override
    MethodSpec setView() {
        MethodSpec setView = MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .addParameter(Object.class, "view")
                .returns(void.class)
                .addStatement("(($T)viewHolder).$N = ($T)$N", viewHolderClassName, valueAccessor.viewHolderFieldName(),
                        viewClassName, "view")
                .build();
        return setView;
    }
}
