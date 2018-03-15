package se.snylt.witch.processor.viewbinder.bind;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class BindTargetMethod extends Bind {

    private final Element element;

    private final TypeName targetTypeName;

    private final TypeName viewTypeName;

    private final TypeName dataTypeName;

    private final TypeMirror dataTypeMirror;

    private final String propertyName;

    private final boolean bindWithHistory;

    public BindTargetMethod(Element element, TypeName targetTypeName, TypeName viewTypeName, TypeName dataTypeName, TypeMirror dataTypeMirror, String propertyName, boolean bindWithHistory) {
        this.element = element;
        this.targetTypeName = targetTypeName;
        this.viewTypeName = viewTypeName;
        this.dataTypeName = dataTypeName;
        this.dataTypeMirror = dataTypeMirror;
        this.propertyName = propertyName;
        this.bindWithHistory = bindWithHistory;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .addParameter(viewTypeName, "view")
                .addParameter(dataTypeName, "value")
                .addParameter(dataTypeName, "history")
                .returns(void.class)
                .addStatement(getBindStatement(), "target", propertyName)
                .build();
    }

    private String getBindStatement() {
        if (bindWithHistory) {
            return "$N.$N(view, value, history)";
        } else {
            return "$N.$N(view, value)";
        }

    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public TypeName getDataTypeName() {
        return dataTypeName;
    }

    @Override
    public TypeMirror getDataTypeMirror() {
        return dataTypeMirror;
    }
}
