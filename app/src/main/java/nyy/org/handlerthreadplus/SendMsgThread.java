package nyy.org.handlerthreadplus;

import android.util.Log;

import nyy.org.handlerthreadplus.util.MHandlerThread;

/**
 * @author niuyy
 */
public class SendMsgThread extends MHandlerThread<SendMsgEvent> {

    private static final String TAG = "N-SendMsgThread";

    @Override
    protected void execute(SendMsgEvent obj) {
        long id = Thread.currentThread().getId();
        Log.i(TAG, "thread id: " + id);
    }
}
