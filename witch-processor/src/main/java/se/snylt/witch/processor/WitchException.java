package se.snylt.witch.processor;

import javax.lang.model.element.Element;

public class WitchException extends Exception {

    private final static String readMore = "Read more at: https://sedstrom.github.io/Witch-Android/";

    public WitchException(String message) {
        super(message);
    }

    private static String errorForElementParent(Element child) {
        return String.format("Error in %s:", child.getEnclosingElement());
    }

    public static WitchException invalidValueAccessor(Element value) {
        return new WitchException(
                String.format(
                        "%s. %s cannot be annotated with @Data. " +
                            "Make sure value is a public or protected field or method. " + readMore
                            , errorForElementParent(value)
                            , value)
        );
    }

    public static WitchException bindMethodNotAccessible(Element method) {
        return new WitchException(
                String.format(
                        "%s. %s is not accessible. " +
                                "Make sure method is not private. " + readMore
                        , errorForElementParent(method)
                        , method)
        );
    }

    public static WitchException noBindForData(Element data) {
        return new WitchException(
                String.format(
                        "%s. Missing @Bind for @Data %s. " + readMore
                        , errorForElementParent(data)
                        , data)
        );
    }

    public static WitchException bindMethodWrongArgumentCount(Element bindMethod) {
        return new WitchException(
                String.format(
                        "%s. %s has wrong number of parameters. " + readMore
                        , errorForElementParent(bindMethod)
                        , bindMethod)
        );
    }

    public static WitchException bindMethodWrongViewType(Element bindMethod) {
        return new WitchException(
                String.format(
                        "%s. %s has invalid view type. " + readMore
                        , errorForElementParent(bindMethod)
                        , bindMethod)
        );
    }
}
