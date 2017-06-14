package se.snylt.witch.processor.viewbinder.isdirty;


public class IsDirtyNever implements IsDirty {

    @Override
    public String isDirty() {
        return "false";
    }
}
