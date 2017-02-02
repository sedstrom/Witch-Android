package se.snylt.zipper.processor.binding;

import com.squareup.javapoet.TypeName;

public class NewInstanceDef extends BindActionDef {

    private final TypeName onBindClass;

    public NewInstanceDef(TypeName onBindClass) {
        this.onBindClass = onBindClass;
    }

    @Override
    public String getNewInstanceJava() {
        return "new " + onBindClass + "()";
    }
}
