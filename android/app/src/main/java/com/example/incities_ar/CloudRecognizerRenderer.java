/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.example.incities_ar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import com.example.incities_ar.sample.arobject.BackgroundRenderHelper;
import com.example.incities_ar.sample.arobject.ColoredCubeRenderer;
import com.example.incities_ar.sample.arobject.TexturedCubeRenderer;
import com.example.incities_ar.sample.arobject.VideoRenderer;
import com.maxst.ar.CameraDevice;
import com.maxst.ar.MaxstAR;
import com.maxst.ar.MaxstARUtil;
import com.maxst.ar.Trackable;
import com.maxst.ar.TrackedImage;
import com.maxst.ar.TrackerManager;
import com.maxst.ar.TrackingResult;
import com.maxst.ar.TrackingState;
import com.maxst.videoplayer.VideoPlayer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class CloudRecognizerRenderer implements Renderer {

	public static final String TAG = CloudRecognizerRenderer.class.getSimpleName();

	private TexturedCubeRenderer texturedCubeRenderer;
	private ColoredCubeRenderer coloredCubeRenderer;
	private VideoRenderer videoRenderer;

	private int surfaceWidth;
	private int surfaceHeight;
	private BackgroundRenderHelper backgroundRenderHelper;

	private final Activity activity;

	public CloudRecognizerActivity.CloudRecognitionResultListener listener = null;
	private String beforeCloudName = "";

	CloudRecognizerRenderer(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Bitmap bitmap = MaxstARUtil.getBitmapFromAsset("MaxstAR_Cube.png", activity.getAssets());

		texturedCubeRenderer = new TexturedCubeRenderer();
		texturedCubeRenderer.setTextureBitmap(bitmap);

		coloredCubeRenderer = new ColoredCubeRenderer();

		videoRenderer = new VideoRenderer();
		VideoPlayer player = new VideoPlayer(activity);
		videoRenderer.setVideoPlayer(player);
		player.openVideo("VideoSample.mp4");

		backgroundRenderHelper = new BackgroundRenderHelper();
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		surfaceWidth = width;
		surfaceHeight = height;

		MaxstAR.onSurfaceChanged(width, height);
	}

	@Override
	public void onDrawFrame(GL10 unused) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		GLES20.glViewport(0, 0, surfaceWidth, surfaceHeight);

		TrackingState state = TrackerManager.getInstance().updateTrackingState();
		TrackingResult trackingResult = state.getTrackingResult();

		TrackedImage image = state.getImage();
		float[] projectionMatrix = CameraDevice.getInstance().getProjectionMatrix();
		float[] backgroundPlaneInfo = CameraDevice.getInstance().getBackgroundPlaneInfo();

		backgroundRenderHelper.drawBackground(image, projectionMatrix, backgroundPlaneInfo);

		boolean legoDetected = false;

		GLES20.glEnable(GLES20.GL_DEPTH_TEST);

		if(trackingResult.getCount() == 1) {
			Trackable trackable = trackingResult.getTrackable(0);
			String cloudName = trackable.getCloudName();
			String cloudMetaData = trackable.getCloudMetaData();

			if(!cloudName.equals(beforeCloudName)) {
				beforeCloudName = cloudName;
				if(listener != null) {
					listener.sendData(cloudName, cloudMetaData);
				}
			}

			switch (cloudName) {
				case "CarmenRicardo":
					legoDetected = true;
					if (videoRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
							videoRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
						videoRenderer.getVideoPlayer().start();
					}
					videoRenderer.setProjectionMatrix(projectionMatrix);
					videoRenderer.setTransform(trackable.getPoseMatrix());
					videoRenderer.setTranslate(0.0f, 0.0f, 0.0f);
					videoRenderer.setScale(trackable.getWidth(), trackable.getHeight(), 1.0f);
					videoRenderer.draw();
					break;
			}
		}

		if (!legoDetected) {
			if (videoRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PLAYING) {
				videoRenderer.getVideoPlayer().pause();
			}
		}
	}
}
