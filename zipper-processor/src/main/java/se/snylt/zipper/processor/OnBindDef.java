package se.snylt.zipper.processor;

public class OnBindDef extends BindActionDef {

    private final String onBindClass;

    public OnBindDef(String onBindClass) {
        this.onBindClass = onBindClass;
    }

    @Override
    public String getNewInstanceJava() {
        return "new " + onBindClass +"()";
    }
}
