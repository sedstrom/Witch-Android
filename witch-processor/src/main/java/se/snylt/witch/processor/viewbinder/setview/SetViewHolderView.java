package se.snylt.witch.processor.viewbinder.setview;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class SetViewHolderView implements MethodSpecModule {

    private final TypeName viewTypeName = ClassName.get("android.view", "View");

    private final TypeName viewHolderTypeName;

    private final String propertyName;

    public SetViewHolderView(TypeName viewHolderTypeName, String propertyName) {
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
