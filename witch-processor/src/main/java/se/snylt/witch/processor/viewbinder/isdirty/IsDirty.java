package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public abstract class IsDirty implements MethodSpecModule {

    @Override
    public MethodSpec create() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("isDirty")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(boolean.class);
        return addReturnStatement(builder).build();
    }

    abstract MethodSpec.Builder addReturnStatement(MethodSpec.Builder builder);
}
