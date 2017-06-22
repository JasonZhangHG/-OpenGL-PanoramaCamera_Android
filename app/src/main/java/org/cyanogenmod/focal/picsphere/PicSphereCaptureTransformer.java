package org.cyanogenmod.focal.picsphere;

import android.hardware.Camera;
import android.util.Log;

import org.cyanogenmod.focal.CameraActivity;
import fr.xplod.focal.R;
import org.cyanogenmod.focal.SnapshotManager;
import org.cyanogenmod.focal.feats.CaptureTransformer;
import org.cyanogenmod.focal.ui.ShutterButton;

/**
 * Capture Transformer for PicSphere that will store all shots to feed them to a new PicSphere
 * created by PicSphereManager
 */
public class PicSphereCaptureTransformer extends CaptureTransformer {
    public final static String TAG = "PicSphereCaptureTransformer";
    private PicSphereManager mPicSphereManager;
    private PicSphere mPicSphere;
    private CameraActivity mContext;
    private Vector3 mLastShotAngle;

    public PicSphereCaptureTransformer(CameraActivity context) {
        super(context.getCamManager(), context.getSnapManager());
        mContext = context;
        mPicSphereManager = context.getPicSphereManager();
    }

    public void removeLastPicture() {
        if (mPicSphere == null) {
            return;
        }

        mPicSphere.removeLastPicture();
        mPicSphereManager.getRenderer().removeLastPicture();

        if (mPicSphere.getPicturesCount() == 0) {
            mContext.setPicSphereUndoVisible(false);
            mPicSphereManager.getRenderer().setCamPreviewVisible(true);
        }
    }

    @Override
    public void onShutterButtonClicked(ShutterButton button) {
        if (mPicSphere == null) {
            // Initialize a new sphere
            mPicSphere = mPicSphereManager.createPicSphere();
            Camera.Parameters params = mCamManager.getParameters();
            float horizontalAngle = 0;

            if (params != null) {
                horizontalAngle = mCamManager.getParameters().getHorizontalViewAngle();
            }

            // In theory, drivers should return a proper value for horizontal angle. However,
            // some careless OEMs put "0" or "360" to pass CTS, so we just check if the value
            // seems legit, otherwise we put 45° as it's the angle of most phone lenses.
            if (horizontalAngle < 30 || horizontalAngle > 70) {
                horizontalAngle = 45;
            }

            mPicSphere.setHorizontalAngle(horizontalAngle);
        }

        mSnapManager.setBypassProcessing(true);
        mSnapManager.queueSnapshot(true, 0);
        mPicSphereManager.getRenderer().setCamPreviewVisible(false);

        // Notify how to finish a sphere
        if (mPicSphere != null && mPicSphere.getPicturesCount() == 0) {
            CameraActivity.notify(mContext.getString(R.string.ps_long_press_to_stop), 4000);
        }
    }

    @Override
    public void onShutterButtonLongPressed(ShutterButton button) {
        if (mPicSphere != null) {
            if (mPicSphere.getPicturesCount() <= 1) {
                CameraActivity.notify(mCamManager.getContext()
                        .getString(R.string.picsphere_need_two_pics), 2000);
                return;
            }
            mPicSphereManager.startRendering(mPicSphere, mContext.getOrientation());
            mPicSphereManager.getRenderer().clearSnapshots();
            mPicSphere = null;

            mContext.setPicSphereUndoVisible(false);
        }
    }

    @Override
    public void onSnapshotShutter(SnapshotManager.SnapshotInfo info) {
        mPicSphereManager.getRenderer().addSnapshot(info.mThumbnail);
        mLastShotAngle = mPicSphereManager.getRenderer().getAngleAsVector();
    }

    @Override
    public void onSnapshotPreview(SnapshotManager.SnapshotInfo info) {

    }

    @Override
    public void onSnapshotProcessing(SnapshotManager.SnapshotInfo info) {

    }

    @Override
    public void onSnapshotSaved(SnapshotManager.SnapshotInfo info) {
        if (mPicSphere != null) {
            mPicSphere.addPicture(info.mUri, mLastShotAngle);
            mContext.setPicSphereUndoVisible(true);
            mContext.setHelperText("");
        } else {
            Log.e(TAG, "No current PicSphere");
        }
    }

    @Override
    public void onMediaSavingStart() {

    }

    @Override
    public void onMediaSavingDone() {

    }

    @Override
    public void onVideoRecordingStart() {

    }

    @Override
    public void onVideoRecordingStop() {

    }
}
