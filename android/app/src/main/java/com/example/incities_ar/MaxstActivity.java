package com.example.incities_ar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.incities_ar.sample.util.SampleUtil;
import com.maxst.ar.CameraDevice;
import com.maxst.ar.MaxstAR;
import com.maxst.ar.ResultCode;
import com.maxst.ar.TrackerManager;

import io.flutter.app.FlutterActivity;

public class MaxstActivity extends ARActivity {

    private ImageTrackerRenderer imageTargetRenderer;
    private GLSurfaceView glSurfaceView;
    private int preferCameraResolution = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxst);

        imageTargetRenderer = new ImageTrackerRenderer(this);
        glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(imageTargetRenderer);

        TrackerManager.getInstance().startTracker(TrackerManager.TRACKER_TYPE_IMAGE);
        TrackerManager.getInstance().addTrackerData("ImageTarget/Glacier.2dmap", true);
        TrackerManager.getInstance().addTrackerData("ImageTarget/Lego.2dmap", true);
        TrackerManager.getInstance().addTrackerData("ImageTarget/Blocks.2dmap", true);
        TrackerManager.getInstance().addTrackerData("ImageTarget/CarmenRicardo.2dmap", true);
//		TrackerManager.getInstance().addTrackerData("{\"image\":\"add_image\",\"image_path\":\"ImageTarget/Blocks.png\",\"image_width\":0.26,\"inclusion\":[{\"x\":50, \"y\":100, \"width\":400, \"height\":400}, {\"x\":400, \"y\":80, \"width\":400, \"height\":400}], \"exclusion\":[{\"x\":200, \"y\":200, \"width\":150, \"height\":150}]}", true);
        //TrackerManager.getInstance().addTrackerData("{\"image\":\"add_image\",\"image_path\":\"ImageTarget/Blocks.png\",\"image_width\":0.26}", true);
        //TrackerManager.getInstance().addTrackerData("{\"image\":\"add_image\",\"image_path\":\"ImageTarget/Glacier.png\",\"image_width\":0.26}", true);
        //TrackerManager.getInstance().addTrackerData("{\"image\":\"add_image\",\"image_path\":\"/sdcard/Download/sample/Blocks.png\",\"image_width\":0.26}", false);
        //TrackerManager.getInstance().addTrackerData("{\"image\":\"add_image\",\"image_path\":\"/sdcard/Download/sample/Glacier.png\",\"image_width\":0.26}", false);
        TrackerManager.getInstance().loadTrackerData();

        preferCameraResolution = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).getInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 0);

    }


    @Override
    protected void onResume() {
        super.onResume();

        glSurfaceView.onResume();
        TrackerManager.getInstance().startTracker(TrackerManager.TRACKER_TYPE_IMAGE);

        ResultCode resultCode = ResultCode.Success;
        switch (preferCameraResolution) {
            case 0:
                resultCode = CameraDevice.getInstance().start(0, 640, 480);
                break;

            case 1:
                resultCode = CameraDevice.getInstance().start(0, 1280, 720);
                break;

            case 2:
                resultCode = CameraDevice.getInstance().start(0, 1920, 1080);
                break;
        }

        if (resultCode != ResultCode.Success) {
            Toast.makeText(this, R.string.camera_open_fail, Toast.LENGTH_SHORT).show();
            finish();
        }

        MaxstAR.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        glSurfaceView.queueEvent(new Runnable() {
            @Override
            public void run() {
                imageTargetRenderer.destroyVideoPlayer();
            }
        });

        glSurfaceView.onPause();

        TrackerManager.getInstance().stopTracker();
        CameraDevice.getInstance().stop();
        MaxstAR.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
