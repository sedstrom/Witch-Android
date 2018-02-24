package se.snylt.witchcore;

import java.util.HashMap;

public abstract class TargetPrinter<T> {

    private final HashMap<String, String> out = new HashMap<>();

    String describeTarget(T target) {
        out.clear();
        describeTarget(target, out);
        return target.getClass().getSimpleName() + " >\n" + out.toString();

        /*
        try {
            return target.getClass().getSimpleName() + " >\n" + out.toString(); //new JSONObject(out).toString(2);
        } catch (JSONException e) {
            return target.getClass().getSimpleName() + " >\n" + new JSONObject(out).toString();
        }*/
    }

    public abstract void describeTarget(T target, HashMap<String, String> out);

}
