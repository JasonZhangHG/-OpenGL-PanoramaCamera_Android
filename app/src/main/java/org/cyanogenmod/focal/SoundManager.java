package org.cyanogenmod.focal;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import fr.xplod.focal.R;

/**
 * Manages sounds played by the app. Since we have a limited
 * set of sounds that can be played, we pre-load them and we use
 * hardcoded values to play them quickly.
 */
public class SoundManager {
    public final static int SOUND_SHUTTER = 0;
    private final static int SOUND_MAX = 1;

    private static SoundManager mSingleton;

    public static SoundManager getSingleton() {
        if (mSingleton == null) {
            mSingleton = new SoundManager();
        }

        return mSingleton;
    }

    private SoundPool mSoundPool;
    private int[] mSoundsFD = new int[SOUND_MAX];

    /**
     * Default constructor
     * Creates the sound pool to play the audio files. Make sure to
     * call preload() before doing anything so the sounds are loaded!
     */
    private SoundManager() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_NOTIFICATION, 0);
    }

    public void preload(Context ctx) {
        mSoundsFD[SOUND_SHUTTER] = mSoundPool.load(ctx, R.raw.snd_capture, 1);
    }

    /**
     * Immediately play the specified sound
     *
     * @param sound The sound to play, see SoundManager.SOUND_*
     * @note Make sure preload() was called before doing play!
     */
    public void play(int sound) {
        mSoundPool.play(mSoundsFD[sound], 1.0f, 1.0f, 0, 0, 1.0f);
    }
}
