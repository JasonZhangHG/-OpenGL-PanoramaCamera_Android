package org.cyanogenmod.focal.widgets;

import android.content.Context;

import org.cyanogenmod.focal.CameraManager;
import fr.xplod.focal.R;

/**
 * Flash Widget, manages the flash settings
 */
public class FlashWidget extends SimpleToggleWidget {
    private static final String KEY_REDEYE_REDUCTION = "redeye-reduction";
    private static final String KEY_FLASH_MODE = "flash-mode";

    public FlashWidget(CameraManager cam, Context context) {
        super(cam, context, KEY_FLASH_MODE, R.drawable.ic_widget_flash_on);
        inflateFromXml(R.array.widget_flash_values, R.array.widget_flash_icons,
                R.array.widget_flash_hints);
        getToggleButton().setHintText(R.string.widget_flash);
        restoreValueFromStorage(KEY_FLASH_MODE);
    }

    /**
     * When the flash is enabled, try to enable red-eye reduction (qualcomm)
     *
     * @param value The value set to the key
     */
    @Override
    public void onValueSet(String value) {
        if (value.equals("on") || value.equals("auto")) {
            if (mCamManager.getParameters().get(KEY_REDEYE_REDUCTION) != null) {
                mCamManager.setParameterAsync(KEY_REDEYE_REDUCTION, "enable");
            }
        } else {
            if (mCamManager.getParameters().get(KEY_REDEYE_REDUCTION) != null) {
                mCamManager.setParameterAsync(KEY_REDEYE_REDUCTION, "disable");
            }
        }
    }
}
