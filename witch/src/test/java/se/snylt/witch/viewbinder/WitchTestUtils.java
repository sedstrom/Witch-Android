package se.snylt.witch.viewbinder;

public class WitchTestUtils {

    public static void testInit(WitchCore core) {
        Witch.witch(core);
        Witch.looperHelper = new TestLooperHelper();
    }

}
