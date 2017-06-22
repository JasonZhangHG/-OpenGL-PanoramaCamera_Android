package org.cyanogenmod.focal.widgets;

import android.content.Context;

import org.cyanogenmod.focal.CameraManager;
import fr.xplod.focal.R;

/**
 * White Balance Widget, manages the wb settings
 */
public class WhiteBalanceWidget extends SimpleToggleWidget {
    private static final String KEY_WHITEBALANCE = "whitebalance";

    public WhiteBalanceWidget(CameraManager cam, Context context) {
        super(cam, context, KEY_WHITEBALANCE, R.drawable.ic_widget_wb_auto);
        inflateFromXml(R.array.widget_whitebalance_values, R.array.widget_whitebalance_icons,
                R.array.widget_whitebalance_hints);
        getToggleButton().setHintText(R.string.widget_whitebalance);
        restoreValueFromStorage(KEY_WHITEBALANCE);
    }
}
