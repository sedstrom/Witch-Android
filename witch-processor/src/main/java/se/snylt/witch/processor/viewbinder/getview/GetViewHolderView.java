package se.snylt.witch.processor.viewbinder.getview;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class GetViewHolderView implements MethodSpecModule {

    private final ClassName viewClassName = ClassName.get("android.view", "View");

    private final ClassName viewHolderClassName;

    private final PropertyAccessor valueAccessor;

    public GetViewHolderView(ClassName viewHolderClassName, PropertyAccessor valueAccessor) {
        this.viewHolderClassName = viewHolderClassName;
        this.valueAccessor = valueAccessor;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .returns(viewClassName)
                .addStatement("return (($T)viewHolder).$N", viewHolderClassName, valueAccessor.viewHolderFieldName())
                .build();
    }
}
