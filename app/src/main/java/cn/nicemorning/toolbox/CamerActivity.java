package cn.nicemorning.toolbox;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

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
    private SeekBar mSeekBar;

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
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress + 50;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                parameters.setZoom(value);
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            }
        });
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
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.set("orientation", "portrait");
            parameters.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
            parameters.setRotation(mCameraId == BACK_CAMERA ? ROTATION : REVERT + ROTATION);
            mZoomMax = value;
            parameters.setZoom(mZoomMax);
            mCamera.setParameters(parameters);
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
                mFlag = true;
            } catch (Exception e) {
                mCamera.release();
                Log.d("CamerActivity", e.getMessage());
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int PREVIEW_WIDTH,
                               int PREVIEW_HEIGHT) {
        startCamera();
    }

    public void surfaceCreated(SurfaceHolder holder) {

    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void onBack(View view) {
        CamerActivity.this.finish();
    }
}