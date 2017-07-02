package se.snylt.witch.processor.viewbinder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;

import static se.snylt.witch.processor.TypeUtils.VALUE;
import static se.snylt.witch.processor.TypeUtils.VALUE_VIEW_BINDER;

public class ValueViewBinder extends DefaultViewBinder {

    public ValueViewBinder(ClassName viewHolderClassName, PropertyAccessor accessor,
            ClassName targetTypeName, int viewId) {
        super(viewHolderClassName, accessor, targetTypeName, viewId);
    }

    @Override
    public TypeSpec newInstance() {
        return TypeSpec.anonymousClassBuilder("$L, $N", viewId, "binder")
                .addSuperinterface(VALUE_VIEW_BINDER)
                .addMethod(setView())
                .addMethod(getView())
                .addMethod(getValue())
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
                .addStatement("return (($T)(($T)target).$N).take()", VALUE, targetTypeName, valueAccessor.accessPropertyString())
                .build();
    }

    @Override
    MethodSpec isDirty() {
        return MethodSpec.methodBuilder("isDirty")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(boolean.class)
                .addStatement("return (($T)(($T)target).$N).isDirty()", VALUE, targetTypeName, valueAccessor.accessPropertyString())
                .build();
    }
}
