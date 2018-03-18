package se.snylt.witch.processor.viewbinder.getdata;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.dataaccessor.DataAccessor;

import static se.snylt.witch.processor.utils.TypeUtils.DIFF_VALUE_NO_HISTORY;

public class GetNoData implements GetData {

    private final Element element;

    private final TypeName targetTypeName;

    private final TypeName dataTypeName = ClassName.OBJECT;

    public GetNoData(Element element, TypeName targetTypeName) {
        this.element = element;
        this.targetTypeName = targetTypeName;
    }

    @Override
    public MethodSpec create() {
        return  MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .returns(dataTypeName)
                .addStatement("return $L", DIFF_VALUE_NO_HISTORY)
                .build();
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String describeData() {
        return null;
    }

    @Override
    public String getDataName() {
        return null;
    }
}
