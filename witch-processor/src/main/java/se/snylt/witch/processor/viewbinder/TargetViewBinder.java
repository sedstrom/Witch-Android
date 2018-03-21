package se.snylt.witch.processor.viewbinder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import se.snylt.witch.processor.WitchException;
import se.snylt.witch.processor.utils.ProcessorUtils;
import se.snylt.witch.processor.utils.TypeUtils;

import static se.snylt.witch.processor.utils.TypeUtils.ARRAY_LIST;
import static se.snylt.witch.processor.utils.TypeUtils.LIST;
import static se.snylt.witch.processor.utils.TypeUtils.STRING;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER;
import static se.snylt.witch.processor.utils.TypeUtils.TARGET_VIEW_BINDER_FACTORY;
import static se.snylt.witch.processor.utils.TypeUtils.VIEW_BINDER;

public class TargetViewBinder {

    private final Element target;

    private final ClassName targetViewBinderClassName;

    private final ClassName viewHolderClassName;

    private final TypeName targetTypeName;

    private List<ViewBinder.Builder> viewBinders = new ArrayList<>();

    public TargetViewBinder(Element target, ClassName targetViewBinderClassName, TypeName targetTypeName, ClassName viewHolderClassName) {
        this.target = target;
        this.targetViewBinderClassName = targetViewBinderClassName;
        this.targetTypeName = targetTypeName;
        this.viewHolderClassName = viewHolderClassName;
    }

    private TypeSpec createBinderFactory() throws WitchException {
        TypeSpec.Builder targetViewBinderFactory =
                TypeSpec.classBuilder(targetViewBinderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(TARGET_VIEW_BINDER_FACTORY);

        TypeName targetViewBinderType = ParameterizedTypeName.get(TARGET_VIEW_BINDER, targetTypeName, viewHolderClassName);
        MethodSpec.Builder createBinder = MethodSpec.methodBuilder("createBinder")
                .addModifiers(Modifier.PUBLIC)
                .returns(targetViewBinderType);

        TypeName valueTypeName = WildcardTypeName.subtypeOf(Object.class);
        TypeName viewTypeName = WildcardTypeName.subtypeOf(Object.class);
        TypeName viewBinderType = ParameterizedTypeName.get(VIEW_BINDER, targetTypeName, viewTypeName, valueTypeName, viewHolderClassName);
        createBinder.addStatement("$T viewBinders = new $T<>()", ParameterizedTypeName.get(LIST, viewBinderType), ARRAY_LIST);
        createBinder.addCode("\n");

        TypeSpec.Builder targetViewBinder =
                TypeSpec.anonymousClassBuilder("viewBinders")
                        .addSuperinterface(targetViewBinderType);

        // Description
        MethodSpec.Builder describeTarget = MethodSpec.methodBuilder("describeTarget")
                .addParameter(targetTypeName, "target")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T description = \"\"", STRING)
                .returns(STRING);

        // All view binders
        for (ViewBinder.Builder builder: viewBinders) {
            ViewBinder binder = builder.build();
            String value = builder.getPropertyName();
            createBinder.addComment("===========================================");
            createBinder.addComment("Binder - " + value + " BEGIN");
            createBinder.addStatement("viewBinders.add($L)", binder.newInstance());

            String dataName = binder.getDataName();
            String accessData = binder.getAccessData();
            if (dataName != null && accessData != null) {
                describeTarget.addStatement("description += \"$N : \" + \"\" + $N", dataName, accessData);
                describeTarget.addStatement("description += \"\\n\"");
            }

            createBinder.addComment("Binder - " + value + " END");
            createBinder.addComment("===========================================");
            createBinder.addCode("\n");
        }

        describeTarget.addStatement("return description");
        targetViewBinder.addMethod(describeTarget.build());

        // Return
        createBinder.addStatement("return $L", targetViewBinder.build());
        targetViewBinderFactory.addMethod(createBinder.build());

        return targetViewBinderFactory.build();
    }

    public JavaFile createBinderFactoryJavaFile(TypeUtils typeUtils) throws WitchException {
        validateViewBinders(typeUtils);
        return JavaFile.builder(targetViewBinderClassName.packageName(),
                createBinderFactory()).build();
    }

    private void validateViewBinders(TypeUtils typeUtils) throws WitchException {
        for (ViewBinder.Builder viewBinder: viewBinders) {
            viewBinder.validate(typeUtils);
        }
    }

    private TypeSpec createViewHolder() {
        TypeSpec.Builder viewHolder =
                TypeSpec.classBuilder(viewHolderClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (ViewBinder.Builder viewBinder : viewBinders) {
            viewHolder.addField(viewBinder.getGetView().getViewTypeName(), viewBinder.getPropertyName(), Modifier.PUBLIC);
        }

        return viewHolder.build();
    }

    public JavaFile createViewHolderJavaFile() throws WitchException {
        return JavaFile.builder(viewHolderClassName.packageName(),
                createViewHolder()).build();
    }

    public Element getElement() {
        return target;
    }

    public boolean hasViewBinderFor(Element member) {
        try {
            return getViewBinderFor(member) != null;
        } catch (WitchException e) {
            return false;
        }
    }

    public ViewBinder.Builder getViewBinderFor(Element member) throws WitchException {
        if(viewBinders == null) {
            throw new WitchException("Target binder for target " + target.getSimpleName() + " not found");
        }
        String propertyName = ProcessorUtils.getPropertyName(member);
        for (ViewBinder.Builder binder: viewBinders) {
            if (binder.getPropertyName().equals(propertyName)) {
                return binder;
            }
        }
        throw new WitchException("Target binder for target " + target.getSimpleName() + " not found");
    }

    public void addViewBinder(ViewBinder.Builder builder) {
        viewBinders.add(builder);
    }
}
