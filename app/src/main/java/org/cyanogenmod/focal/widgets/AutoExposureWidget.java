package org.cyanogenmod.focal.widgets;

import android.content.Context;
import android.hardware.Camera;

import org.cyanogenmod.focal.CameraManager;
import fr.xplod.focal.R;

/**
 * Auto Exposure Widget, manages the auto-exposure measurement method
 */
public class AutoExposureWidget extends SimpleToggleWidget {
    private static final String KEY_AUTOEXPOSURE = "auto-exposure";

    public AutoExposureWidget(CameraManager cam, Context context) {
        super(cam, context, KEY_AUTOEXPOSURE, R.drawable.ic_widget_autoexposure);
        inflateFromXml(R.array.widget_autoexposure_values, R.array.widget_autoexposure_icons,
                R.array.widget_autoexposure_hints);
        getToggleButton().setHintText(R.string.widget_autoexposure);
        restoreValueFromStorage(KEY_AUTOEXPOSURE);
    }

    @Override
    public boolean isSupported(Camera.Parameters params) {
        return super.isSupported(params) && mCamManager.isExposureAreaSupported();
    }
}
