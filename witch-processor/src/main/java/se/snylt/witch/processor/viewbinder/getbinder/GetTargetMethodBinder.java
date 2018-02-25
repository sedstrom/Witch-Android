package se.snylt.witch.processor.viewbinder.getbinder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.processor.utils.TypeUtils;

import static se.snylt.witch.processor.utils.TypeUtils.BINDER;

public class GetTargetMethodBinder extends GetBinder {

    private final Element element;

    private final TypeName targetTypeName;

    private final TypeName viewTypeName;

    private final TypeName dataTypeName;

    private final TypeMirror dataTypeMirror;

    private final String propertyName;

    public GetTargetMethodBinder(Element element, TypeName targetTypeName, TypeName viewTypeName, TypeName dataTypeName, TypeMirror dataTypeMirror, String propertyName) {
        this.element = element;
        this.targetTypeName = targetTypeName;
        this.viewTypeName = viewTypeName;
        this.dataTypeName = dataTypeName;
        this.dataTypeMirror = dataTypeMirror;
        this.propertyName = propertyName;
    }

    @Override
    public MethodSpec create() {

        // Bind viw target method
        MethodSpec method = MethodSpec.methodBuilder("onBind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewTypeName, "view")
                .addParameter(dataTypeName, "value")
                .returns(void.class)
                .addStatement("$N.$N(view, value)", "target", propertyName)
                .build();

        TypeSpec anonymous = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(TypeUtils.SYNC_ON_BIND, viewTypeName, dataTypeName))
                .addMethod(method)
                .build();

        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("getBinder")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(targetTypeName, "target", Modifier.FINAL)
                        .returns(BINDER)
                        .addCode("if(binder != null) { return binder; }\n")
                        .addStatement("$N = $T.create($L)", "binder", BINDER, anonymous)
                        .addStatement("return binder");

        return builder.build();
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
