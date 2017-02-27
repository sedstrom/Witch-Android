package se.snylt.zipper.processor.binding;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.zipper.processor.TypeUtils;

import static se.snylt.zipper.PropertytUtils.getPropertySetter;

public class OnBindGetAdapterViewDef extends BindActionDef {

    private final String property;

    private final TypeName viewType;

    private final TypeName adapterType;

    private final TypeName valueType;

    public OnBindGetAdapterViewDef(String property, TypeName viewType, TypeName adapterType,  TypeName valueType) {
        this.property = property;
        this.viewType = viewType;
        this.adapterType = adapterType;
        this.valueType = valueType;
    }

    @Override
    public String getNewInstanceJava() {
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewType, "view")
                .addParameter(valueType, "value")
                .returns(void.class)
                .addStatement("(($T)$N.getAdapter()).$N(($T)value)", adapterType, "view", getPropertySetter(property), valueType)
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName
                        .get(TypeUtils.ON_BIND_ACTION, viewType, valueType))
                .addMethod(method)
                .build();

        return anonymous.toString();
    }
}
