package se.snylt.witch.processor.viewbinder.isdirty;


public class IsDirtyAlways implements IsDirty {

    @Override
    public String isDirty() {
        return "true";
    }
}
