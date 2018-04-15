package se.snylt.witch.processor;

import javax.lang.model.element.Element;

import se.snylt.witch.processor.utils.ProcessorUtils;

import static se.snylt.witch.processor.utils.TypeUtils.getReturnTypeDescription;

public class WitchException extends Exception {

    private final static String readMore = "Read more at: https://sedstrom.github.io/Witch-Android/";

    public WitchException(String message) {
        super(message);
    }

    private static String errorForElementParent(Element child) {
        return String.format("Witch error in %s:\n", child.getEnclosingElement());
    }

    private static String methodWithReturnType(Element method) {
        String returnType = getReturnTypeDescription(method);
        String childName = method.toString();
        return String.format("%s %s", returnType, childName);
    }

    public static WitchException invalidDataAccessor(Element value) {
        return new WitchException(
            String.format(
                "%s\n"
                    + "%s is not a valid data accessor.\n"
                    + "Make sure accessor conforms to:\n"
                    + "- Is field or method\n"
                    + "- Is not private nor protected\n"
                    + "- Has no parameters\n"
                    + "- Has a non-void return type\n"
                    + readMore
                    , errorForElementParent(value)
                    , methodWithReturnType(value))
        );
    }

    public static WitchException bindMethodNotAccessible(Element method) {
        return new WitchException(
                String.format(
                        "%s %s is not accessible. " +
                                "Make sure bind method is not private nor protected. " + readMore
                        , errorForElementParent(method)
                        , methodWithReturnType(method))
        );
    }

    static WitchException noBindForData(Element data) {
        return new WitchException(
                String.format(
                        "%s %s is missing a bind method. " + readMore
                        , errorForElementParent(data)
                        , methodWithReturnType(data))
        );
    }

    public static WitchException incompatibleDataTypes(Element data, Element bindMethod) {
        return new WitchException(
                String.format(
                        "%s%s\nis invalid bind method for:\n%s.\nData types are incompatible. " + readMore
                        , errorForElementParent(bindMethod)
                        , methodWithReturnType(bindMethod)
                        , methodWithReturnType(data))
        );
    }

    static WitchException invalidBindWhenValue(Element bindWhen, String bindWhenValue) {
        return new WitchException(
                String.format(
                        "%s %s has invalid value \"%s\" for @BindWhen.\nValid values are:\n%s\n%s\n%s\n%s\n" + readMore
                        , errorForElementParent(bindWhen)
                        , bindWhen
                        , bindWhenValue
                        , "BindWhen.NOT_SAME"
                        , "BindWhen.NOT_EQUALS"
                        , "BindWhen.ALWAYS"
                        , "BindWhen.ONCE")
        );
    }

    public static WitchException conflictingBindWhen(Element element) {
        return new WitchException(
                String.format(
                        "%s @BindWhen is defined multiple times for %s. " + readMore
                        , errorForElementParent(element)
                        , methodWithReturnType(element))
        );
    }

    WitchRuntimeException toRuntimeException() {
        return new WitchRuntimeException(getMessage());
    }

    public static WitchException invalidBindMethod(Element bindMethod) {
        return new WitchException(
                String.format(
                        "%s%s is not a valid bind method.\n%s" + readMore
                        , errorForElementParent(bindMethod)
                        , methodWithReturnType(bindMethod)
                        , validBindMethodSignatures(bindMethod))
        );
    }

    public static WitchException bindMethodMissingData(Element bindMethod, String missingDataName) {
        return new WitchException(
                String.format(
                        "%sMissing @Data-annotated field named \"%s\" for bind method %s\n" + readMore
                        , errorForElementParent(bindMethod)
                        , missingDataName
                        , methodWithReturnType(bindMethod))
        );
    }

    private static String validBindMethodSignatures(Element bindMethod) {
        return String.format("Valid bind method signatures are:\n%s"
                        , describeBindMethodSignatures(bindMethod));
    }

    private static String describeBindMethodSignatures(Element bindMethod) {
        String simpleName = bindMethod.getSimpleName().toString();
        StringBuilder builder = new StringBuilder();
        for (ProcessorUtils.BindMethod.Type type: ProcessorUtils.BindMethod.Type.values()) {
            builder.append(simpleName);
            builder.append(type.getSignatureDescription());
            builder.append("\n");
        }
        return builder.toString();
    }

    public static WitchException bindNullNotCombinedWithBind(Element bindNull) {
        return new WitchException(
                String.format(
                        "%sInvalid use of @BindNull at %s.\n@BindNull must be combined with a @Bind or @BindData annotation.\n" + readMore
                        , errorForElementParent(bindNull)
                        , methodWithReturnType(bindNull))
        );
    }

    public static WitchException bindWhenNotCombinedWithBind(Element bindWhen) {
        return new WitchException(
                String.format(
                        "%sInvalid use of @BindWhen at %s.\n@BindWhen must be combined with a @Bind or @BindData annotation.\n" + readMore
                        , errorForElementParent(bindWhen)
                        , methodWithReturnType(bindWhen))
        );
    }
}
