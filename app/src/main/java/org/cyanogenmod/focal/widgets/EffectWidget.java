package org.cyanogenmod.focal.widgets;

import android.content.Context;
import android.os.Build;

import org.cyanogenmod.focal.CameraManager;

import fr.xplod.focal.R;

public class EffectWidget extends SimpleToggleWidget {
    private static final String KEY_EFFECT = "effect";

    public EffectWidget(CameraManager cam, Context context) {
        super(cam, context, KEY_EFFECT, R.drawable.ic_widget_effect);
        inflateFromXml(R.array.widget_effects_values, R.array.widget_effects_icons,
                R.array.widget_effects_hints);
        getToggleButton().setHintText(R.string.widget_effect);
        restoreValueFromStorage(KEY_EFFECT);
    }

    @Override
    public boolean filterDeviceSpecific(String value) {
        if (Build.DEVICE.equals("mako")) {
            if (value.equals("whiteboard") || value.equals("blackboard")) {
                // Hello Google, why do you report whiteboard/blackboard supported (in -values),
                // when they are not? :(
                return false;
            }
        }
        return true;
    }
}
