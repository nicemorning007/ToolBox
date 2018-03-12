package cn.nicemorning.toolbox;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by Nicemorning on 12-Mar-18.
 * In package cn.nicemorning.toolbox
 */

public class CamerActivity extends Activity implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private int value = 50;
    private Camera.Parameters parameters;
    private Camera mCamera;
    private SurfaceHolder holder;
    private boolean mFlag = true;
    private int mCameraId = 0;
    private static final int BACK_CAMERA = 0;
    private static final int ROTATION = 90;
    private static final int REVERT = 180;
    private static final int PREVIEW_WIDTH = 320;
    private static final int PREVIEW_HEIGHT = 240;
    private int mZoomMax;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSurfaceView = findViewById(R.id.surfaceView);
        holder = mSurfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception e) {
            Log.d("CamerActivity", e.getMessage());
        }
    }

    private void startCamera() {
        if (mFlag) {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }
        try {
            mCamera = Camera.open(mCameraId);
        } catch (Exception e) {
            e.printStackTrace();
            mCamera = null;
        }
        if (mCamera != null) {
            mCamera.setDisplayOrientation(mCameraId == BACK_CAMERA ? ROTATION : ROTATION);
            parameters = mCamera.getParameters();
        }
    }
}
