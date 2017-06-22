package org.cyanogenmod.focal.widgets;

import android.content.res.Resources;
import android.hardware.Camera;
import android.view.View;

import org.cyanogenmod.focal.CameraActivity;
import org.cyanogenmod.focal.feats.BurstCapture;

import fr.xplod.focal.R;

/**
 * Burst-shooting mode widget
 */
public class BurstModeWidget extends WidgetBase implements View.OnClickListener {
    private WidgetOptionButton mBtnOff;
    private WidgetOptionButton mBtn5;
    private WidgetOptionButton mBtn10;
    private WidgetOptionButton mBtn15;
    private WidgetOptionButton mBtnInf;
    private WidgetOptionButton mPreviousMode;
    private CameraActivity mCameraActivity;
    private BurstCapture mTransformer;
    private final static String DRAWABLE_TAG = "nemesis-burst-mode";

    public BurstModeWidget(CameraActivity activity) {
        super(activity.getCamManager(), activity, R.drawable.ic_widget_burst);

        mCameraActivity = activity;

        // Create options
        // XXX: Move that into an XML
        mBtnOff = new WidgetOptionButton(R.drawable.ic_widget_burst_off, activity);
        mBtn5 = new WidgetOptionButton(R.drawable.ic_widget_burst_5, activity);
        mBtn10 = new WidgetOptionButton(R.drawable.ic_widget_burst_10, activity);
        mBtn15 = new WidgetOptionButton(R.drawable.ic_widget_burst_15, activity);
        mBtnInf = new WidgetOptionButton(R.drawable.ic_widget_burst_inf, activity);

        getToggleButton().setHintText(R.string.widget_burstmode);
        final Resources res = getWidget().getResources();
        mBtn5.setHintText(String.format(res.getString(R.string.widget_burstmode_count_shots), 5));
        mBtn10.setHintText(String.format(res.getString(R.string.widget_burstmode_count_shots), 10));
        mBtn15.setHintText(String.format(res.getString(R.string.widget_burstmode_count_shots), 15));
        mBtnOff.setHintText(R.string.widget_burstmode_off);
        mBtnInf.setHintText(R.string.widget_burstmode_infinite);

        addViewToContainer(mBtnOff);
        addViewToContainer(mBtn5);
        addViewToContainer(mBtn10);
        addViewToContainer(mBtn15);
        addViewToContainer(mBtnInf);

        mBtnOff.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn10.setOnClickListener(this);
        mBtn15.setOnClickListener(this);
        mBtnInf.setOnClickListener(this);

        mPreviousMode = mBtnOff;
        mPreviousMode.activeImage(DRAWABLE_TAG + "=off");

        mTransformer = new BurstCapture(activity);
    }

    @Override
    public boolean isSupported(Camera.Parameters params) {
        // Burst mode is supported by everything. If we are in photo mode that is.
        if (CameraActivity.getCameraMode() == CameraActivity.CAMERA_MODE_PHOTO) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        mPreviousMode.resetImage();

        if (view == mBtnOff) {
            // Disable the transformer
            mCameraActivity.setCaptureTransformer(null);
            mBtnOff.activeImage(DRAWABLE_TAG + "=off");
            mPreviousMode = mBtnOff;
        } else if (view == mBtn5) {
            mTransformer.setBurstCount(5);
            mCameraActivity.setCaptureTransformer(mTransformer);
            mBtn5.activeImage(DRAWABLE_TAG + "=5");
            mPreviousMode = mBtn5;
        } else if (view == mBtn10) {
            mTransformer.setBurstCount(10);
            mCameraActivity.setCaptureTransformer(mTransformer);
            mBtn10.activeImage(DRAWABLE_TAG + "=10");
            mPreviousMode = mBtn10;
        } else if (view == mBtn15) {
            mTransformer.setBurstCount(15);
            mCameraActivity.setCaptureTransformer(mTransformer);
            mBtn15.activeImage(DRAWABLE_TAG + "=15");
            mPreviousMode = mBtn15;
        } else if (view == mBtnInf) {
            // Infinite burst count
            mTransformer.setBurstCount(0);
            mCameraActivity.setCaptureTransformer(mTransformer);
            mBtnInf.activeImage(DRAWABLE_TAG + "=inf");
            mPreviousMode = mBtnInf;
        }
    }
}
