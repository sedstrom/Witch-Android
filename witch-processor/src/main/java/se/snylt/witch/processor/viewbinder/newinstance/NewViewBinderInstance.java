package se.snylt.witch.processor.viewbinder.newinstance;

import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import se.snylt.witch.processor.viewbinder.TypeSpecModule;

import static se.snylt.witch.processor.utils.TypeUtils.VIEW_BINDER;


public class NewViewBinderInstance implements TypeSpecModule {

    private final int viewId;

    private final TypeName viewTypeName;

    private final TypeName viewHolderTypeName;

    private final TypeName targetTypeName;

    private final TypeName dataTypeName;

    public NewViewBinderInstance(int viewId, TypeName viewTypeName, TypeName viewHolderTypeName,
            TypeName targetTypeName, TypeName dataTypeName) {
        this.viewId = viewId;
        this.viewTypeName = viewTypeName;
        this.viewHolderTypeName = viewHolderTypeName;
        this.targetTypeName = targetTypeName;
        this.dataTypeName = dataTypeName;
    }

    @Override
    public TypeSpec.Builder builder() {
        return TypeSpec.anonymousClassBuilder("$L", viewId)
                .addSuperinterface(ParameterizedTypeName.get(VIEW_BINDER, targetTypeName,
                        viewTypeName, dataTypeName, viewHolderTypeName));

    }
}
