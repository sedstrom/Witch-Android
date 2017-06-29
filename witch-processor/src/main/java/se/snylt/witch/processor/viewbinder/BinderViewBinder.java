package se.snylt.witch.processor.viewbinder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.ValueAccessor;

import static se.snylt.witch.processor.TypeUtils.BINDER;

public class BinderViewBinder extends DefaultViewBinder {

    private final String binderAccessor;

    public BinderViewBinder(ClassName viewHolderClassName, ValueAccessor valueAccessor, ClassName targetClassName, int viewId, String binderAccessor) {
        super(viewHolderClassName, valueAccessor, targetClassName, viewId);
        this.binderAccessor = binderAccessor;
    }

    @Override
    MethodSpec getBinder() {
        return MethodSpec.methodBuilder("getBinder")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(BINDER)
                .addStatement("return (($T)target).$N", targetTypeName, binderAccessor)
                .build();
    }
}
