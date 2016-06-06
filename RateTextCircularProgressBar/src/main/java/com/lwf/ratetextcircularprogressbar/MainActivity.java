package com.lwf.ratetextcircularprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RateTextCircularProgressBar progressBar;
    private Button btn;
    private WorkHandlerThread workHandlerThread;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (RateTextCircularProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (workHandlerThread != null) {
                stopWorkThread();
            }
            num = 0;
            startWorkThread();
        }
    };

    private void startWorkThread() {
        workHandlerThread = new WorkHandlerThread("work", callback);
        workHandlerThread.startWork();
    }

    private void stopWorkThread() {
        if (workHandlerThread != null) {
            workHandlerThread.stopWork();
            workHandlerThread = null;
        }
    }

    private ICallback callback = new ICallback() {
        @Override
        public boolean handleMessage() {
            if (num == 100) {
                stopWorkThread();
            }
            progressBar.setProgress(num);
            num++;
            return false;
        }
    };
}
