/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.example.incities_ar;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incities_ar.ARActivity;
import com.example.incities_ar.R;
import com.example.incities_ar.sample.util.SampleUtil;
import com.maxst.ar.CameraDevice;
import com.maxst.ar.MaxstAR;
import com.maxst.ar.ResultCode;
import com.maxst.ar.TrackerManager;

public class CloudRecognizerActivity extends ARActivity {

	private CloudRecognizerRenderer cloudTargetRenderer;
	private GLSurfaceView glSurfaceView;
	private int preferCameraResolution = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_maxst);

		cloudTargetRenderer = new CloudRecognizerRenderer(this);
		cloudTargetRenderer.listener = resultListener;

		glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(cloudTargetRenderer);

		preferCameraResolution = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).getInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();

		glSurfaceView.onResume();

		/* Add SecertId and SecretKey */
		TrackerManager.getInstance().setCloudRecognitionSecretIdAndKey("d163b3f29279437bbeed6756ce50f8ec", "fc1e67674c6a432099f3139eb8ff71ce");
		TrackerManager.getInstance().startTracker(TrackerManager.TRACKER_TYPE_CLOUD_RECOGNIZER);

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

		glSurfaceView.onPause();

		TrackerManager.getInstance().stopTracker();
		CameraDevice.getInstance().stop();
		MaxstAR.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private CloudRecognitionResultListener resultListener = new CloudRecognitionResultListener() {
		@Override
		public void sendData(String cloudName, final String cloudMetaData) {
			(CloudRecognizerActivity.this).runOnUiThread(new Runnable(){
				@Override
				public void run() {

				}
			});
		}
	};

	public interface CloudRecognitionResultListener {

		void sendData(String cloudName, String cloudMetaData);
	}

}
