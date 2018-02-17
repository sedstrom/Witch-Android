package se.snylt.witch.processor.viewbinder.getvalue;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public interface GetValue extends MethodSpecModule {

    TypeName getValueTypeName();

    PropertyAccessor getValueAccessor();

    String describeValue();

}
