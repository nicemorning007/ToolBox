package cn.nicemorning.toolbox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.nicemorning.toolbox.util.ElectricityBR;
import cn.nicemorning.toolbox.view.CircleMenuLayout;

public class MainActivity extends Activity implements SensorEventListener {

    private CircleMenuLayout mCircleMenuLayout;
    private String[] mItemTexts = new String[]{"放大镜", "工具尺", "分贝测试仪", "手电筒",
            "计算器", "SOS"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};
    private ImageView image_znz;
    private float angle = 0f;
    private SensorManager sensorManager;
    private TextView batterytv;
    private int[] batterystatusimgs = {R.drawable.battery1,
            R.drawable.battery2, R.drawable.battery3};
    private int[] batterystatuspercent = {75, 30, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_znz = findViewById(R.id.iv_znz);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        batterytv = findViewById(R.id.batterytv);
        registerReceiver(batInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        mCircleMenuLayout = findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(
                new CircleMenuLayout.OnMenuItemClickListener() {
                    @Override
                    public void itemClick(View view, int pos) {
                        if (pos == 0) {
                            Toast.makeText(getApplicationContext(), "点击菜单按钮0执行方法",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (pos == 1) {
                            Toast.makeText(getApplicationContext(), "点击菜单按钮1执行方法",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (pos == 2) {
                            Toast.makeText(getApplicationContext(), "点击菜单按钮2执行方法",
                                    Toast.LENGTH_SHORT).show();

                        }
                        if (pos == 3) {
                            Toast.makeText(getApplicationContext(), "点击菜单按钮3执行方法",
                                    Toast.LENGTH_SHORT).show();

                        }
                        if (pos == 4) {
                            Toast.makeText(getApplicationContext(), "点击菜单按钮4执行方法",
                                    Toast.LENGTH_SHORT).show();

                        }
                        if (pos == 5) {
                            Toast.makeText(getApplicationContext(), "点击菜单按钮5执行方法",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void itemCenterClick(View view) {
                        Toast.makeText(getApplicationContext(), "单击中心按钮执行方法",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float zangle = event.values[0];
            RotateAnimation rotateAnimation = new RotateAnimation(angle, -zangle,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            image_znz.startAnimation(rotateAnimation);
            angle = -zangle;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor((Sensor.TYPE_ORIENTATION)),
                SensorManager.SENSOR_DELAY_GAME);
    }

    private BroadcastReceiver batInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int intLevel = intent.getIntExtra("level", 0);
                int intScale = intent.getIntExtra("scale", 100);
                onBatteryInfoReceiver(intLevel, intScale);
            }
        }
    };

    public void onBatteryInfoReceiver(int intLevel, int intScale) {
        int bp = intLevel * 100 / intScale;
        batterytv.setText(bp + "%");
        if (bp >= batterystatuspercent[2]) {
            batterytv.setBackgroundResource(batterystatusimgs[2]);
        }
        if (bp >= batterystatuspercent[1]) {
            batterytv.setBackgroundResource(batterystatusimgs[1]);
        }
        if (bp >= batterystatuspercent[0]) {
            batterytv.setBackgroundResource(batterystatusimgs[0]);
        }
    }

    public void onDc(View view) {
        ElectricityBR electricityBR = new ElectricityBR();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        MainActivity.this.registerReceiver(electricityBR, filter);
    }

}
