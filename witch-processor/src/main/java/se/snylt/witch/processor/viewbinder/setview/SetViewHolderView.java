package se.snylt.witch.processor.viewbinder.setview;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class SetViewHolderView implements MethodSpecModule {

    private final TypeName viewTypeName;

    private final TypeName viewHolderTypeName;

    private final String propertyName;

    public SetViewHolderView(TypeName viewTypeName, TypeName viewHolderTypeName, String propertyName) {
        this.viewTypeName = viewTypeName;
        this.viewHolderTypeName = viewHolderTypeName;
        this.propertyName = propertyName;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewHolderTypeName, "viewHolder")
                .addParameter(viewTypeName, "view")
                .returns(void.class)
                .addStatement("viewHolder.$N = $N", propertyName, "view")
                .build();
    }
}
