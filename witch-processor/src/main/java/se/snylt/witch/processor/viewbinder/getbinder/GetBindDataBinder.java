package se.snylt.witch.processor.viewbinder.getbinder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.binding.OnBindPropertySetter;

import static se.snylt.witch.processor.utils.TypeUtils.BINDER;

public class GetBindDataBinder extends GetBinder {

    private final Element element;

    private final TypeName targetTypeName;

    private final OnBindPropertySetter onBindPropertySetter;

    public GetBindDataBinder(Element element, TypeName targetTypeName, String propertyName, TypeName viewTypeName, TypeName dataTypeName, TypeMirror dataTypeMirror) {
        this.element = element;
        this.targetTypeName = targetTypeName;
        this.onBindPropertySetter = new OnBindPropertySetter(propertyName, viewTypeName, dataTypeName, dataTypeMirror);
    }

    @Override
    public MethodSpec create() {
        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("getBinder")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(targetTypeName, "target")
                        .returns(BINDER)
                        .addCode("if(binder != null) { return binder; }\n")
                        .addStatement("$N = $T.create()", "binder", BINDER)
                        .addStatement("binder = binder.next($L)", onBindPropertySetter.create());;

        builder = builder.addStatement("return binder");
        return builder.build();
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public TypeName getDataTypeName() {
        return onBindPropertySetter.getDataTypeName();
    }

    @Override
    public TypeMirror getDataTypeMirror() {
        return onBindPropertySetter.getDataTypeMirror();
    }
}
