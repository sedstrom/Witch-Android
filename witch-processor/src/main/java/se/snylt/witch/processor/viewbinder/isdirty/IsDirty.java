package se.snylt.witch.processor.viewbinder.isdirty;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Modifier;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public abstract class IsDirty implements MethodSpecModule {

    private final TypeName targetTypeName;

    private boolean bindNull = false;

    protected IsDirty(TypeName targetTypeName) {
        this.targetTypeName = targetTypeName;
    }

    @Override
    public MethodSpec create() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("isDirty")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .returns(boolean.class);
        if (!bindNull) {
            builder.addCode("if(getData(target) == null) { return false; } else { return $N; }", getIsDirty());
        } else {
            builder.addCode("return $N;", getIsDirty());
        }

        return builder.build();
    }

    public void setBindNull(boolean bindNull) {
        this.bindNull = bindNull;
    }

    abstract String getIsDirty();
}
