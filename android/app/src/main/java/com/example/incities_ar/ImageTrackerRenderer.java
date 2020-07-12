package com.example.incities_ar;


import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.example.incities_ar.sample.arobject.Image2DRenderer;
import com.example.incities_ar.sample.arobject.SphereRenderer;
import com.maxst.ar.CameraDevice;
import com.maxst.ar.MaxstAR;
import com.maxst.ar.MaxstARUtil;
import com.maxst.ar.Trackable;
import com.maxst.ar.TrackedImage;
import com.maxst.ar.TrackerManager;
import com.maxst.ar.TrackingResult;
import com.maxst.ar.TrackingState;
import com.example.incities_ar.sample.arobject.BackgroundRenderHelper;
import com.example.incities_ar.sample.arobject.ChromaKeyVideoRenderer;
import com.example.incities_ar.sample.arobject.ColoredCubeRenderer;
import com.example.incities_ar.sample.arobject.TexturedCubeRenderer;
import com.example.incities_ar.sample.arobject.VideoRenderer;
import com.maxst.videoplayer.VideoPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class ImageTrackerRenderer implements Renderer {

    public static final String TAG = ImageTrackerRenderer.class.getSimpleName();

    private Image2DRenderer texturedCubeRenderer;
    private Image2DRenderer logoIncities;
    private ColoredCubeRenderer coloredCubeRenderer;
    private VideoRenderer videoRenderer;
    private VideoRenderer assetRenderer;
    private ChromaKeyVideoRenderer chromaKeyVideoRenderer;

    private int surfaceWidth;
    private int surfaceHeight;
    private BackgroundRenderHelper backgroundRenderHelper;
    private String filename;
    private String map;

    private final Activity activity;

    ImageTrackerRenderer(Activity activity, String filename, String map) {
        this.activity = activity;
        this.filename = filename;
        this.map = map;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        System.out.println(map);

        switch (filename.split("/")[6].split("\\.")[1]){
            case "jpg":
            case "png":
                File initialFile = new File(filename);
                Bitmap bitmap = null;
                try {
                    InputStream inputStream = new FileInputStream(initialFile);
                    BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
                    bitmap = BitmapFactory.decodeStream(bufferedStream);
                    texturedCubeRenderer = new Image2DRenderer();
                    System.out.println(filename);
                    texturedCubeRenderer.setTextureBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "mp4":
                assetRenderer = new VideoRenderer();
                VideoPlayer player = new VideoPlayer(activity);
                player.openVideo(filename);
                assetRenderer.setVideoPlayer(player);
                break;
        }


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
        
        boolean assetDetected = false;


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        for (int i = 0; i < trackingResult.getCount(); i++) {
            Trackable trackable = trackingResult.getTrackable(i);
            //Log.i(TAG, "Image width : " + trackable.getWidth() + ", height : " + trackable.getHeight());
            if(trackable.getName().equals(map)){
                    switch (filename.split("/")[6].split("\\.")[1]) {
                        case "png":
                        case "jpg":
                            texturedCubeRenderer.setProjectionMatrix(projectionMatrix);
                            texturedCubeRenderer.setTransform(trackable.getPoseMatrix());
                            texturedCubeRenderer.setTranslate(0.0f, 0.0f, 0.0f);
                            texturedCubeRenderer.ScaleBitMap(trackable.getWidth());
                            texturedCubeRenderer.setScale(texturedCubeRenderer.getWidth(), texturedCubeRenderer.getHeight(), 1.0f);
                            texturedCubeRenderer.draw();
                            break;
                        case "mp4":
                            assetDetected = true;
                            if (assetRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
                                    assetRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
                                assetRenderer.getVideoPlayer().start();
                            }
                            assetRenderer.setProjectionMatrix(projectionMatrix);
                            assetRenderer.setTransform(trackable.getPoseMatrix());
                            assetRenderer.setTranslate(0.0f, 0.0f, 0.0f);
                            assetRenderer.setScale(trackable.getWidth(), trackable.getHeight(), 1.0f);
                            assetRenderer.draw();
                            break;

                    }
                    break;
            }

        if(assetRenderer!= null){
            if (!assetDetected) {
                if (assetRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PLAYING) {
                    assetRenderer.getVideoPlayer().pause();
                }
            }
        }

    }

    }

    void destroyVideoPlayer() {
        if(assetRenderer != null){
        assetRenderer.getVideoPlayer().destroy();
        }
    }
}