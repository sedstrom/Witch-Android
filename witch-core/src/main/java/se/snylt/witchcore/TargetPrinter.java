package se.snylt.witchcore;

import java.util.HashMap;

public abstract class TargetPrinter<T> {

    private final HashMap<String, String> out = new HashMap<>();

    public abstract void describeTarget(T target, HashMap<String, String> out);

}
