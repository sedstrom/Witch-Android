package se.snylt.witch.processor.viewbinder.getview;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

import static se.snylt.witch.processor.utils.TypeUtils.ANDROID_VIEW;

public class GetNoView implements GetView {

    private final TypeName viewHolderTypeName;

    public GetNoView(TypeName viewHolderTypeName) {
        this.viewHolderTypeName = viewHolderTypeName;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewHolderTypeName, "viewHolder")
                .returns(ANDROID_VIEW)
                .addStatement("return null")
                .build();
    }

    @Override
    public TypeName getViewTypeName() {
        return ANDROID_VIEW;
    }

    @Override
    public TypeName getViewHolderTypeName() {
        return viewHolderTypeName;
    }
}
