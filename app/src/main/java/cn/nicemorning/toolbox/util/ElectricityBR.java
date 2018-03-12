package cn.nicemorning.toolbox.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Nicemorning on 11-Mar-18.
 * In package cn.nicemorning.toolbox.util
 */

public class ElectricityBR extends BroadcastReceiver {
    Dialog dialog = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);
            String technology = intent.getStringExtra("technology");
            if (dialog == null) {
                dialog = new AlertDialog.Builder(context).setTitle("电池电量")
                        .setMessage("电量为：" + String.valueOf(level * 100 / scale)
                                + "%\n电压为：" + String.valueOf((float) voltage / 1000)
                                + "V\n电池类型：" + technology
                                + "\n温度为：" + String.valueOf((float) temperature / 10)
                                + "℃").setNegativeButton("Close",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                dialog.show();
            }
        }
    }
}
