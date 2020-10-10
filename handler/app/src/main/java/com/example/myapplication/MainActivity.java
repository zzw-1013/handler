package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvShow;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShow=findViewById(R.id.tv_show);
        btnStart=findViewById(R.id.btn_start);

        // 主线程中的handle，使用handleMessage回调处理消息中的内容
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                tvShow.setText(message.arg1 + " ");
            }
        };

        // 构建 Runnable 来创建线程，线程启动的时候会回调run方法
        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                int process = 0;
                while (process <= 100) {
                    Message message = new Message();
                    message.arg1 = process;
                    handler.sendMessage(message);
                    process += 10;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message message = handler.obtainMessage();
                message.arg1 = -1;
                handler.sendMessage(message);
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(null, myWorker, "WorkerThread");
                thread.start();
            }
        });
    }
}
