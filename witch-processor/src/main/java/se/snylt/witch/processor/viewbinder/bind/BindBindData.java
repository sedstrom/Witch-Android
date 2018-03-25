package se.snylt.witch.processor.viewbinder.bind;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class BindBindData extends Bind {

    private final String property;

    private final TypeName targetTypeName;

    private final TypeName viewTypeName;

    private TypeName dataTypeName;

    private TypeMirror dataTypeMirror;

    public BindBindData(String property, TypeName targetTypeName, TypeName viewTypeName,
                        TypeName dataTypeName, TypeMirror dataTypeMirror) {
        this.property = property;
        this.targetTypeName = targetTypeName;
        this.viewTypeName = viewTypeName;
        this.dataTypeName = dataTypeName;
        this.dataTypeMirror = dataTypeMirror;
    }

    @Override
    public MethodSpec create() {
        return  MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .addParameter(viewTypeName, "view")
                .addParameter(dataTypeName, "data")
                .addParameter(dataTypeName, "history")
                .returns(void.class)
                .addStatement("$N.$N(data)", "view", getPropertySetter(property))
                .build();
    }

    private static String getPropertySetter(String property) {
        String firstUpperCase = property.toUpperCase().charAt(0) + ((property.length() > 0)
                ? property.substring(1) : "");
        return "set" + firstUpperCase;
    }

    @Override
    public Element getElement() {
        return null;
    }

    @Override
    public TypeName getDataTypeName() {
        return dataTypeName;
    }

    @Override
    public TypeMirror getDataTypeMirror() {
        return dataTypeMirror;
    }
}
