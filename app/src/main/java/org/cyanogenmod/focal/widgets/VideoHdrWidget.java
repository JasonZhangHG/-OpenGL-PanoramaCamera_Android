package org.cyanogenmod.focal.widgets;

import android.content.Context;
import android.hardware.Camera;

import org.cyanogenmod.focal.CameraManager;
import fr.xplod.focal.R;

/**
 * Video HDR widget
 * - On non-sony QCOM devices, video hdr is toggled by "video-hdr"
 * - On Sony devices, video hdr is toggled by "sony-video-hdr"
 */
public class VideoHdrWidget extends SimpleToggleWidget {
    private static final String KEY_QCOM_VIDEO_HDR = "video-hdr";
    private static final String KEY_SONY_VIDEO_HDR = "sony-video-hdr";

    public VideoHdrWidget(CameraManager cam, Context context) {
        super(cam, context, R.drawable.ic_widget_placeholder); // TODO: Icon, video hdr

        setVideoOnly(true);

        // We cannot inflate from XML here, because there are device-specific keys and values
        Camera.Parameters params = mCamManager.getParameters();
        if (params == null) {
            return;
        }

        if (params.get(KEY_SONY_VIDEO_HDR) != null) {
            // Use Sony values
            setKey(KEY_SONY_VIDEO_HDR);
            addValue("off", R.drawable.ic_widget_hdr_off, context.getString(R.string.disabled));
            addValue("on", R.drawable.ic_widget_hdr_on, context.getString(R.string.enabled));
        } else if (params.get(KEY_QCOM_VIDEO_HDR) != null) {
            // Use Qcom values
            setKey(KEY_QCOM_VIDEO_HDR);
            addValue("0", R.drawable.ic_widget_hdr_off, context.getString(R.string.disabled));
            addValue("1", R.drawable.ic_widget_hdr_on, context.getString(R.string.enabled));
        }

        getToggleButton().setHintText(R.string.widget_videohdr);
    }
}
