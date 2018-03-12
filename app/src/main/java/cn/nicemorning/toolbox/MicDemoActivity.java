package cn.nicemorning.toolbox;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class MicDemoActivity extends Activity {
    private MicrophoneThread microphoneThread = new MicrophoneThread();
    private MediaRecorder mARecorder;
    private boolean istrue = true;
    private File mAudiofile, mSampleDir;
    private ImageView iv_record_wave_left, iv_record_wave_right;
    private AnimationDrawable ad_left, ad_right;
    private TextView textView;
    private MHandler mHandler = new MHandler();

    class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    textView.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_mic_demo);
        init();
    }

    private void init() {
        textView = findViewById(R.id.textView1);
        iv_record_wave_left = findViewById(R.id.iv_record_wave_left);
        iv_record_wave_right = findViewById(R.id.iv_record_wave_right);
        ad_left = (AnimationDrawable) iv_record_wave_left.getBackground();
        ad_right = (AnimationDrawable) iv_record_wave_right.getBackground();
        ad_left.start();
        ad_right.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mARecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mARecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        mARecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mSampleDir = Environment.getExternalStorageDirectory();
            mAudiofile = File.createTempFile("IM" + System.currentTimeMillis(),
                    ".amr", mSampleDir);
        } catch (IOException e) {
            Log.d("MicDemoActivity", "sdcard access error");
        }
        mARecorder.setOutputFile(mAudiofile.getAbsolutePath());
        try {
            mARecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mARecorder.start();
        microphoneThread.start();
    }

    public void onBack(View view) {
        MicDemoActivity.this.finish();
    }

    class MicrophoneThread extends Thread {
        final float minAngle = (float) Math.PI * 4 / 11;
        float angle;

        @Override
        public void run() {
            while (istrue) {
                angle = 100 * minAngle * mARecorder.getMaxAmplitude() / 32768;
                if (angle > 100) {
                    angle = 100;
                }
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String p = decimalFormat.format(angle);
                mHandler.sendMessage(mHandler.obtainMessage(1, p));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
