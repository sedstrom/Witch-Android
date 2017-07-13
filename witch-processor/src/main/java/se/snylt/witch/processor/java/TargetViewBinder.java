package se.snylt.witch.processor.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.ViewBinder;

import static se.snylt.witch.processor.utils.TypeUtils.ARRAY_LIST;
import static se.snylt.witch.processor.utils.TypeUtils.LIST;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER_FACTORY;
import static se.snylt.witch.processor.utils.TypeUtils.VIEW_BINDER;

public class TargetViewBinder {

    private final List<ViewBinder.Builder> viewBinders;

    private final ClassName binderClassName;

    public TargetViewBinder(List<ViewBinder.Builder> viewBinders, ClassName binderClassName) {
        this.viewBinders = viewBinders;
        this.binderClassName = binderClassName;
    }

    public TypeSpec create() {

        // Class
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(binderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(TARGET_VIEW_BINDER_FACTORY);

        // createBinding
        MethodSpec.Builder createBinding = MethodSpec.methodBuilder("createBinder")
                .addModifiers(Modifier.PUBLIC)
                .returns(TARGET_VIEW_BINDER);

        createBinding.addStatement("$T viewBinders = new $T<>()", ParameterizedTypeName.get(LIST, VIEW_BINDER), ARRAY_LIST);
        createBinding.addCode("\n");

        // All views
        for (ViewBinder.Builder viewBinder : viewBinders) {

            String value = viewBinder.getValueAccessor().accessPropertyString();
            createBinding.addComment("===========================================");
            createBinding.addComment("Binder - " + value + " BEGIN");
            createBinding.addStatement("viewBinders.add($L)", viewBinder.build().newInstance());
            createBinding.addComment("Binder - " + value + " END");
            createBinding.addComment("===========================================");
            createBinding.addCode("\n");
        }

        // Return
        createBinding.addStatement("return new TargetViewBinder($N)", "viewBinders");
        builder.addMethod(createBinding.build());

        return builder.build();
    }
}
