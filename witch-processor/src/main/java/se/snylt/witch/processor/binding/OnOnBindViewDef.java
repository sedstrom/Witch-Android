package se.snylt.witch.processor.binding;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.TypeUtils;

public class OnOnBindViewDef extends OnBindDef {

    private final String property;

    private final TypeName viewType;

    private final TypeName valueType;

    public OnOnBindViewDef(String property, TypeName viewType, TypeName valueType) {
        this.property = property;
        this.viewType = viewType;
        this.valueType = valueType;
    }

    @Override
    public String create() {
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewType, "target")
                .addParameter(valueType, "value")
                .returns(void.class)
                .addStatement("$N.$N(value)", "target", getPropertySetter(property))
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(TypeUtils.SYNC_ON_BIND, viewType, valueType))
                .addMethod(method)
                .build();

        return anonymous.toString();
    }

    private static String getPropertySetter(String property) {
        String firstUpperCase = property.toUpperCase().charAt(0) + ((property.length() > 0) ? property.substring(1) : "");
        return "set" + firstUpperCase;
    }

}
