package org.cyanogenmod.focal.widgets;

import android.content.Context;
import android.hardware.Camera;
import android.view.View;

import org.cyanogenmod.focal.CameraManager;
import org.cyanogenmod.focal.SettingsStorage;

import fr.xplod.focal.R;

/**
 * Skin Tone Enhancement widget (Qualcomm)
 * Most info (values, etc) from
 * https://codeaurora.org/cgit/quic/la/platform/packages/apps/Camera/commit/?id=9d48710275915ac0672cb7c1bc1b9aa14d70e8b0
 */
public class SkinToneWidget extends WidgetBase {
    private static final String KEY_PARAMETER = "skinToneEnhancement";
    private static final int MIN_SCE_FACTOR = -10;
    private static final int MAX_SCE_FACTOR = +10;

    private WidgetOptionButton mMinusButton;
    private WidgetOptionButton mPlusButton;
    private WidgetOptionLabel mValueLabel;


    private class MinusClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setToneValue(Math.max(getToneValue() - 1, MIN_SCE_FACTOR));
        }
    }

    private class PlusClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setToneValue(Math.min(getToneValue() + 1, MAX_SCE_FACTOR));
        }
    }

    public SkinToneWidget(CameraManager cam, Context context) {
        super(cam, context, R.drawable.ic_widget_skintone);

        // Add views in the widget
        mMinusButton = new WidgetOptionButton(R.drawable.ic_widget_timer_minus, context);
        mPlusButton = new WidgetOptionButton(R.drawable.ic_widget_timer_plus, context);
        mValueLabel = new WidgetOptionLabel(context);

        mMinusButton.setOnClickListener(new MinusClickListener());
        mPlusButton.setOnClickListener(new PlusClickListener());

        addViewToContainer(mMinusButton);
        addViewToContainer(mValueLabel);
        addViewToContainer(mPlusButton);

        restoreValueFromStorage(KEY_PARAMETER);
        mValueLabel.setText(Integer.toString(getToneValue()));

        getToggleButton().setHintText(R.string.widget_skintone);
    }

    @Override
    public boolean isSupported(Camera.Parameters params) {
        return params.get(KEY_PARAMETER) != null;
    }

    public int getToneValue() {
        Camera.Parameters params = mCamManager.getParameters();
        String value = params.get(KEY_PARAMETER);

        if (value != null) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    public void setToneValue(int value) {
        String valueStr = Integer.toString(value);

        mCamManager.setParameterAsync(KEY_PARAMETER, valueStr);
        SettingsStorage.storeCameraSetting(mWidget.getContext(), mCamManager.getCurrentFacing(),
                KEY_PARAMETER, valueStr);
        mValueLabel.setText(valueStr);
    }
}
