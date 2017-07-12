package se.snylt.witch.processor.viewbinder.getbinder;


import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.binding.OnBindDef;

import static se.snylt.witch.processor.TypeUtils.BINDER;

public class GetBinderComposition extends GetBinder {

    List<OnBindDef> onBinds = new ArrayList<>();

    @Override
    public MethodSpec create() {
        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("getBinder")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(BINDER)
                .addCode("if(binder != null) { return binder; }\n")
                .addStatement("$N = $T.create()", "binder", BINDER);

        for (OnBindDef onBind : onBinds) {
            builder = builder.addStatement("binder = binder.next($L)", onBind.create());
        }

        builder = builder.addStatement("return binder");

        return builder.build();
    }

    @Override
    public void addOnBind(OnBindDef onBind) {
        onBinds.add(onBind);
    }
}
