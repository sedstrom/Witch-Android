package se.snylt.witch.processor;

import javax.lang.model.element.Element;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

import se.snylt.witch.annotations.BindWhen;
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

    private static String withReturnType(Element child) {
        String returnType = getReturnTypeDescription(child);
        String childName = child.toString();
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
                    , withReturnType(value))
        );
    }

    public static WitchException bindMethodNotAccessible(Element method) {
        return new WitchException(
                String.format(
                        "%s %s is not accessible. " +
                                "Make sure bind method is not private nor protected. " + readMore
                        , errorForElementParent(method)
                        , withReturnType(method))
        );
    }

    static WitchException noBindForData(Element data) {
        return new WitchException(
                String.format(
                        "%s %s is missing a bind method. " + readMore
                        , errorForElementParent(data)
                        , withReturnType(data))
        );
    }

    public static WitchException incompatibleDataTypes(Element data, Element bindMethod) {
        return new WitchException(
                String.format(
                        "%s%s\nis invalid bind method for:\n%s.\nData types are incompatible. " + readMore
                        , errorForElementParent(bindMethod)
                        , withReturnType(bindMethod)
                        , withReturnType(data))
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
                        , withReturnType(element))
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
                        , withReturnType(bindMethod)
                        , validBindMethodSignatures(bindMethod))
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

}
