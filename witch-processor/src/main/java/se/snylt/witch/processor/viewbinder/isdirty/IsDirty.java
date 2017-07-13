package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public abstract class IsDirty implements MethodSpecModule {

    private final TypeName targetTypeName;

    protected IsDirty(TypeName targetTypeName) {
        this.targetTypeName = targetTypeName;
    }

    @Override
    public MethodSpec create() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("isDirty")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .returns(boolean.class);
        return addReturnStatement(builder).build();
    }

    abstract MethodSpec.Builder addReturnStatement(MethodSpec.Builder builder);
}
