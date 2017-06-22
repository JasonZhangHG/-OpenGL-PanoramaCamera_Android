package org.cyanogenmod.focal.widgets;

import android.content.Context;

import org.cyanogenmod.focal.CameraManager;
import fr.xplod.focal.R;

/**
 * ISO widget, sets ISO sensitivity value
 */
public class IsoWidget extends SimpleToggleWidget {
    private static final String KEY_ISO = "iso";

    public IsoWidget(CameraManager cam, Context context) {
        super(cam, context, KEY_ISO, R.drawable.ic_widget_iso);
        inflateFromXml(R.array.widget_iso_values, R.array.widget_iso_icons,
                R.array.widget_iso_hints);
        getToggleButton().setHintText(R.string.widget_iso);
        restoreValueFromStorage(KEY_ISO);
    }
}
