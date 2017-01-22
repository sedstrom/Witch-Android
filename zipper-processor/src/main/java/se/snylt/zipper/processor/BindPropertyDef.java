package se.snylt.zipper.processor;

public class BindPropertyDef extends BindActionDef {

    private final static String className = "se.snylt.zipper.viewbinder.onbind.PropertyOnBind";

    private final String bindProperty;

    public BindPropertyDef(String bindProperty) {
        this.bindProperty = bindProperty;
    }

    @Override
    public String getNewInstanceJava() {
        return String.format("new %s(\"%s\")", className, bindProperty);
    }
}
