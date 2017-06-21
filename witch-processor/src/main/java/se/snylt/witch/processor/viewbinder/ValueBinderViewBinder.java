package se.snylt.witch.processor.viewbinder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.ValueAccessor;

import static se.snylt.witch.processor.TypeUtils.BINDER;
import static se.snylt.witch.processor.TypeUtils.VALUE_BINDER;
import static se.snylt.witch.processor.TypeUtils.VALUE_BINDER_VIEW_BINDER;

public class ValueBinderViewBinder extends ValueViewBinder {

    public ValueBinderViewBinder(ClassName viewHolderClassName, ValueAccessor accessor,
            ClassName targetTypeName, int viewId) {
        super(viewHolderClassName, accessor, targetTypeName, viewId);
    }

    @Override
    public TypeSpec newInstance() {
        return TypeSpec.anonymousClassBuilder("$L", viewId)
                .addSuperinterface(VALUE_BINDER_VIEW_BINDER)
                .addMethod(setView())
                .addMethod(getView())
                .addMethod(isDirty())
                .addMethod(getValueBinder())
                .build();
    }

    @Override
    MethodSpec isDirty() {
        return MethodSpec.methodBuilder("isDirty")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(boolean.class)
                .addStatement("return (($T)(($T)target).$N).isDirty()", VALUE_BINDER, targetTypeName, valueAccessor.accessValueString())
                .build();
    }

    @Override
    MethodSpec getBinder() {
        return MethodSpec.methodBuilder("getBinder")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(BINDER)
                .addStatement("return (($T)(($T)target).$N).getBinder()", VALUE_BINDER, targetTypeName, valueAccessor.accessValueString())
                .build();
    }

    MethodSpec getValueBinder() {
        return MethodSpec.methodBuilder("getValueBinder")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(VALUE_BINDER)
                .addStatement("return ($T)(($T)target).$N", VALUE_BINDER, targetTypeName, valueAccessor.accessValueString())
                .build();
    }
}
