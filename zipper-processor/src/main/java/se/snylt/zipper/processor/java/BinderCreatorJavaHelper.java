package se.snylt.zipper.processor.java;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import se.snylt.zipper.processor.valueaccessor.ValueAccessor;
import se.snylt.zipper.processor.binding.BindActionDef;
import se.snylt.zipper.processor.binding.ViewBindingDef;

import static se.snylt.zipper.processor.TypeUtils.ARRAY_LIST;
import static se.snylt.zipper.processor.TypeUtils.BINDER;
import static se.snylt.zipper.processor.TypeUtils.BINDING_CREATOR;
import static se.snylt.zipper.processor.TypeUtils.BIND_ACTION;
import static se.snylt.zipper.processor.TypeUtils.BIND_ACTIONS;
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
        MethodSpec.Builder createBinding = MethodSpec.methodBuilder("createBinder")
                .addModifiers(Modifier.PUBLIC)
                .returns(BINDER);

        createBinding.addStatement("$T bindingSpecs = new $T<>()", ParameterizedTypeName.get(LIST, VIEW_BINDER), ARRAY_LIST);

        createBinding.addStatement("$T bindActions", LIST);

        // All views
        for (ViewBindingDef viewBindingDef : viewActionses) {

            createBinding.addComment("===========================================");
            createBinding.addComment("Bind " + viewBindingDef.value.accessValueString());

            // Add all binding actions
            createBinding.addStatement("bindActions = new $T()", ARRAY_LIST);
            for (BindActionDef bindAction : viewBindingDef.getBindActions()) {
                createBinding.addStatement("bindActions.add($L)", bindAction.getNewInstanceJava());
            }

            // Add complete binding spec
            createBinding.addStatement("bindingSpecs.add($L)",
                    newViewBinderInstance(
                            target,
                            viewBindingDef,
                            viewHolderClassName));
        }

        // Return
        createBinding.addStatement("return new Binder($N)", "bindingSpecs");
        builder.addMethod(createBinding.build());

        return builder.build();
    }

    private static TypeSpec newViewBinderInstance(ClassName targetTypeName, ViewBindingDef viewBindingDef, ClassName viewHolderClassName) {
        ClassName viewClassName = ClassName.get("android.view", "View");
        int viewId = viewBindingDef.viewId;
        ValueAccessor accessor = viewBindingDef.value;

        // Get view
        MethodSpec getView = MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .returns(viewClassName)
                .addStatement("return (($T)viewHolder).$N", viewHolderClassName, accessor.viewHolderFieldName())
                .build();

        // Set view
        MethodSpec setView = MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .addParameter(Object.class, "view")
                .returns(void.class)
                .addStatement("(($T)viewHolder).$N = ($T)$N", viewHolderClassName, accessor.viewHolderFieldName(), viewClassName, "view")
                .build();

        // Value
        MethodSpec getValue = MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(Object.class)
                .addStatement("return (($T)target).$N", targetTypeName, accessor.accessValueString())
                .build();

        // Always bind
        MethodSpec alwaysBind = MethodSpec.methodBuilder("isAlwaysBind")
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addStatement("return $L", viewBindingDef.isAlwaysBind())
                .build();

        // Mods
        TypeName listOfMods = ArrayTypeName.of(BIND_ACTION);
        MethodSpec.Builder getModActions = MethodSpec.methodBuilder("getModActions")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "mod")
                .returns(listOfMods);

        for(Element e: viewBindingDef.getMods()){
            getModActions.addCode("if(mod instanceof $T) {\n", e.asType());
            getModActions.addStatement("return (($T)mod).$N", e.asType(), accessor.accessValueString());
            getModActions.addCode("}\n");
        }

        getModActions.addStatement("return null");

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("$L, $S, new $T($N)", viewId, accessor.viewHolderFieldName(), BIND_ACTIONS, "bindActions")
                .addSuperinterface(VIEW_BINDER)
                .addMethod(setView)
                .addMethod(getView)
                .addMethod(getValue)
                .addMethod(alwaysBind)
                .addMethod(getModActions.build())
                .build();

        return anonymous;
    }
}
