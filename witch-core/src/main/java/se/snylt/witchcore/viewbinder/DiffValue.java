package se.snylt.witchcore.viewbinder;


public class DiffValue {

    public static final Object NO_HISTORY = new Object();

    public static final Object NO_DATA = new Object();

    public static boolean notEquals(Object newValue, Object historyValue) {
        if (newValue == NO_DATA) {
            return true;
        } else if(historyValue == NO_HISTORY) {
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
        if (newValue == NO_DATA) {
            return true;
        } if(historyValue == NO_HISTORY) {
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

    public static boolean hasHistory(Object newValue, Object historyValue) {
        return historyValue != NO_HISTORY;
    }
}
