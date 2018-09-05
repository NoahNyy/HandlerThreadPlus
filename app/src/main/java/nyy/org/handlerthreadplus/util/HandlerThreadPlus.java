package nyy.org.handlerthreadplus.util;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 增强版 HandlerThread
 * @author niuyy
 */
public abstract class HandlerThreadPlus {

    private static final String TAG = "N-HandlerThreadPlus";

    private static final Map<Class, MHandlerThreadManager> threadManagerMap = new ConcurrentHashMap<>();

    private static long allThreadSleepTime = 60 * 1000;

    /**
     * 发送消息
     * @param event 消息
     */
    public static void send(Object event){
        MHandlerThreadManager mHandlerThreadManager = threadManagerMap.get(event.getClass());
        if (mHandlerThreadManager == null) {
            throw new IllegalStateException(event.getClass() + " has not register yet. see @HandlerThreadPlus.register");
        } else {
            mHandlerThreadManager.send(event, null);
        }
    }

    /**
     * 延时发送消息
     * @param event 消息
     * @param delayMillis 延时时间
     */
    public static void sendMessageDelayed(Object event, long delayMillis){
        MHandlerThreadManager mHandlerThreadManager = threadManagerMap.get(event.getClass());
        if (mHandlerThreadManager == null) {
            throw new IllegalStateException(event.getClass() + " has not register yet. see @HandlerThreadPlus.register");
        } else {
            mHandlerThreadManager.send(event, delayMillis);
        }
    }

    /**
     * 注册线程
     * @param thread 线程
     */
    public static void register(@NonNull Class<? extends MHandlerThread> thread){
        register(thread, allThreadSleepTime);
    }

    /**
     * 注册线程
     * @param thread 线程
     * @param sleepTime 销毁前的等待时间
     */
    public static void register(@NonNull Class<? extends MHandlerThread> thread, long sleepTime){

        Class eventClz = GenericSuperclassUtil.getActualTypeArgument(thread);
        if (eventClz == null) {
            throw new IllegalStateException(thread + " has not set a GenericType when extend Class MHandlerThread");
        }

        synchronized (HandlerThreadPlus.class) {
            MHandlerThreadManager mHandlerThreadManager = threadManagerMap.get(eventClz);
            if (mHandlerThreadManager == null) {
                MHandlerThreadManager newMHandlerThreadManager = new MHandlerThreadManager(thread, sleepTime);
                threadManagerMap.put(eventClz, newMHandlerThreadManager);
            } else if (!thread.equals(mHandlerThreadManager.getThreadClz())) {
                throw new IllegalStateException("event: " + eventClz + "has been registered by thread: " + mHandlerThreadManager.getThreadClz());
            }
        }

    }

    /**
     * 获取线程销毁前的等待时间
     * @return 线程销毁前的等待时间
     */
    public static long getAllThreadSleepTime() {
        return allThreadSleepTime;
    }

    /**
     * 设置线程销毁前的等待时间
     * @param allThreadSleepTime 线程销毁前的等待时间
     */
    public static void setAllThreadSleepTime(long allThreadSleepTime) {
        HandlerThreadPlus.allThreadSleepTime = allThreadSleepTime;
    }
}
