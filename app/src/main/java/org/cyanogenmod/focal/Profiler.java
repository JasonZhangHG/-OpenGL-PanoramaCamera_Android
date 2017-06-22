package org.cyanogenmod.focal;

import android.os.SystemClock;
import android.util.Log;

import java.util.HashMap;

/**
 * Simple profiler to help debug app speed
 */
public class Profiler {
    private static final String TAG = "Profiler";

    private static Profiler sDefault = null;
    private HashMap<String, Long> mTimestamps;

    public static Profiler getDefault() {
        if (sDefault == null) sDefault = new Profiler();
        return sDefault;
    }

    private Profiler() {
        mTimestamps = new HashMap<String, Long>();
    }

    public void start(String name) {
        mTimestamps.put(name, SystemClock.uptimeMillis());
    }

    public void logProfile(String name) {
        long time = mTimestamps.get(name);
        long delta = SystemClock.uptimeMillis() - time;

        Log.d(TAG, "Time for '" + name + "': " + delta + "ms");
    }
}
