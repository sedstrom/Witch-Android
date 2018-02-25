package se.snylt.witch.processor.viewbinder.getdata;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public interface GetData extends MethodSpecModule {

    Element getElement();

    TypeMirror getDataTypeMirror();

    TypeName getDataTypeName();

    PropertyAccessor getValueAccessor();

    String describeValue();

}
