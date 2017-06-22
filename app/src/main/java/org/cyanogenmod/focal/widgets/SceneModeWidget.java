package org.cyanogenmod.focal.widgets;

import android.content.Context;

import org.cyanogenmod.focal.CameraManager;
import fr.xplod.focal.R;

public class SceneModeWidget extends SimpleToggleWidget {
    private static final String KEY_SCENEMODE = "scene-mode";

    public SceneModeWidget(CameraManager cam, Context context) {
        super(cam, context, KEY_SCENEMODE, R.drawable.ic_widget_scenemode);
        inflateFromXml(R.array.widget_scenemode_values, R.array.widget_scenemode_icons,
                R.array.widget_scenemode_hints);
        getToggleButton().setHintText(R.string.widget_scenemode);
        restoreValueFromStorage(KEY_SCENEMODE);
    }
}
