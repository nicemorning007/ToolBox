package cn.nicemorning.toolbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.nicemorning.toolbox.view.CircleMenuLayout;

public class MainActivity extends AppCompatActivity {

    private CircleMenuLayout mCircleMenuLayout;
    private String[] mItemTexts = new String[]{"放大镜", "工具尺", "分贝测试仪", "手电筒",
            "计算器", "SOS"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
