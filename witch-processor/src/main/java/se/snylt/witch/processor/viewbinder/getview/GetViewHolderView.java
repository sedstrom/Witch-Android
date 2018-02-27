package se.snylt.witch.processor.viewbinder.getview;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class GetViewHolderView implements MethodSpecModule {

    private final TypeName viewTypeName = ClassName.get("android.view", "View");

    private final TypeName viewHolderTypeName;

    private final String propertyName;

    public GetViewHolderView(TypeName viewHolderTypeName, String propertyName) {
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
