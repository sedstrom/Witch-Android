package se.snylt.witch.processor.viewbinder.getbinder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.utils.TypeUtils;

import static se.snylt.witch.processor.utils.TypeUtils.BINDER;

public class GetMethodBinder extends GetBinder {

    private final TypeName targetTypeName;

    private final TypeName viewTypeName;

    private final TypeName valueTypeName;

    private final String propertyName;

    public GetMethodBinder(TypeName targetTypeName, TypeName viewTypeName, TypeName valueTypeName, String propertyName) {
        this.targetTypeName = targetTypeName;
        this.viewTypeName = viewTypeName;
        this.valueTypeName = valueTypeName;
        this.propertyName = propertyName;
    }

    @Override
    public MethodSpec create() {

        // Bind viw target method
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewTypeName, "view")
                .addParameter(valueTypeName, "value")
                .returns(void.class)
                .addStatement("$N.$N(view, value)", "target", propertyName)
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(TypeUtils.SYNC_ON_BIND, viewTypeName, valueTypeName))
                .addMethod(method)
                .build();

        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("getBinder")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(targetTypeName, "target", Modifier.FINAL)
                        .returns(BINDER)
                        .addCode("if(binder != null) { return binder; }\n")
                        .addStatement("$N = $T.create($L)", "binder", BINDER, anonymous)
                        .addStatement("return binder");

        return builder.build();
    }

    @Override
    public void addOnBind(OnBind onBind) {
        throw new IllegalAccessError("Not supported");
    }
}
