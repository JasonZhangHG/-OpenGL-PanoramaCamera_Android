package org.cyanogenmod.focal;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.getkeepsafe.relinker.ReLinker;

/**
 * Manages the application itself (on top of the activity), mainly to force Camera getting
 * closed in case of crash.
 */
public class CameraApplication extends Application {
    private final static String TAG = "FocalApp";
    private Thread.UncaughtExceptionHandler mDefaultExHandler;
    private CameraManager mCamManager;

    private Thread.UncaughtExceptionHandler mExHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            if (mCamManager != null) {
                Log.e(TAG, "Uncaught exception! Closing down camera safely firsthand");
                mCamManager.forceCloseCamera();
            }

            mDefaultExHandler.uncaughtException(thread, ex);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
                //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
                //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                            Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
                            Toast.makeText(CameraApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });

        ReLinker.log(logcatLogger).force().recursively().loadLibrary(this, "jni_mosaic2", new ReLinker.LoadListener() {
            @Override
            public void success() {
                Log.e("ReLinker", "success");
            }

            @Override
            public void failure(Throwable t) {
                Log.e("ReLinker", "failed");
                t.printStackTrace();
            }
        });


        mDefaultExHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mExHandler);
    }

    private ReLinker.Logger logcatLogger = new ReLinker.Logger() {
        @Override
        public void log(String message) {
            Log.d("ReLinker", message);
        }
    };

    public void setCameraManager(CameraManager camMan) {
        mCamManager = camMan;
    }


}
