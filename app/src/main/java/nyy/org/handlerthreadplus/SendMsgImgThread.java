package nyy.org.handlerthreadplus;

import android.util.Log;

import nyy.org.handlerthreadplus.util.MHandlerThread;

/**
 * @author niuyy
 */
public class SendMsgImgThread extends MHandlerThread<SendMsgImgEvent> {

    private static final String TAG = "N-SendMsgImgThread";

    @Override
    protected void execute(SendMsgImgEvent obj) {
        long id = Thread.currentThread().getId();
        Log.i(TAG, "thread id: " + id);
    }
}
