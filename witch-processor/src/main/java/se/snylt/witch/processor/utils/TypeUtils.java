package se.snylt.witch.processor.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class TypeUtils {

    private final Types types;

    private final Elements elements;

    public static final TypeName STRING = ClassName.bestGuess("String");

    private final static String LIBRARY_PACKAGE = "se.snylt.witchcore";

    private final static String LIBRARY_VIEW_BINDER_PACKAGE = "se.snylt.witchcore.viewbinder";

    public static final ClassName LIST = ClassName.get(List.class);

    public static final TypeName ARRAY_LIST = TypeName.get(ArrayList.class);

    public final static ClassName VIEW_BINDER = ClassName.get(LIBRARY_VIEW_BINDER_PACKAGE, "ViewBinder");

    public final static TypeName DIFF_VALUE = ClassName.get(LIBRARY_VIEW_BINDER_PACKAGE, "DiffValue");

    public final static TypeName DIFF_VALUE_NO_DATA = ClassName.get(LIBRARY_VIEW_BINDER_PACKAGE, "DiffValue.NO_DATA");

    public static final ClassName TARGET_VIEW_BINDER = ClassName.get(LIBRARY_PACKAGE, "TargetViewBinder");

    public final static TypeName TARGET_VIEW_BINDER_FACTORY = ClassName.get(LIBRARY_PACKAGE, "TargetViewBinderFactory");

    public static final TypeName ANDROID_VIEW = ClassName.get("android.view", "View");

    public TypeUtils(Types types, Elements elements) {
        this.types = types;
        this.elements = elements;
    }

    public Types types() {
        return types;
    }

    public TypeMirror typeMirror(TypeName typeName) {
        return types.getDeclaredType(elements.getTypeElement(typeName.toString()));
    }

    public TypeMirror getBoxedReturnTypeMirror(Element element) {
        if (element.getKind().isField()) {
            return boxed(element.asType());
        } else if (element.getKind() == ElementKind.METHOD) {
            ExecutableType ext = (ExecutableType) element.asType();
            return boxed(ext.getReturnType());
        }
        return null;
    }

    public static String getReturnTypeDescription(Element element) {
        if (element.getKind().isField()) {
            return element.asType().toString();
        } else if (element.getKind() == ElementKind.METHOD) {
            ExecutableType ext = (ExecutableType) element.asType();
            return ext.getReturnType().toString();
        }
        return "";
    }

    public TypeName getReturnTypeName(Element element) {
        return TypeName.get(getBoxedReturnTypeMirror(element));
    }

    TypeMirror boxed(TypeMirror type) {

        // Box if primitive
        if(type.getKind() != null && type.getKind().isPrimitive()) {
            return types.boxedClass((PrimitiveType) type).asType();
        }
        return type;
    }

    public boolean isAssignable(TypeName one, TypeName two) {
        return isAssignable(typeMirror(one), typeMirror(two));
    }

    public boolean isAssignable(TypeMirror one, TypeMirror two) {
        return types.isAssignable(boxed(one), boxed(two));
    }

    public boolean isSubtype(TypeMirror one, TypeMirror two) {
        return types.isSubtype(one, two);
    }

    public boolean isSameType(TypeMirror one, TypeMirror two) {
        return types.isSameType(one, two);
    }
}
