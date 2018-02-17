package se.snylt.witch.processor.viewbinder.setview;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

public class SetViewHolderView implements SetView {

    private final TypeName viewTypeName;

    private final TypeName viewHolderTypeName;

    private final String viewName;

    public SetViewHolderView(TypeName viewTypeName, TypeName viewHolderTypeName, String viewName) {
        this.viewTypeName = viewTypeName;
        this.viewHolderTypeName = viewHolderTypeName;
        this.viewName = viewName;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewHolderTypeName, "viewHolder")
                .addParameter(viewTypeName, "view")
                .returns(void.class)
                .addStatement("viewHolder.$N = $N", viewName, "view")
                .build();
    }
}
