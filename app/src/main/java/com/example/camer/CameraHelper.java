package com.example.camer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

/**
 * create at 2020/7/28
 * author raotong
 * Description : 自定义相机的工具类
 */
public class CameraHelper {
    private final String TAG = "CameraManager=====>";

    private Camera camera;
    private ToneGenerator tone;
    private boolean isPreviewing;

    private SurfaceHolder surfaceHolder;
    private int format = PixelFormat.JPEG;  //图片格式
    private int picQuality = 100;  //照片质量

    private int frameWidth;    //扫描区 宽度 即拍照图片宽度
    private int frameHeight;   //扫描区 高度 即拍照图片高度
    private int surfaceWidth;  //显示区 宽度 即相机扫描的宽度
    private int surfaceHeight; //显示区 高度 即相机扫描的高度

    public static CameraHelper getInstance() {
        return Singleton.SINGLETON.getInstance();
    }

    public interface OnCaptureCallback {
        void onCapture(byte[] data, Bitmap bitmap);
    }

    private int cameraPosition = 1; //当前选用的摄像头，1后置 0前置
    public void switchCamera() {
        int cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    //重新打开
                    resOpenCamera(i);
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    resOpenCamera(i);
                    cameraPosition = 1;
                    break;
                }
            }
        }
    }

    /**
     * 启动相机
     */
    public void openCamera(SurfaceHolder holder, int format, int frameWidth, int frameHeight, int surfaceWidth, int surfaceHeight) {

        this.surfaceHolder = holder;
        this.format = format != 0 ? format : PixelFormat.JPEG;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;

        try {
            releaseCamera();

            camera = Camera.open();
            camera.setPreviewDisplay(holder);
            initParameters();
            startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resOpenCamera(int i) {
        try {
            releaseCamera();

            camera = Camera.open(i);
            camera.setPreviewDisplay(this.surfaceHolder);
            initParameters();
            startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始预览
     */
    public void startPreview() {
        if (camera != null && !isPreviewing) {
            camera.startPreview();
            camera.autoFocus(null);
            isPreviewing = true;
        }
    }
    /**
     * 停止预览
     */
    public void stopPreview() {
        if (camera != null && isPreviewing) {
            camera.stopPreview();
            isPreviewing = false;
        }
    }

    /**
     * 释放相机
     */
    public void releaseCamera() {
        if (camera != null) {
            if (isPreviewing) {
                stopPreview();
            }
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
            isPreviewing = false;
        }
    }
    /**
     * 聚焦
     */
    public void autoFocus(Camera.AutoFocusCallback autoFocusCallback) {
        if (camera != null && isPreviewing) {
            camera.autoFocus(autoFocusCallback);
        }
    }
    /**
     * 拍照
     */
    public void takePicture(final OnCaptureCallback callback) {
        if (camera != null && isPreviewing) {
            Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
                @Override
                public void onShutter() {
                    if (tone == null) {
                        tone = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
                    }
                    tone.startTone(ToneGenerator.TONE_PROP_BEEP);
                }
            };
            Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    stopPreview();
                    Bitmap bitmap = dealwithResult(data);
                    if (callback != null) {
                        callback.onCapture(data, bitmap);
                    }
                }
            };

            camera.takePicture(shutterCallback, null, pictureCallback);
        }
    }

    /**
     * 聚焦并拍照
     */
    public void takePhotoWithAutoFocus(final OnCaptureCallback callback) {
        autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                takePicture(callback);
            }
        });
    }

    /**
     * 打开闪光灯
     */
    public void openFlashLight() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        }
    }
    /**
     * 关闭闪光灯
     */
    public void closeFlashLight() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
        }
    }

    /**
     * 相机放大
     */
    public void zoomOut() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (!parameters.isZoomSupported()) return;

            int zoom = parameters.getZoom() + 1;
            if (zoom < parameters.getMaxZoom()) {
                parameters.setZoom(zoom);
                camera.setParameters(parameters);
            }
        }
    }
    /**
     * 相机缩小
     */
    public void zoomIn() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (!parameters.isZoomSupported()) return;

            int zoom = parameters.getZoom() - 1;
            if (zoom >= 0) {
                parameters.setZoom(zoom);
                camera.setParameters(parameters);
            }
        }
    }
    /**
     * 初始化相机参数
     */
    private void initParameters() {
        try {
//            int orientation = LoanApplication.orientation;
//            if(orientation == Configuration.ORIENTATION_PORTRAIT){
//                camera.setDisplayOrientation(90);//竖屏
//            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
//                camera.setDisplayOrientation(180);   //横屏
//            }
            camera.setDisplayOrientation(90);//竖屏
            Camera.Parameters parameters = camera.getParameters();
            //设置照片格式
            parameters.setPictureFormat(this.format);
            //照片质量
            parameters.set("jpeg-quality", this.picQuality);

            //自动对焦
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }

            if (frameWidth > 0 && frameHeight > 0) {
                //获取支持系统的预览尺寸
                List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                //获取最佳预览尺寸
                Camera.Size previewSize = getOptimalPreviewSize(supportedPreviewSizes, frameWidth, frameHeight);
                try {
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                } catch (Exception e) {
                    Log.e(TAG, "不支持的相机预览分辨率: " + previewSize.width + " × " + previewSize.height);
                }
            }

            if (surfaceWidth > 0 && surfaceHeight > 0) {
                //获取支持的照片尺寸
                List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
                //获取照片最佳尺寸
                Camera.Size pictureSize = getPicutreSize(supportedPictureSizes, surfaceWidth, surfaceHeight);
                try {
                    parameters.setPictureSize(pictureSize.width, pictureSize.height);
                } catch (Exception e) {
                    Log.e(TAG, "不支持的照片尺寸: " + pictureSize.width + " × " + pictureSize.height);
                }
            }

            camera.setParameters(parameters);

        } catch (Exception e) {
            Log.e(TAG, "相机参数设置错误");
        }
    }
    /**
     * 获取最佳预览尺寸
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) width / height;
        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = height;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double r = size.width * 1.0 / size.height * 1.0;
            if (r != 4 / 3 || r != 3 / 4 || r != 16 / 9 || r != 9 / 16) {
                continue;
            }

            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
    /**
     * 设置照片尺寸为最接近屏幕尺寸
     *
     * @param list
     * @return
     */
    private Camera.Size getPicutreSize(List<Camera.Size> list, int screenWidth, int screenHeight) {
        Camera.Size pictureSize = null;

        int approach = Integer.MAX_VALUE;
        for (Camera.Size size : list) {
            int temp = Math.abs(size.width - screenWidth + size.height - screenHeight);
            System.out.println("approach: " + approach + ", temp: " + temp + ", size.width: " + size.width + ", size.height: " + size.height);
            if (approach > temp) {
                approach = temp;
                pictureSize = size;
            }
        }
        return pictureSize;

//      //降序
//      if(list.get(0).width>list.get(list.size()-1).width){
//          int len = list.size();
//          list = list.subList(0, len/2==0? len/2 : (len+1)/2);
//          this.pictureSize = list.get(list.size()-1);
//      }else{
//          int len = list.size();
//          list = list.subList(len/2==0? len/2 : (len-1)/2, len-1);
//          this.pictureSize = list.get(0);
//      }
    }
    private Bitmap dealwithResult(byte[] data) {
        if (data == null || data.length == 0) return null;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bitmap == null || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return null;
        }
        //竖屏旋转照片
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        if (frameWidth == 0 || frameHeight == 0) {
            return bitmap;
        }
        int x = (bitmap.getWidth() - frameWidth) / 2;
        int y = (bitmap.getHeight() - frameHeight) / 2;
        return Bitmap.createBitmap(bitmap, x, y, frameWidth, frameHeight);
    }

    private enum Singleton {
        SINGLETON;

        private CameraHelper manager;

        Singleton() {
            manager = new CameraHelper();
        }

        public CameraHelper getInstance() {
            return manager;
        }
    }


}
