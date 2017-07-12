package se.snylt.witch.processor.binding;

import com.squareup.javapoet.TypeName;

public class NewInstanceDef extends OnBindDef {

    private final TypeName onBindClass;

    public NewInstanceDef(TypeName onBindClass) {
        this.onBindClass = onBindClass;
    }

    @Override
    public String create() {
        return "new " + onBindClass + "()";
    }
}
