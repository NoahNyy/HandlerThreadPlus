package nyy.org.handlerthreadplus.util;

/**
 * 线程工具类
 * @author niuyy
 */
public class ThreadUtil {
    /**
     * <p>让调用该方法的线程睡眠一段时间</p>
     * <p>
     * NOTE:
     * <pre>
     *     1. 在睡眠之前会复位中断标志位
     *     2. 捕捉 {@link InterruptedException} 异常, 但是不做处理
     * </pre>
     * @param time
     */
    public static void sleep(long time) {
        try {
            // 清除状态位
            Thread.interrupted();
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // 线程重新进入循环
        }
    }
}
