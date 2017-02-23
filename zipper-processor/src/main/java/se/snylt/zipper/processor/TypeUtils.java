package se.snylt.zipper.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class TypeUtils {

    private final static String LIBRARY_PACKAGE = "se.snylt.zipper.viewbinder";

    private final static String LIBRARY_BIND_ACTIONS_PACKAGE = "se.snylt.zipper.viewbinder.bindaction";

    public static final ClassName LIST = ClassName.get(List.class);

    public static final TypeName ARRAY_LIST = TypeName.get(ArrayList.class);

    public static final TypeName VIEW_BINDER = ClassName.get(LIBRARY_PACKAGE, "ViewBinder");

    public final static TypeName BINDING_CREATOR = ClassName.get(LIBRARY_PACKAGE, "BindingCreator");

    public final static TypeName BINDER = ClassName.get(LIBRARY_PACKAGE, "Binder");

    public static final TypeName ANDROID_VIEW = ClassName.get("android.view", "View");

    public static final ClassName ON_PRE_BIND_ACTION = ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "OnPreBindAction");

    public static final ClassName ON_BIND_ACTION =  ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "OnBindAction");

    public static final ClassName ON_POST_BIND_ACTION =  ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "OnPostBindAction");

    public static final ClassName BIND_ACTION =  ClassName.get(LIBRARY_BIND_ACTIONS_PACKAGE, "BindAction");

    public static String asString(ClassName className) {
        return className.packageName() + "." + className.simpleName();
    }

    public static TypeMirror onPostBindActionDeclaredType(Types types, Elements elements) {
        return declared(types, elements, ON_POST_BIND_ACTION);
    }

    public static TypeMirror onPreBindActionDeclaredType(Types types, Elements elements) {
        return declared(types, elements, ON_PRE_BIND_ACTION);
    }

    public static TypeMirror onBindActionDeclaredType(Types types, Elements elements) {
        return declared(types, elements, ON_PRE_BIND_ACTION);
    }


    private static TypeMirror declared(Types types, Elements elements, ClassName name) {
        return types.getDeclaredType(elements.getTypeElement(asString(name)),
                types.getWildcardType(null, null),
                types.getWildcardType(null, null));
    }
}
