package se.snylt.witch.processor.binding;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.utils.TypeUtils;

public class OnBindPropertySetter extends OnBind {

    private final String property;

    private final TypeName viewType;

    private final TypeName dataType;

    private TypeName dataTypeName;

    private TypeMirror dataTypeMirror;

    public OnBindPropertySetter(String property, TypeName viewType, TypeName dataType, TypeMirror dataTypeMirror) {
        this.property = property;
        this.viewType = viewType;
        this.dataType = dataType;
        this.dataTypeMirror = dataTypeMirror;
    }

    @Override
    public String create() {
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewType, "target")
                .addParameter(dataType, "value")
                .returns(void.class)
                .addStatement("$N.$N(value)", "target", getPropertySetter(property))
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(TypeUtils.SYNC_ON_BIND, viewType, dataType))
                .addMethod(method)
                .build();

        return anonymous.toString();
    }

    private static String getPropertySetter(String property) {
        String firstUpperCase = property.toUpperCase().charAt(0) + ((property.length() > 0) ? property.substring(1) : "");
        return "set" + firstUpperCase;
    }

    public TypeName getDataTypeName() {
        return dataTypeName;
    }

    public TypeMirror getDataTypeMirror() {
        return dataTypeMirror;
    }
}
