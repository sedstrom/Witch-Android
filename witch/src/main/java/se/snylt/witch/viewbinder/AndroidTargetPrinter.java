package se.snylt.witch.viewbinder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.HashMap;

public abstract class AndroidTargetPrinter<T> implements TargetPrinter<T> {

    private final HashMap<String, String> out = new HashMap<>();

    private final String LOG_TAG = "Witch";

    @Override
    public void printTarget(T target) {
        out.clear();
        describeTarget(target, out);
        try {
            Log.v(LOG_TAG, target.getClass().getSimpleName() + " >\n" + new JSONObject(out).toString(2));
        } catch (JSONException e) {
            Log.v(LOG_TAG, target.getClass().getSimpleName() + " >\n" + new JSONObject(out).toString());
        }
    }

    public abstract void describeTarget(T target, HashMap<String, String> out);
}
