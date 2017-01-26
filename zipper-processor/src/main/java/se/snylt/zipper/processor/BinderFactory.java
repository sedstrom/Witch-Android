package se.snylt.zipper.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import se.snylt.zipper.BindingSpecFactory;
import se.snylt.zipper.BindingSpec;

public class BinderFactory {

    private static final ClassName LIST = ClassName.get(List.class);

    private static final TypeName ARRAY_LIST = TypeName.get(ArrayList.class);

    private static final TypeName BINDING_SPEC = TypeName.get(BindingSpec.class);

    public static TypeSpec toJava(
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
                .addStatement("$T bindingSpecs = new $T<>()", listOfBindingSpecs, ARRAY_LIST)
                .addStatement("$T bindActions", LIST);

        // All views
        for (BindToViewActions bindToViewActions : bindToViewActionses) {
            getBindingSpecs.addComment("Bind " + bindToViewActions.value.getSimpleName());

            // All actions for each view
            getBindingSpecs.addStatement("bindActions = new $T<>()", ARRAY_LIST);
            for (BindActionDef bindAction : bindToViewActions.bindActions) {
                getBindingSpecs.addStatement("bindActions.add($L)", bindAction.getNewInstanceJava());
            }

            // Add complete binding spec
            getBindingSpecs.addStatement("bindingSpecs.add($L)",
                    newBindSpecInstance(
                            bindToViewActions.viewId,
                            bindToViewActions.value.getSimpleName().toString(),
                            viewHolderClassName));
        }

        // Return
        getBindingSpecs.addStatement("return bindingSpecs");
        builder.addMethod(getBindingSpecs.build());

        return builder.build();
    }

    private static TypeSpec newBindSpecInstance(int viewIdParam, String keyParam, ClassName viewHolderClassName) {
        ClassName viewClassName = ClassName.get("android.view", "View");

        MethodSpec getView = MethodSpec.methodBuilder("getView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .returns(viewClassName)
                .addStatement("return (($T)viewHolder).$N", viewHolderClassName, keyParam)
                .build();

        MethodSpec setView = MethodSpec.methodBuilder("setView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "viewHolder")
                .addParameter(Object.class, "view")
                .returns(void.class)
                .addStatement("(($T)viewHolder).$N = ($T)$N", viewHolderClassName, keyParam, viewClassName, "view")
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("$L, $S, $N", viewIdParam, keyParam, "bindActions")
                .addSuperinterface(BINDING_SPEC)
                .addMethod(setView)
                .addMethod(getView)
                .build();

        return anonymous;

    }
}
