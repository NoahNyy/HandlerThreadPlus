package nyy.org.handlerthreadplus.util;

import android.os.Message;
import android.util.Log;

import static nyy.org.handlerthreadplus.util.MHandlerMessageConstant.MSG_WHAT;

/**
 * @author niuyy
 */
public class MHandlerThreadManager {

    private static final String TAG = "-MHandlerThreadManager";

    private long sleepTime;
    private MHandlerThread mThread;
    private Class<? extends MHandlerThread> threadClz;

    /**
     * 构造器
     * @param threadClz 线程类字节码
     * @param sleepTime 线程等待时间
     */
    public MHandlerThreadManager(Class<? extends MHandlerThread> threadClz, long sleepTime) {
        this.threadClz = threadClz;
        this.sleepTime = sleepTime;
    }

    /**
     * 发送消息
     * @param event 消息对象
     */
    public synchronized void send(Object event) {
        // 构建消息
        Message msg = this.mThread == null || this.mThread.getMmHandler() == null ? new Message() : this.mThread.getMmHandler().obtainMessage();
        msg.what = MSG_WHAT;
        msg.obj = event;

        // 第一次创建线程 || 线程运行结束 -> 先启动线程,在发送消息
        if (this.mThread == null || this.mThread.getMmHandler() == null || Thread.State.TERMINATED.equals(this.mThread.getState())) {

            this.restartThread();
            this.mThread.getMmHandler().sendMessage(msg);

        } else if (Thread.State.TIMED_WAITING.equals(this.mThread.getState())) {

            // 线程睡眠中, 先发送消息, 再中断睡眠
            this.mThread.getMmHandler().sendMessage(msg);
            this.mThread.interrupt();

        } else {

            // 其余状态(运行中), 直接发送消息
            this.mThread.getMmHandler().sendMessage(msg);

        }
    }

    /**
     * 重启线程
     */
    private void restartThread() {
        try {
            this.mThread = this.threadClz.newInstance();
            this.mThread.setSleepTime(this.getSleepTime());
            this.mThread.start();
            while (this.mThread.getMmHandler() == null) {
                // 静静等待线程启动成功
                ThreadUtil.sleep(100L);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e(TAG, "create thread instance error", e);
        }
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Class<? extends MHandlerThread> getThreadClz() {
        return threadClz;
    }

}
