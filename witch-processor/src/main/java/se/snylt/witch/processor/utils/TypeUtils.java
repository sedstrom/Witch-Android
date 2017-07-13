package se.snylt.witch.processor.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import se.snylt.witch.processor.WitchException;

public class TypeUtils {

    private final Types types;

    private final Elements elements;

    private final static String LIBRARY_PACKAGE = "se.snylt.witch.viewbinder";

    private final static String LIBRARY_VIEW_BINDER_PACKAGE = "se.snylt.witch.viewbinder.viewbinder";

    private final static String LIBRARY_BIND_ACTIONS_PACKAGE = "se.snylt.witch.viewbinder.bindaction";

    public static final ClassName LIST = ClassName.get(List.class);

    public static final TypeName ARRAY_LIST = TypeName.get(ArrayList.class);

    public final static ClassName VIEW_BINDER = ClassName.get(LIBRARY_VIEW_BINDER_PACKAGE, "ViewBinder");

    public final static TypeName VALUE = ClassName.get(LIBRARY_PACKAGE, "Value");

    public final static TypeName DIFF_VALUE = ClassName.get(LIBRARY_VIEW_BINDER_PACKAGE, "DiffValue");

    public static final TypeName TARGET_VIEW_BINDER = ClassName.get(LIBRARY_PACKAGE, "TargetViewBinder");

    public final static TypeName TARGET_VIEW_BINDER_FACTORY = ClassName.get(LIBRARY_PACKAGE, "TargetViewBinderFactory");

    public final static TypeName BINDER = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "Binder");

    public static final TypeName ANDROID_VIEW = ClassName.get("android.view", "View");

    public static final ClassName ON_BIND = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "OnBind");

    public static final ClassName SYNC_ON_BIND = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "SyncOnBind");

    public TypeUtils(Types types, Elements elements) {
        this.types = types;
        this.elements = elements;
    }

    public Types types() {
        return types;
    }

    private String asString(ClassName className) {
        return className.packageName() + "." + className.simpleName();
    }

    public TypeMirror onBindDeclaredType() {
        return declared(ON_BIND);
    }

    private TypeMirror declared(ClassName name) {
        return types.getDeclaredType(elements.getTypeElement(asString(name)),
                types.getWildcardType(null, null),
                types.getWildcardType(null, null));
    }

    TypeMirror typeMirror(TypeName typeName) {
        return types.getDeclaredType(elements.getTypeElement(typeName.toString()));
    }

    boolean isValueContainer(TypeMirror type) {
        TypeMirror valueType = typeMirror(VALUE);
        return types.isAssignable(type, valueType);
    }

    public boolean isValueContainer(Element element) {
        return isValueContainer(getType(element));
    }

    public TypeMirror getType(Element element) throws WitchException {
        if (element.getKind().isField()) {
            return element.asType();
        } else if (element.getKind() == ElementKind.METHOD) {
            ExecutableType ext = (ExecutableType) element.asType();
            return ext.getReturnType();
        }
        return null;
    }

    public TypeName getValueType(Element value) {

        TypeMirror type = getType(value);

        // Box if primitive
        if(type.getKind() != null && type.getKind().isPrimitive()) {
            type = types.boxedClass((PrimitiveType) type).asType();
        }

        // Get generic type if value container
        if(isValueContainer(type)) {
            DeclaredType declaredType = (DeclaredType) type;
            TypeMirror genericParameter = declaredType.getTypeArguments().get(0);
            return TypeName.get(genericParameter);
        }

        return TypeName.get(type);
    }

}
