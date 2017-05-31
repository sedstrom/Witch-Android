package se.snylt.witch.processor.viewbinder;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import se.snylt.witch.processor.valueaccessor.ValueAccessor;

public abstract class ViewBinder {

    public boolean isAlwaysBind;

    public ValueAccessor valueAccessor;

    public abstract TypeSpec newInstance();

    abstract MethodSpec getView();

    abstract MethodSpec setView();

    abstract MethodSpec getValue();

    abstract MethodSpec isAlwaysBind();

    abstract MethodSpec getBinder();

    abstract MethodSpec isDirty();

}
