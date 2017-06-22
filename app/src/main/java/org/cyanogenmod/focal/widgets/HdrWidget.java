package org.cyanogenmod.focal.widgets;

import android.content.Context;
import android.hardware.Camera;

import org.cyanogenmod.focal.CameraManager;

import java.util.List;

import fr.xplod.focal.R;

public class HdrWidget extends SimpleToggleWidget {
    private final static String KEY_PARAMETER = "ae-bracket-hdr";

    public HdrWidget(CameraManager cam, Context context) {
        super(cam, context, "ae-bracket-hdr", R.drawable.ic_widget_hdr);
        getToggleButton().setHintText(R.string.widget_hdr);

        // Reminder: AOSP's HDR mode is scene-mode, so we did put that in scene-mode
        // Here, it's for qualcomm's ae-bracket-hdr param. We filter out scene-mode
        // HDR in priority though, for devices like the Nexus 4 which reports
        // ae-bracket-hdr, but doesn't use it.
        Camera.Parameters params = cam.getParameters();
        if (params == null) {
            return;
        }

        List<String> sceneModes = params.getSupportedSceneModes();

        if (sceneModes != null && !sceneModes.contains("hdr")) {
            addValue("Off", R.drawable.ic_widget_hdr_off, context.getString(R.string.disabled));
            addValue("HDR", R.drawable.ic_widget_hdr_on, context.getString(R.string.enabled));
            addValue("AE-Bracket", R.drawable.ic_widget_hdr_aebracket,
                    context.getString(R.string.widget_hdr_aebracket));

            restoreValueFromStorage(KEY_PARAMETER);
        }
    }
}
