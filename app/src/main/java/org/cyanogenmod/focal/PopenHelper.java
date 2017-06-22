package org.cyanogenmod.focal;

/**
 * Helper class calling a JNI native popen method to run system
 * commands
 */
public class PopenHelper {
    static {
        System.loadLibrary("xmptoolkit");
        System.loadLibrary("popen_helper_jni");
    }

    /**
     * Runs the specified command-line program in the command parameter.
     * Note that this command is ran with the shell interpreter (/system/bin/sh), thus
     * a shell script or a bunch of commands can be called in one method call.
     * The output of the commands (stdout/stderr) is sent out to the logcat with the
     * INFORMATION level.
     *
     * @params command The command to run
     * @return The call return code
     */
    public static native int run(String command);
}
