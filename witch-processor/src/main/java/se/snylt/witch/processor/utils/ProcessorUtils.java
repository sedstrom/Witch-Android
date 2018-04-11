package se.snylt.witch.processor.utils;

import com.squareup.javapoet.ClassName;
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
import se.snylt.witch.processor.dataaccessor.FieldAccessor;
import se.snylt.witch.processor.dataaccessor.MethodAccessor;
import se.snylt.witch.processor.dataaccessor.DataAccessor;

import static se.snylt.witch.processor.utils.TypeUtils.ANDROID_VIEW;

public class ProcessorUtils {

    public static TypeUtils typeUtils;

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

    public static BindMethod getBindMethod(Element bindMethod) throws WitchException {

        if(!isAccessibleMethod(bindMethod)) {
            throw WitchException.bindMethodNotAccessible(bindMethod);
        }

        List<? extends TypeMirror> parameters = ((ExecutableType) bindMethod.asType()).getParameterTypes();
        if (isEmpty(parameters)) {
            return new BindMethod(parameters, BindMethod.Type.EMPTY);
        } else if (isView(parameters)) {
            return new BindMethod(parameters, BindMethod.Type.VIEW);
        } else if (isViewData(parameters)) {
            return new BindMethod(parameters, BindMethod.Type.VIEW_DATA);
        } else if (isViewDataHistory(parameters)) {
            return new BindMethod(parameters, BindMethod.Type.VIEW_DATA_HISTORY);
        } else if (isData(parameters)) {
            return new BindMethod(parameters, BindMethod.Type.DATA);
        } else if (isDataHistory(parameters)) {
            return new BindMethod(parameters, BindMethod.Type.DATA_HISTORY);
        }

        throw WitchException.invalidBindMethod(bindMethod);
    }

    private static boolean  isEmpty(List<? extends TypeMirror> parameters) {
        return parameters.isEmpty();
    }

    private static boolean isView(List<? extends TypeMirror> parameters) {
        return parameters.size() == 1 && isAndroidView(parameters.get(0));
    }

    private static boolean isViewData(List<? extends TypeMirror> parameters) {
        return parameters.size() == 2 && isAndroidView(parameters.get(0));
    }

    private static boolean isViewDataHistory(List<? extends TypeMirror> parameters) {
        return parameters.size() == 3
                && isAndroidView(parameters.get(0))
                && isSameType(parameters.get(1), parameters.get(2));
    }

    private static boolean isData(List<? extends TypeMirror> parameters) {
        return parameters.size() == 1 && !isAndroidView(parameters.get(0));
    }

    private static boolean isDataHistory(List<? extends TypeMirror> parameters) {
        return parameters.size() == 2
                && !isAndroidView(parameters.get(0))
                && isSameType(parameters.get(0), parameters.get(1));
    }

    private static boolean isAndroidView(TypeMirror typeMirror) {
        return typeUtils.isSubtype(typeMirror, typeUtils.typeMirror(ANDROID_VIEW));
    }

    private static boolean isSameType(TypeMirror one, TypeMirror two) {
        return typeUtils.isSameType(one, two);
    }

    public static TypeName getBindDataViewTypeName(Element action) {
        TypeMirror bindClass;
        try {
            action.getAnnotation(BindData.class).view();
            return null;
        } catch (MirroredTypeException mte) {
            bindClass = mte.getTypeMirror();
        }
        return TypeName.get(bindClass);
    }

    public static class BindMethod {

        private final List<? extends TypeMirror> parameters;

        private final Type type;

        public enum Type {
            EMPTY,
            VIEW,
            VIEW_DATA,
            VIEW_DATA_HISTORY,
            DATA,
            DATA_HISTORY;

            public String getSignatureDescription() {
                switch (this) {
                    case EMPTY:
                        return "()";
                    case VIEW:
                        return "(View)";
                    case VIEW_DATA:
                        return "(View, Data)";
                    case VIEW_DATA_HISTORY:
                        return "(View, Data, Data)";
                    case DATA:
                        return "(Data)";
                    case DATA_HISTORY:
                        return "(Data, Data)";
                }
                return "";
            }
        }

        BindMethod(List<? extends TypeMirror> parameters, Type type) {
            this.type = type;
            this.parameters = parameters;
        }

        public Type getType() {
            return type;
        }

        public TypeName getViewTypeName() {
            switch (getType()) {
                case VIEW:
                case VIEW_DATA:
                case VIEW_DATA_HISTORY:
                    return TypeName.get(parameters.get(0));
                case EMPTY:
                case DATA:
                case DATA_HISTORY:
                default:
                    return ANDROID_VIEW;
            }
        }

        public TypeName getDataTypeName() {
            return TypeName.get(getDataTypeMirror());
        }

        public TypeMirror getDataTypeMirror() {
            switch (getType()) {
                case VIEW_DATA:
                case VIEW_DATA_HISTORY:
                    return typeUtils.boxed(parameters.get(1));
                case DATA:
                case DATA_HISTORY:
                    return typeUtils.boxed(parameters.get(0));
                case VIEW:
                case EMPTY:
                default:
                    return typeUtils.typeMirror(ClassName.OBJECT);
            }
        }
    }
}
