package se.snylt.witch.processor.viewbinder.getview;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class GetViewHolderView implements MethodSpecModule {

    private final TypeName viewTypeName;

    private final TypeName viewHolderTypeName;

    private final String propertyName;

    public GetViewHolderView(TypeName viewTypeName, TypeName viewHolderTypeName, String propertyName) {
        this.viewTypeName = viewTypeName;
        this.viewHolderTypeName = viewHolderTypeName;
        this.propertyName = propertyName;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewHolderTypeName, "viewHolder")
                .returns(viewTypeName)
                .addStatement("return viewHolder.$N", propertyName)
                .build();
    }

    public TypeName getViewTypeName() {
        return viewTypeName;
    }

    public TypeName getViewHolderTypeName() {
        return viewHolderTypeName;
    }
}
