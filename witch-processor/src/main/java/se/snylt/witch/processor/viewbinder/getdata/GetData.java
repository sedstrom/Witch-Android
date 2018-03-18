package se.snylt.witch.processor.viewbinder.getdata;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.dataaccessor.DataAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public interface GetData extends MethodSpecModule {

    Element getElement();

    String describeData();

    String getDataName();
}
