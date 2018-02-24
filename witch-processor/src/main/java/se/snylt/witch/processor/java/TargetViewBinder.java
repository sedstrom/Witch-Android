package se.snylt.witch.processor.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.viewbinder.ViewBinder;

import static se.snylt.witch.processor.utils.TypeUtils.PRINTER;
import static se.snylt.witch.processor.utils.TypeUtils.ARRAY_LIST;
import static se.snylt.witch.processor.utils.TypeUtils.HASH_MAP_STRINGS;
import static se.snylt.witch.processor.utils.TypeUtils.LIST;
import static se.snylt.witch.processor.utils.TypeUtils.STRING;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER_FACTORY;
import static se.snylt.witch.processor.utils.TypeUtils.VIEW_BINDER;

public class TargetViewBinder {

    private final List<ViewBinder.Builder> viewBinders;

    private final ClassName targetViewBinderClassName;

    private final TypeName targetTypeName;

    private final ClassName viewHolderClassName;

    public TargetViewBinder(List<ViewBinder.Builder> viewBinders, ClassName targetViewBinderClassName, TypeName targetTypeName, ClassName viewHolderClassName) {
        this.viewBinders = viewBinders;
        this.targetViewBinderClassName = targetViewBinderClassName;
        this.targetTypeName = targetTypeName;
        this.viewHolderClassName = viewHolderClassName;
    }

    public TypeSpec create() {

        TypeSpec.Builder targetViewBinderFactory =
                TypeSpec.classBuilder(targetViewBinderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(TARGET_VIEW_BINDER_FACTORY);

        // TODO
        TypeName targetViewBinderType = ParameterizedTypeName.get(TARGET_VIEW_BINDER, targetTypeName, viewHolderClassName);
        MethodSpec.Builder createBinder = MethodSpec.methodBuilder("createBinder")
                .addModifiers(Modifier.PUBLIC)
                .returns(targetViewBinderType);

        TypeName valueTypeName = WildcardTypeName.subtypeOf(Object.class);
        TypeName viewTypeName = WildcardTypeName.subtypeOf(Object.class);
        TypeName viewBinderType = ParameterizedTypeName.get(VIEW_BINDER, targetTypeName, viewTypeName, valueTypeName, viewHolderClassName);
        createBinder.addStatement("$T viewBinders = new $T<>()", ParameterizedTypeName.get(LIST, viewBinderType), ARRAY_LIST);
        createBinder.addCode("\n");

        TypeSpec.Builder targetViewBinder =
                TypeSpec.anonymousClassBuilder("viewBinders")
                        .addSuperinterface(targetViewBinderType);

        // Description
        MethodSpec.Builder describeTarget = MethodSpec.methodBuilder("describeTarget")
                .addParameter(targetTypeName, "target")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T description = \"\"", STRING)
                .returns(STRING);

        // All view binders
        for (ViewBinder.Builder builder: viewBinders) {
            ViewBinder binder = builder.build();
            String value = builder.getPropertyName();
            createBinder.addComment("===========================================");
            createBinder.addComment("Binder - " + value + " BEGIN");
            createBinder.addStatement("viewBinders.add($L)", binder.newInstance());

            String valueName = binder.getValueName();
            String accessValue = binder.getAccessValue();
            describeTarget.addStatement("description += \"$N : \" + \"\" + $N", valueName, accessValue);
            describeTarget.addStatement("description += \"\\n\"");

            createBinder.addComment("Binder - " + value + " END");
            createBinder.addComment("===========================================");
            createBinder.addCode("\n");
        }

        describeTarget.addStatement("return description");
        targetViewBinder.addMethod(describeTarget.build());

        // Return
        createBinder.addStatement("return $L", targetViewBinder.build());
        targetViewBinderFactory.addMethod(createBinder.build());

        return targetViewBinderFactory.build();
    }
}
