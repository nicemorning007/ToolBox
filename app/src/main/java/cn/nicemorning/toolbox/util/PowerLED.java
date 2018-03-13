package cn.nicemorning.toolbox.util;

import android.content.Context;
import android.hardware.Camera;
import android.widget.Toast;

/**
 * Created by Nicemorning on 13-Mar-18.
 * In package cn.nicemorning.toolbox.util
 */

public class PowerLED {
    boolean m_isOn;
    Camera m_Camera;

    public PowerLED(Context context) {
        m_isOn = false;
        try {
            m_Camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "请在设置中打开权限", Toast.LENGTH_SHORT).show();
        }
    }

    public void turnOn() {
        if (!m_isOn) {
            m_isOn = true;
            Camera.Parameters mParameters;
            mParameters = m_Camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            m_Camera.setParameters(mParameters);
        }
    }

    public void turnOff() {
        if (m_isOn) {
            m_isOn = false;
            Camera.Parameters mParameters;
            mParameters = m_Camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            m_Camera.setParameters(mParameters);
        }
    }

    public void destroy() {
        if (m_Camera != null) {
            m_Camera.release();
            m_Camera = null;
        }
    }
}
