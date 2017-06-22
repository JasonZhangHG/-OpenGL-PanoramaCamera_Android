package org.cyanogenmod.focal.widgets;

import android.content.Context;

import org.cyanogenmod.focal.CameraManager;
import fr.xplod.focal.R;

public class VideoFrWidget extends SimpleToggleWidget {

    public VideoFrWidget(CameraManager cam, Context context) {
        super(cam, context, "video-hfr", R.drawable.ic_widget_videofr);

        // For video only
        setVideoOnly(true);

        // TODO: Needs filtering depending on resolution (see video-hfr-size)
        addValue("off", R.drawable.ic_widget_videofr_30, "30 FPS");
        addValue("60", R.drawable.ic_widget_videofr_60, "60 FPS");
        addValue("90", R.drawable.ic_widget_videofr_90, "90 FPS");
        addValue("120", R.drawable.ic_widget_videofr_120, "120 FPS");

        getToggleButton().setHintText(R.string.widget_videofr);
    }
}
