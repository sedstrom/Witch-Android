package se.snylt.witch.processor.utils;

import com.squareup.javapoet.TypeName;

import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.annotations.BindData;
import se.snylt.witch.processor.WitchException;
import se.snylt.witch.processor.valueaccessor.FieldAccessor;
import se.snylt.witch.processor.valueaccessor.MethodAccessor;
import se.snylt.witch.processor.valueaccessor.DataAccessor;

import static se.snylt.witch.processor.utils.TypeUtils.ANDROID_VIEW;

public class ProcessorUtils {

    private final TypeUtils typeUtils;

    public ProcessorUtils(TypeUtils typeUtils) {
        this.typeUtils = typeUtils;
    }

    public static String getPropertySetter(String property) {
        return "set" + capitalize(property);
    }

    private static String capitalize(String s) {
        return s.toUpperCase().charAt(0) + ((s.length() > 0) ? s.substring(1) : "");
    }

    private static boolean notPrivateOrProtected(Element e) {
        Set<Modifier> modifiers = e.getModifiers();
        return !modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.PROTECTED);
    }

    static boolean isAccessibleMethod(Element e) {
        return e.getKind() == ElementKind.METHOD && notPrivateOrProtected(e);
    }

    static boolean isAccessibleMethodWithZeroParameters(Element e) {
        return isAccessibleMethod(e) && ((ExecutableType) e.asType()).getParameterTypes().isEmpty();
    }

    private static boolean isAccessibleField(Element e) {
        return e.getKind().isField() && notPrivateOrProtected(e);
    }

    public static String getPropertyName(Element element) {
        return element.getSimpleName().toString();
    }

    public static DataAccessor getDataAccessor(Element element) throws WitchException {
        if (isAccessibleMethodWithZeroParameters(element)) {
            return new MethodAccessor(element.getSimpleName().toString());
        }

        if (isAccessibleField(element)) {
            return new FieldAccessor(element.getSimpleName().toString());
        }

        throw WitchException.invalidDataAccessor(element);
    }

    public TypeName[] getBindMethodTypeNames(Element bindMethod) throws WitchException {
        TypeMirror[] typeMirrors = getBindMethodTypeMirrors(bindMethod);
        return new TypeName[]{TypeName.get(typeMirrors[0]), TypeName.get(typeMirrors[1])};
    }

    public TypeMirror[] getBindMethodTypeMirrors(Element bindMethod) throws WitchException {

        if(!isAccessibleMethod(bindMethod)) {
            throw WitchException.bindMethodNotAccessible(bindMethod);
        }

        ExecutableType type = (ExecutableType) bindMethod.asType();
        List<? extends TypeMirror> parameters = type.getParameterTypes();
        if(parameters.size() != 2) {
            throw WitchException.bindMethodWrongArgumentCount(bindMethod);
        }

        // View
        TypeMirror view = parameters.get(0);
        if(!typeUtils.isSubtype(view, typeUtils.typeMirror(ANDROID_VIEW))) {
            throw WitchException.bindMethodWrongViewType(bindMethod);
        }

        // Data
        TypeMirror data = typeUtils.boxed(parameters.get(1));

        return new TypeMirror[]{view, data};

    }

    public TypeName getBindDataViewTypeName(Element action) {
        TypeMirror bindClass;
        try {
            action.getAnnotation(BindData.class).view();
            return null;
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }
}
