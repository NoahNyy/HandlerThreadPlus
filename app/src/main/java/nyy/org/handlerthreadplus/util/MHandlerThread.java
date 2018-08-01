package nyy.org.handlerthreadplus.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 增强版 HandlerThread
 * @author niuyy
 */
public abstract class MHandlerThread<T> extends Thread {

    private static final int MSG_WHAT = 1;
    private static final String TAG = "N-MHandlerThread";

    private volatile Handler mmHandler;
    private long sleepTime;

    /**
     * 线程具体的执行逻辑
     */
    protected abstract void execute(T obj);

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        try {
            Looper.prepare();
            this.mmHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    try {

                        // 具体执行
                        execute((T) msg.obj);

                        if (!this.hasMessages(MSG_WHAT)) {
                            ThreadUtil.sleep(getSleepTime());
                            if (!this.hasMessages(MSG_WHAT)) {
                                // 结束线程
                                Looper.myLooper().quitSafely();
                            }
                        }

                    } catch (Throwable e) {
                        Log.e(TAG, "Handler", e);
                    }
                }
            };
            Looper.loop();
        } catch (Throwable e) {
            Log.e(TAG, "Thread", e);
        } finally {
            this.mmHandler = null;
        }
    }

    public Handler getMmHandler() {
        return mmHandler;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }
}
