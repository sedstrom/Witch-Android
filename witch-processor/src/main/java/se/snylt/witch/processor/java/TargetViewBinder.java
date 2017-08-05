package se.snylt.witch.processor.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.ViewBinder;

import static se.snylt.witch.processor.utils.TypeUtils.ANDROID_PRINTER;
import static se.snylt.witch.processor.utils.TypeUtils.ARRAY_LIST;
import static se.snylt.witch.processor.utils.TypeUtils.HASH_MAP_STRINGS;
import static se.snylt.witch.processor.utils.TypeUtils.LIST;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER_FACTORY;
import static se.snylt.witch.processor.utils.TypeUtils.VIEW_BINDER;

public class TargetViewBinder {

    private final List<ViewBinder.Builder> viewBinders;

    private final ClassName binderClassName;

    private final TypeName targetTypeName;

    public TargetViewBinder(List<ViewBinder.Builder> viewBinders, ClassName binderClassName, TypeName targetTypeName) {
        this.viewBinders = viewBinders;
        this.binderClassName = binderClassName;
        this.targetTypeName = targetTypeName;
    }

    public TypeSpec create() {

        TypeSpec.Builder builder =
                TypeSpec.classBuilder(binderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(TARGET_VIEW_BINDER_FACTORY);

        MethodSpec.Builder createBinder = MethodSpec.methodBuilder("createBinder")
                .addModifiers(Modifier.PUBLIC)
                .returns(TARGET_VIEW_BINDER);

        createBinder.addStatement("$T viewBinders = new $T<>()", ParameterizedTypeName.get(LIST, VIEW_BINDER), ARRAY_LIST);
        createBinder.addCode("\n");

        // All viewbinders
        for (ViewBinder.Builder viewBinder: viewBinders) {
            String value = viewBinder.getValueAccessor().accessPropertyString();
            createBinder.addComment("===========================================");
            createBinder.addComment("Binder - " + value + " BEGIN");
            createBinder.addStatement("viewBinders.add($L)", viewBinder.build().newInstance());
            createBinder.addComment("Binder - " + value + " END");
            createBinder.addComment("===========================================");
            createBinder.addCode("\n");
        }

        // Printer
        TypeSpec.Builder androidPrinter = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(ANDROID_PRINTER, targetTypeName));

        MethodSpec.Builder describeTarget = MethodSpec.methodBuilder("describeTarget")
                .addParameter(targetTypeName, "target")
                .addParameter(HASH_MAP_STRINGS, "out")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID);

        for(ViewBinder.Builder viewBinder: viewBinders) {
            String property = viewBinder.getValueAccessor().accessPropertyString();
            describeTarget.addStatement("out.put(\"$N\", \"\" + target.$N)", property, property);
        }
        androidPrinter.addMethod(describeTarget.build());

        // Return
        createBinder.addStatement("return new TargetViewBinder($N, $L)", "viewBinders", androidPrinter.build());
        builder.addMethod(createBinder.build());

        return builder.build();
    }
}
