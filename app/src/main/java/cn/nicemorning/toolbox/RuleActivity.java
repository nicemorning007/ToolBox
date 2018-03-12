package cn.nicemorning.toolbox;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import cn.nicemorning.toolbox.view.RulerView;

public class RuleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_rule);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        RelativeLayout relativeLayout = findViewById(R.id.main_layout);
        RulerView rulerView = new RulerView(this, displayMetrics);
        relativeLayout.addView(rulerView);
    }
}
