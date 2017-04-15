package se.snylt.witch.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class TypeUtils {

    private final static String LIBRARY_PACKAGE = "se.snylt.witch.viewbinder";

    private final static String LIBRARY_BIND_ACTIONS_PACKAGE = "se.snylt.witch.viewbinder.bindaction";

    public static final ClassName LIST = ClassName.get(List.class);

    public static final TypeName ARRAY_LIST = TypeName.get(ArrayList.class);

    public final static TypeName VIEW_BINDER = ClassName.get(LIBRARY_PACKAGE, "ViewBinder");

    public static final TypeName TARGET_VIEW_BINDER = ClassName.get(LIBRARY_PACKAGE, "TargetViewBinder");

    public final static TypeName TARGET_VIEW_BINDER_FACTORY = ClassName.get(LIBRARY_PACKAGE, "TargetViewBinderFactory");

    public final static TypeName VALUE_BINDER = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "ValueBinder");

    public final static TypeName BINDER = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "Binder");

    public static final TypeName ANDROID_VIEW = ClassName.get("android.view", "View");

    static final ClassName ON_BIND = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "OnBind");

    public static final ClassName SYNC_ON_BIND = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "SyncOnBind");

    private static String asString(ClassName className) {
        return className.packageName() + "." + className.simpleName();
    }

    public static TypeMirror onBindDeclaredType(Types types, Elements elements) {
        return declared(types, elements, ON_BIND);
    }

    private static TypeMirror declared(Types types, Elements elements, ClassName name) {
        return types.getDeclaredType(elements.getTypeElement(asString(name)),
                types.getWildcardType(null, null),
                types.getWildcardType(null, null));
    }

    public static TypeMirror typeMirror(Types types, Elements elements, TypeName typeName) {
        return types.getDeclaredType(elements.getTypeElement(typeName.toString()));
    }
}
