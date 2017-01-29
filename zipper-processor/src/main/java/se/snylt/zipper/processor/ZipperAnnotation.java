package se.snylt.zipper.processor;

public class ZipperAnnotation {

    public final String asString;

    public ZipperAnnotation(String asString) {
        this.asString = asString;
    }

    public static ZipperAnnotation fromName(String asString) {
        return new ZipperAnnotation(asString);
    }
}
