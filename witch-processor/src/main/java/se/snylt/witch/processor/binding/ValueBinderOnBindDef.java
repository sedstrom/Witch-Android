package se.snylt.witch.processor.binding;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.TypeUtils;

public class ValueBinderOnBindDef extends OnBindDef {

    public ValueBinderOnBindDef(){}

    @Override
    public String getNewInstanceJava() {
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.OBJECT, "target")
                .addParameter(TypeName.OBJECT, "value")
                .returns(void.class)
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(TypeUtils.SYNC_ON_BIND, TypeName.OBJECT, TypeName.OBJECT))
                .addMethod(method)
                .build();

        return anonymous.toString();
    }
}
