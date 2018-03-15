package se.snylt.witch.processor.viewbinder.getdata;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.dataaccessor.DataAccessor;

public class GetTargetData implements GetData {

    private final Element element;

    private final TypeName targetTypeName;

    private final DataAccessor valueAccessor;

    private final TypeName dataTypeName;

    private final TypeMirror dataTypeMirror;

    public GetTargetData(Element element, TypeName targetTypeName, DataAccessor valueAccessor, TypeName dataTypeName, TypeMirror dataTypeMirror) {
        this.element = element;
        this.targetTypeName = targetTypeName;
        this.valueAccessor = valueAccessor;
        this.dataTypeName = dataTypeName;
        this.dataTypeMirror = dataTypeMirror;
    }

    @Override
    public MethodSpec create() {
        return  MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .returns(dataTypeName)
                .addStatement("return target.$N", valueAccessor.accessPropertyString())
                .build();
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public TypeMirror getDataTypeMirror() {
        return dataTypeMirror;
    }

    @Override
    public TypeName getDataTypeName() {
        return dataTypeName;
    }

    @Override
    public DataAccessor getValueAccessor() {
        return valueAccessor;
    }

    @Override
    public String describeValue() {
        return "target." + valueAccessor.accessPropertyString();
    }
}
