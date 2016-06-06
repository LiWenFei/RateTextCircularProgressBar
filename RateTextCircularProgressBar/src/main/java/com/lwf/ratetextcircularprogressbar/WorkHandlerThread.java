package com.lwf.ratetextcircularprogressbar;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * Created by liwenfei on 16/6/2.
 */
public class WorkHandlerThread extends HandlerThread {

    private Handler handler;
    private ICallback mCallback;
    private long mDelayMillis = 0;

    public WorkHandlerThread(String name, ICallback callback) {
        super(name);
        mCallback = callback;
    }

    public void startWork() {
        if (handler == null) {
            handler = new Handler(Looper.myLooper(), callback);
        }
        this.start();
        post();
    }

    public void stopWork() {
        if (runnable != null) {
            runnable = null;
        }
        if (handler != null) {
            handler = null;
        }
        this.quit();
    }

    public void setDelayMillis(long delayMillis) {
        mDelayMillis = delayMillis;
        if (mDelayMillis <= 0) {
            mDelayMillis = 1000;
        } else {
            mDelayMillis = delayMillis;
        }
    }

    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(final Message msg) {
            if (handler == null)
                return false;
            if (mCallback != null) {
                mCallback.handleMessage();
            }
            return false;
        }
    };

    private void post() {
        if (handler == null)
            return;
        handler.postDelayed(runnable, mDelayMillis);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (handler == null)
                return;
            handler.postDelayed(runnable, mDelayMillis);
            if (mCallback != null) {
                mCallback.handleMessage();
            }
        }
    };

}
