package se.snylt.witch.processor.viewbinder.getview;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Modifier;

public class GetViewHolderView implements GetView {

    private final TypeName viewTypeName;

    private final TypeName viewHolderTypeName;

    private final String viewName;

    public GetViewHolderView(TypeName viewTypeName, TypeName viewHolderTypeName, String viewName) {
        this.viewTypeName = viewTypeName;
        this.viewHolderTypeName = viewHolderTypeName;
        this.viewName = viewName;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewHolderTypeName, "viewHolder")
                .returns(viewTypeName)
                .addStatement("return ($T) viewHolder.$N", viewTypeName, viewName)
                .build();
    }

    public TypeName getViewTypeName() {
        return viewTypeName;
    }

    public TypeName getViewHolderTypeName() {
        return viewHolderTypeName;
    }
}
