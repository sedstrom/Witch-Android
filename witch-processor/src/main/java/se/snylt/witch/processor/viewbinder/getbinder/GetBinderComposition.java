package se.snylt.witch.processor.viewbinder.getbinder;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.binding.OnBind;

import static se.snylt.witch.processor.utils.TypeUtils.BINDER;

public class GetBinderComposition extends GetBinder {

    private final TypeName targetTypeName;

    List<OnBind> onBinds = new ArrayList<>();

    public GetBinderComposition(TypeName targetTypeName) {
        this.targetTypeName = targetTypeName;
    }

    @Override
    public MethodSpec create() {
        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("getBinder")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .returns(BINDER)
                .addCode("if(binder != null) { return binder; }\n")
                .addStatement("$N = $T.create()", "binder", BINDER);

        for (OnBind onBind : onBinds) {
            builder = builder.addStatement("binder = binder.next($L)", onBind.create());
        }

        builder = builder.addStatement("return binder");

        return builder.build();
    }

    @Override
    public void addOnBind(OnBind onBind) {
        onBinds.add(onBind);
    }
}
