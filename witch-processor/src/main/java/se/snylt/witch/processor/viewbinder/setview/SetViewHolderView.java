package se.snylt.witch.processor.viewbinder.setview;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class SetViewHolderView implements MethodSpecModule {

    private final ClassName viewClassName = ClassName.get("android.view", "View");

    private final ClassName viewHolderClassName;

    private final PropertyAccessor valueAccessor;

    public SetViewHolderView(ClassName viewHolderClassName, PropertyAccessor valueAccessor) {
        this.viewHolderClassName = viewHolderClassName;
        this.valueAccessor = valueAccessor;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .addParameter(Object.class, "view")
                .returns(void.class)
                .addStatement("(($T)viewHolder).$N = ($T)$N", viewHolderClassName, valueAccessor.viewHolderFieldName(),
                        viewClassName, "view")
                .build();
    }
}
