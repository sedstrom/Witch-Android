package se.snylt.witch.processor.viewbinder.getview;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class GetViewHolderView implements MethodSpecModule {

    private final TypeName viewTypeName = ClassName.get("android.view", "View");

    private final TypeName viewHolderTypeName;

    private final PropertyAccessor valueAccessor;

    public GetViewHolderView(TypeName viewHolderTypeName, PropertyAccessor valueAccessor) {
        this.viewHolderTypeName = viewHolderTypeName;
        this.valueAccessor = valueAccessor;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewHolderTypeName, "viewHolder")
                .returns(viewTypeName)
                .addStatement("return viewHolder.$N", valueAccessor.viewHolderFieldName())
                .build();
    }
}
