package se.snylt.zipper.processor.binding;

import com.squareup.javapoet.TypeName;

public class OnBindDef extends BindActionDef {

    private final TypeName onBindClass;

    public OnBindDef(TypeName onBindClass) {
        this.onBindClass = onBindClass;
    }

    @Override
    public String getNewInstanceJava() {
        return "new " + onBindClass + "()";
    }
}
