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

    private final ProcessorUtils.BindMethod bindMethod;

    public BindTargetMethod(Element element, TypeName targetTypeName, String propertyName, ProcessorUtils.BindMethod bindMethod) {
        this.element = element;
        this.targetTypeName = targetTypeName;
        this.propertyName = propertyName;
        this.bindMethod = bindMethod;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .addParameter(bindMethod.getViewTypeName(), "view")
                .addParameter(bindMethod.getDataTypeName(), "data")
                .addParameter(bindMethod.getDataTypeName(), "history")
                .returns(void.class)
                .addStatement(getBindStatement(), "target", propertyName)
                .build();
    }

    private String getBindStatement() {
        switch (bindMethod.getType()) {
            case EMPTY:
                return "$N.$N()";
            case VIEW:
                return "$N.$N(view)";
            case VIEW_DATA:
                return "$N.$N(view, data)";
            case VIEW_DATA_HISTORY:
                return "$N.$N(view, data, history)";
            case DATA:
                return "$N.$N(data)";
            case DATA_HISTORY:
            default:
                return "$N.$N(data, history)";
        }
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public TypeName getDataTypeName() {
        return bindMethod.getDataTypeName();
    }

    @Override
    public TypeMirror getDataTypeMirror() {
        return bindMethod.getDataTypeMirror();
    }
}
