package se.snylt.witchcore.viewbinder;


public class DiffUtils {

    static final Object NO_HISTORY = new Object();

    public static final Object NO_DATA = new Object();

    public static boolean notEquals(Object newData, Object historyData) {
        if (newData == NO_DATA) {
            return true;
        } else if(historyData == NO_HISTORY) {
            return true;
        } else if(newData == null && historyData != null) {
            return true;
        } else if(newData != null && historyData == null) {
            return true;
        } else if(newData == null) {
            return false;
        } else {
            return !newData.equals(historyData);
        }
    }

    public static boolean notSame(Object newData, Object historyData) {
        if (newData == NO_DATA) {
            return true;
        } if(historyData == NO_HISTORY) {
            return true;
        } else if(newData == null && historyData != null) {
            return true;
        } else if(newData != null && historyData == null) {
            return true;
        } else if(newData == null) {
            return false;
        } else {
            return newData != historyData;
        }
    }

    public static boolean hasHistory(Object newData, Object historyData) {
        return historyData != NO_HISTORY;
    }
}
