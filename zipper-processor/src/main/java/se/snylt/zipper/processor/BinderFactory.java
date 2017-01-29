package se.snylt.zipper.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.zipper.BindingSpec;
import se.snylt.zipper.BindingSpecFactory;
import se.snylt.zipper.BindingViewHolder;

public class BinderFactory {

    private static final ClassName LIST = ClassName.get(List.class);

    private static final TypeName ARRAY_LIST = TypeName.get(ArrayList.class);

    private static final TypeName BINDING_SPEC = TypeName.get(BindingSpec.class);

    public static TypeSpec toJava(
            ClassName target,
            List<BindToViewActions> bindToViewActionses,
            ClassName binderClassName,
            ClassName viewHolderClassName) {

        // Class
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(binderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(ClassName.get(BindingSpecFactory.class));

        // getBindingSpecs
        TypeName listOfBindingSpecs = ParameterizedTypeName.get(LIST, BINDING_SPEC);
        MethodSpec.Builder getBindingSpecs = MethodSpec.methodBuilder("getBindingSpecs")
                .addModifiers(Modifier.PUBLIC)
                .returns(listOfBindingSpecs);

        getBindingSpecs
                .addStatement("$T bindingSpecs = new $T<>()", listOfBindingSpecs, ARRAY_LIST);

        getBindingSpecs.addStatement("$T bindActions", LIST);

        // All views
        for (BindToViewActions bindToViewActions : bindToViewActionses) {
            getBindingSpecs.addComment("Bind " + bindToViewActions.value.getSimpleName());

            // Add all binding actions
            getBindingSpecs.addStatement("bindActions = new $T()", ARRAY_LIST);
            for (BindActionDef bindAction : bindToViewActions.bindActions) {
                getBindingSpecs.addStatement("bindActions.add($L)", bindAction.getNewInstanceJava());
            }

            // Add complete binding spec
            getBindingSpecs.addStatement("bindingSpecs.add($L)",
                    newBindSpecInstance(
                            target,
                            bindToViewActions,
                            viewHolderClassName));
        }

        // Return
        getBindingSpecs.addStatement("return bindingSpecs");
        builder.addMethod(getBindingSpecs.build());

        return builder.build();
    }

    private static TypeSpec newBindSpecInstance(ClassName targetTypeName, BindToViewActions bindToViewActions, ClassName viewHolderClassName) {
        ClassName viewClassName = ClassName.get("android.view", "View");
        String valueKey =  bindToViewActions.value.getSimpleName().toString();
        int viewId = bindToViewActions.viewId;

        // Get view
        MethodSpec getView = MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(BindingViewHolder.class, "viewHolder")
                .returns(viewClassName)
                .addStatement("return (($T)viewHolder).$N", viewHolderClassName, valueKey)
                .build();

        // Set view
        MethodSpec setView = MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(BindingViewHolder.class, "viewHolder")
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
                .addSuperinterface(BINDING_SPEC)
                .addMethod(setView)
                .addMethod(getView)
                .addMethod(getValue)
                .build();

        return anonymous;
    }
}
