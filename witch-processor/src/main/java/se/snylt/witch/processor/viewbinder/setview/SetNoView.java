package se.snylt.witch.processor.viewbinder.setview;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import static se.snylt.witch.processor.utils.TypeUtils.ANDROID_VIEW;

public class SetNoView implements SetView {

    private final TypeName viewHolderTypeName;

    public SetNoView(TypeName viewHolderTypeName) {
        this.viewHolderTypeName = viewHolderTypeName;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewHolderTypeName, "viewHolder")
                .addParameter(ANDROID_VIEW, "view")
                .returns(void.class)
                .build();
    }
}
