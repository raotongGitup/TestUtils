package com.example.testutils.activity;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.camer.CameraHelper;
import com.example.camer.IDCardScanBoxView;
import com.example.testutils.R;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView surfaceView;
    private IDCardScanBoxView idCardScanBoxView;
    private ImageView selectPhotoBtn;
    private ImageView takePhotoBtn;
    private ImageView flashBtn;
    private boolean isFlashlightOpen;

    private ScaleGestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        surfaceView = ((SurfaceView) findViewById(R.id.surfaceView));
        // idCardScanBoxView = ((IDCardScanBoxView) findViewById(R.id.idCardScanBoxView));
        selectPhotoBtn = ((ImageView) findViewById(R.id.selectPhotoBtn));
        takePhotoBtn = ((ImageView) findViewById(R.id.takePhotoBtn));
        flashBtn = ((ImageView) findViewById(R.id.flashBtn));
//        if (!SystemUtils.isCameraFlash(this)) {
//            flashBtn.setVisibility(View.GONE);
//        }
        surfaceView.setOnClickListener(this);
        selectPhotoBtn.setOnClickListener(this);
        takePhotoBtn.setOnClickListener(this);
        flashBtn.setOnClickListener(this);

        initSurface();
    }

    private void initSurface() {
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                CameraHelper.getInstance().openCamera(holder, 0, 720, 1280, surfaceView.getWidth(), surfaceView.getHeight());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                CameraHelper.getInstance().releaseCamera();
            }
        });

        gestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            float mScaleFactor;

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (detector.getCurrentSpan() > mScaleFactor) {
                    CameraHelper.getInstance().zoomOut();
                } else {
                    CameraHelper.getInstance().zoomIn();
                }
                mScaleFactor = detector.getCurrentSpan();
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                mScaleFactor = detector.getCurrentSpan();
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                mScaleFactor = detector.getCurrentSpan();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.surfaceView:
                CameraHelper.getInstance().autoFocus(null);
                break;
            case R.id.selectPhotoBtn:
                CameraHelper.getInstance().switchCamera();
                break;
            case R.id.takePhotoBtn:
                CameraHelper.getInstance().takePhotoWithAutoFocus(new CameraHelper.OnCaptureCallback() {
                    @Override
                    public void onCapture(byte[] data, Bitmap bitmap) {
                        if (bitmap != null) {
                            selectPhotoBtn.setImageBitmap(bitmap);

                            CameraHelper.getInstance().closeFlashLight();
                            isFlashlightOpen = false;
                        }
                    }
                });
                break;
            case R.id.flashBtn:
                if (isFlashlightOpen) {
                    CameraHelper.getInstance().closeFlashLight();
                    isFlashlightOpen = false;

                } else {
                    CameraHelper.getInstance().openFlashLight();
                    isFlashlightOpen = true;

                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        if (gestureDetector != null && pointerCount > 1) {
            gestureDetector.onTouchEvent(event);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            CameraHelper.getInstance().autoFocus(null);
        }

        return super.onTouchEvent(event);
    }


}
