package se.snylt.zipper.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.zipper.Binding;
import se.snylt.zipper.BindingSpec;

public class BinderFactory {

    private static final ClassName LIST = ClassName.get(List.class);

    private static final TypeName ARRAY_LIST = TypeName.get(ArrayList.class);

    private static final TypeName BINDING_SPEC = TypeName.get(BindingSpec.class);

    public static TypeSpec toJava(List<BindToView> bindToViews, ClassName className) {

        // Class
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(ClassName.get(Binding.class));

        // Header and name
        TypeName listOfBindingSpecs = ParameterizedTypeName.get(LIST, BINDING_SPEC);
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("getBindingSpecs")
                .addModifiers(Modifier.PUBLIC)
                .returns(listOfBindingSpecs);

        // Body
        methodBuilder
                .addStatement("$T bindingSpecs = new $T<>()", listOfBindingSpecs, ARRAY_LIST)
                .addStatement("$T bindActions", LIST);

        // All views
        for (BindToView bindToView : bindToViews) {
            methodBuilder.addComment("Bind " + bindToView.value.getSimpleName());

            // All actions for each view
            methodBuilder.addStatement("bindActions = new $T<>()", ARRAY_LIST);
            for (BindActionDef bindAction : bindToView.bindActions) {
                methodBuilder.addStatement(String.format("bindActions.add(%s)", bindAction.getNewInstanceJava()));
            }

            // Add complete binding spec
            methodBuilder.addStatement("bindingSpecs.add(new $T($L, $S, bindActions))",
                    BINDING_SPEC,
                    bindToView.viewId,
                    bindToView.value.getSimpleName());
        }

        // Return
        methodBuilder.addStatement("return bindingSpecs");

        builder.addMethod(methodBuilder.build());
        return builder.build();
    }
}
