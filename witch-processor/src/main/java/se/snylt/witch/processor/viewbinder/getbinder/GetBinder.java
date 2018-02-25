package se.snylt.witch.processor.viewbinder.getbinder;

import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public abstract class GetBinder implements MethodSpecModule {

    public abstract Element getElement();

    public abstract TypeName getDataTypeName();

    public abstract TypeMirror getDataTypeMirror();

}
