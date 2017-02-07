package se.snylt.zipper.processor.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.zipper.processor.binding.BindActionDef;
import se.snylt.zipper.processor.binding.ViewBindingDef;

import static se.snylt.zipper.processor.TypeUtils.ARRAY_LIST;
import static se.snylt.zipper.processor.TypeUtils.BINDING;
import static se.snylt.zipper.processor.TypeUtils.BINDING_CREATOR;
import static se.snylt.zipper.processor.TypeUtils.LIST;
import static se.snylt.zipper.processor.TypeUtils.VIEW_BINDER;

public class BinderCreatorJavaHelper {

    public static TypeSpec toJava(
            ClassName target,
            List<ViewBindingDef> viewActionses,
            ClassName binderClassName,
            ClassName viewHolderClassName) {

        // Class
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(binderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(BINDING_CREATOR);

        // createBinding
        MethodSpec.Builder createBinding = MethodSpec.methodBuilder("createBinding")
                // .addParameter(ON_UNBIND_LISTENER, "listener")
                .addModifiers(Modifier.PUBLIC)
                .returns(BINDING);

        createBinding.addStatement("$T bindingSpecs = new $T<>()", ParameterizedTypeName.get(LIST, VIEW_BINDER), ARRAY_LIST);

        createBinding.addStatement("$T bindActions", LIST);

        // All views
        for (ViewBindingDef viewBindingDef : viewActionses) {

            createBinding.addComment("===========================================");
            createBinding.addComment("Bind " + viewBindingDef.value.getSimpleName());

            // Add all binding actions
            createBinding.addStatement("bindActions = new $T()", ARRAY_LIST);
            for (BindActionDef bindAction : viewBindingDef.getBindActions()) {
                createBinding.addStatement("bindActions.add($L)", bindAction.getNewInstanceJava());
            }

            // Add complete binding spec
            createBinding.addStatement("bindingSpecs.add($L)",
                    newBindSpecInstance(
                            target,
                            viewBindingDef,
                            viewHolderClassName));
        }

        // Return
        createBinding.addStatement("return new Binding($N)", "bindingSpecs"); // , "listener");
        builder.addMethod(createBinding.build());

        return builder.build();
    }

    private static TypeSpec newBindSpecInstance(ClassName targetTypeName, ViewBindingDef viewBindingDef, ClassName viewHolderClassName) {
        ClassName viewClassName = ClassName.get("android.view", "View");
        String valueKey =  viewBindingDef.value.getSimpleName().toString();
        int viewId = viewBindingDef.viewId;

        // Get view
        MethodSpec getView = MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .returns(viewClassName)
                .addStatement("return (($T)viewHolder).$N", viewHolderClassName, valueKey)
                .build();

        // Set view
        MethodSpec setView = MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .addParameter(Object.class, "view")
                .returns(void.class)
                .addStatement("(($T)viewHolder).$N = ($T)$N", viewHolderClassName, valueKey, viewClassName, "view")
                .build();

        // Value
        MethodSpec getValue = MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(Object.class)
                .addStatement("return (($T)target).$N", targetTypeName, valueKey)
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("$L, $S, $N", viewId, valueKey, "bindActions")
                .addSuperinterface(VIEW_BINDER)
                .addMethod(setView)
                .addMethod(getView)
                .addMethod(getValue)
                .build();

        return anonymous;
    }
}
