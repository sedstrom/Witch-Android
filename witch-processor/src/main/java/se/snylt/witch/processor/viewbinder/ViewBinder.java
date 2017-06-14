package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import se.snylt.witch.processor.valueaccessor.ValueAccessor;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirtyIfNotEquals;

public abstract class ViewBinder {

    public IsDirty isDirty = new IsDirtyIfNotEquals();

    public ValueAccessor valueAccessor;

    public abstract TypeSpec newInstance();

    abstract MethodSpec getView();

    abstract MethodSpec setView();

    abstract MethodSpec getValue();

    abstract MethodSpec getBinder();

    abstract MethodSpec isDirty();

}
