package se.snylt.witch.processor.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.ViewBinderComposition;

import static se.snylt.witch.processor.TypeUtils.ARRAY_LIST;
import static se.snylt.witch.processor.TypeUtils.LIST;
import static se.snylt.witch.processor.TypeUtils.TARGET_VIEW_BINDER;
import static se.snylt.witch.processor.TypeUtils.TARGET_VIEW_BINDER_FACTORY;
import static se.snylt.witch.processor.TypeUtils.VIEW_BINDER;

public class BinderCreatorJavaHelper {

    private final static String VAR_BINDER = "binder";

    public static TypeSpec toJava(List<ViewBinderComposition.Builder> viewBinders, ClassName binderClassName) {

        // Class
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(binderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(TARGET_VIEW_BINDER_FACTORY);

        // createBinding
        MethodSpec.Builder createBinding = MethodSpec.methodBuilder("createBinder")
                .addModifiers(Modifier.PUBLIC)
                .returns(TARGET_VIEW_BINDER);

        createBinding.addStatement("$T bindingSpecs = new $T<>()", ParameterizedTypeName.get(LIST, VIEW_BINDER), ARRAY_LIST);

        // All views
        for (ViewBinderComposition.Builder viewBinder : viewBinders) {

            String value = viewBinder.getValueAccessor().accessPropertyString();
            createBinding.addComment("===========================================");
            createBinding.addComment("Bind " + value + " BEGIN");
            
            createBinding.addCode("\n");
            createBinding.addComment("Create ViewBinder");
            createBinding.addStatement("bindingSpecs.add($L)", viewBinder.build().newInstance());

            createBinding.addComment("Bind " + value + " END");
            createBinding.addComment("===========================================");
            createBinding.addCode("\n");
        }

        // Return
        createBinding.addStatement("return new TargetViewBinder($N)", "bindingSpecs");
        builder.addMethod(createBinding.build());

        return builder.build();
    }
}
