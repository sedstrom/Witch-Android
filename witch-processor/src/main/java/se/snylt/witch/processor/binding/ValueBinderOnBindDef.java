package se.snylt.witch.processor.binding;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.TypeUtils;

public class ValueBinderOnBindDef extends OnBindDef {

    private final TypeName viewType;

    private final TypeName valueType;

    private final TypeName valueBinderType;

    private final TypeName binderType;

    public ValueBinderOnBindDef(TypeName viewType, TypeName valueType, TypeName binderType, TypeName valueBinderType) {
        this.viewType = viewType;
        this.valueType = valueType;
        this.binderType = binderType;
        this.valueBinderType = valueBinderType;
    }

    @Override
    public String getNewInstanceJava() {
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewType, "target")
                .addParameter(valueBinderType, "valueBinder")
                .returns(void.class)
                .addStatement("$T binder = valueBinder.getBinder()", binderType)
                .addStatement("$T value = valueBinder.getValue()", valueType)
                .addStatement("binder.bind(target, value)")
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(TypeUtils.SYNC_ON_BIND, viewType, valueBinderType))
                .addMethod(method)
                .build();

        return anonymous.toString();
    }
}
