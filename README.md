# HandlerThreadPlus

[项目地址](https://github.com/NoahNyy/HandlerThreadPlus)

# 概述

本质上是一个安卓的 HandlerThread，但是，HandlerThread 是一直运行的，HandlerThreadPlus 在没有消息处理一段时间后，线程会自动结束。

优点：

1. 避免使用太多 HandlerThread 造成应用卡顿


# 使用

### 1. 引用

拷贝 util 包下的工具类到自己项目中

![工具类](https://upload-images.jianshu.io/upload_images/1218612-b2e4940e57eb4a5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 ### 2. 创建线程和事件

Event 可以是任意一个 JavaBean，如：

```java
public class SendMsgEvent {
  private String content;
}
```

Thread 继承 MHandlerThread，并制定泛型，如：

```java
public class SendMsgThread extends MHandlerThread<SendMsgThread> {

    @Override
    protected void execute(SendMsgThread event) {
        // TODO 线程要处理的事情
    }
}
```

### 3. 注册线程

```
HandlerThreadPlus.register(SendMsgThread.class);
```

或

```
HandlerThreadPlus.register(SendMsgThread.class, 6000l);
```

则线程 SendMsgThread 在处理完最后一个消息时，等待 6000 毫秒没有消息进入则结束线程

修改默认线程等待时间
```java
HandlerThreadPlus.setAllThreadSleepTime(long allThreadSleepTime)
```

### 4. 发布事件

```java
HandlerThreadPlus.send(Object event);
```

# 注意

- 目前 Event 和 Thread 仅支持一对一
















