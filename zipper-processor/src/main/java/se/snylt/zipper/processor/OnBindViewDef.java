package se.snylt.zipper.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class OnBindViewDef extends BindActionDef {

    private final String property;

    private final TypeName viewType;

    private final TypeName valueType;

    public OnBindViewDef(String property, TypeName viewType, TypeName valueType) {
        this.property = property;
        this.viewType = viewType;
        this.valueType = valueType;
    }

    @Override
    public String getNewInstanceJava() {
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewType, "view")
                .addParameter(Object.class, "value")
                .returns(void.class)
                .addStatement("$N.$N(($T)value)", "view", getPropertySetter(property), valueType)
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName
                        .get(ClassName.get("se.snylt.zipper.viewbinder.onbind", "OnBind"), viewType))
                .addMethod(method)
                .build();

        return anonymous.toString();
    }
    private static String getPropertySetter(String property) {
        String firstUpperCase = property.toUpperCase().charAt(0) + ((property.length() > 0) ? property.substring(1) : "");
        return "set" + firstUpperCase;
    }

}
