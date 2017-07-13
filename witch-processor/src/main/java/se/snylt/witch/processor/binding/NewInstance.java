package se.snylt.witch.processor.binding;

import com.squareup.javapoet.TypeName;

public class NewInstance extends OnBind {

    private final TypeName onBindClass;

    public NewInstance(TypeName onBindClass) {
        this.onBindClass = onBindClass;
    }

    @Override
    public String create() {
        return "new " + onBindClass + "()";
    }
}
