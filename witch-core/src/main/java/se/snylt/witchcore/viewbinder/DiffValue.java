package se.snylt.witchcore.viewbinder;


public class DiffValue {

    static final Object NO_HISTORY = new Object();

    public static boolean notEquals(Object newValue, Object historyValue) {
        if(historyValue == NO_HISTORY) {
            return true;
        } else if(newValue == null && historyValue != null) {
            return true;
        } else if(newValue != null && historyValue == null) {
            return true;
        } else if(newValue == null) {
            return false;
        } else {
            return !newValue.equals(historyValue);
        }
    }

    public static boolean notSame(Object newValue, Object historyValue) {
        if(historyValue == NO_HISTORY) {
            return true;
        } else if(newValue == null && historyValue != null) {
            return true;
        } else if(newValue != null && historyValue == null) {
            return true;
        } else if(newValue == null) {
            return false;
        } else {
            return newValue != historyValue;
        }
    }
}
