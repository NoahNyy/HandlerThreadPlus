package nyy.org.handlerthreadplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import nyy.org.handlerthreadplus.util.HandlerThreadPlus;

/**
 * 主视图
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "N-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.send_btn).setOnClickListener(this);
        findViewById(R.id.send_img_btn).setOnClickListener(this);

        // 注册线程
        HandlerThreadPlus.register(SendMsgThread.class);
        HandlerThreadPlus.register(SendMsgImgThread.class);

        // 起线程观察所有线程
        new Thread(){
            @Override
            public void run() {
                for (;;) {
                    Log.d(TAG, "activeCount: " + Thread.activeCount());

                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "", e);
                    }
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_btn:
                HandlerThreadPlus.send(new SendMsgEvent());
                break;
            case R.id.send_img_btn:
                HandlerThreadPlus.send(new SendMsgImgEvent());
                break;
            default:
        }
    }
}
