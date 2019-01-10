package se.snylt.witch.android;

import se.snylt.witchcore.WitchCore;

public class WitchTestUtils {

    public static void testInit(WitchCore core) {
        Witch.witch(core);
        Witch.looperHelper = new TestLooperHelper();
    }

}
