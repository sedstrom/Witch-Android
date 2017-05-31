package se.snylt.witch.processor.viewbinder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.ValueAccessor;

import static se.snylt.witch.processor.TypeUtils.BINDER;
import static se.snylt.witch.processor.TypeUtils.DEFAULT_VIEW_BINDER;

public class DefaultViewBinder extends ViewBinderBase {

    protected final ClassName targetTypeName;

    public DefaultViewBinder(ClassName viewHolderClassName, ValueAccessor accessor,
            ClassName targetTypeName, int viewId) {
        super(viewHolderClassName, accessor, viewId);
        this.targetTypeName = targetTypeName;
    }

    @Override
    public TypeSpec newInstance() {
        return TypeSpec.anonymousClassBuilder("$L, $N", viewId, "binder")
                .addSuperinterface(DEFAULT_VIEW_BINDER)
                .addMethod(setView())
                .addMethod(getView())
                .addMethod(getValue())
                .addMethod(isAlwaysBind())
                .addMethod(isDirty())
                .addMethod(getBinder())
                .build();
    }

    @Override
    MethodSpec getValue() {
        return MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(Object.class)
                .addStatement("return (($T)target).$N", targetTypeName, valueAccessor.accessValueString())
                .build();
    }

    @Override
    MethodSpec getBinder() {
        return MethodSpec.methodBuilder("getBinder")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(BINDER)
                .addStatement("return binder")
                .build();
    }

    @Override
    MethodSpec isDirty() {
        return MethodSpec.methodBuilder("isDirty")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(boolean.class)
                .addStatement("return super.isDirty(target)")
                .build();
    }
}
