package se.snylt.witch.processor.viewbinder.bind;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.utils.ProcessorUtils;

public class BindTargetMethod extends Bind {

    private final Element element;

    private final TypeName targetTypeName;

    private final String propertyName;

    private final ProcessorUtils.BindSpec bindSpec;

    public BindTargetMethod(Element element, TypeName targetTypeName, String propertyName, ProcessorUtils.BindSpec bindSpec) {
        this.element = element;
        this.targetTypeName = targetTypeName;
        this.propertyName = propertyName;
        this.bindSpec = bindSpec;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .addParameter(bindSpec.getViewTypeName(), "view")
                .addParameter(bindSpec.getDataTypeName(), "value")
                .addParameter(bindSpec.getDataTypeName(), "history")
                .returns(void.class)
                .addStatement(getBindStatement(), "target", propertyName)
                .build();
    }

    private String getBindStatement() {
        switch (bindSpec.getType()) {
            case NO_DATA:
                return "$N.$N(view)";
            case DATA:
                return "$N.$N(view, value)";
            case DATA_WITH_HISTORY:
            default:
                return "$N.$N(view, value, history)";
        }
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public TypeName getDataTypeName() {
        return bindSpec.getDataTypeName();
    }

    @Override
    public TypeMirror getDataTypeMirror() {
        return bindSpec.getDataTypeMirror();
    }
}
