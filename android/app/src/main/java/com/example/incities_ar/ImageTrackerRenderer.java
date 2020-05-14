package com.example.incities_ar;


import android.app.Activity;
import android.graphics.Bitmap;
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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class ImageTrackerRenderer implements Renderer {

    public static final String TAG = ImageTrackerRenderer.class.getSimpleName();

    private Image2DRenderer texturedCubeRenderer;
    private ColoredCubeRenderer coloredCubeRenderer;
    private VideoRenderer videoRenderer;
    private VideoRenderer assetRenderer;
    private ChromaKeyVideoRenderer chromaKeyVideoRenderer;

    private int surfaceWidth;
    private int surfaceHeight;
    private BackgroundRenderHelper backgroundRenderHelper;
    private String filename = "Linea de tiempo .jpg";

    private final Activity activity;

    ImageTrackerRenderer(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);


        switch (filename.split("\\.")[1]){
            case "jpg":
            case "png":
                Bitmap bitmap = MaxstARUtil.getBitmapFromAsset(filename, activity.getAssets());
                texturedCubeRenderer = new Image2DRenderer();
                texturedCubeRenderer.setTextureBitmap(bitmap);
                break;
            case "mp4":
                assetRenderer = new VideoRenderer();
                VideoPlayer player = new VideoPlayer(activity);
                player.openVideo(filename);
                assetRenderer.setVideoPlayer(player);
                break;

        }

        coloredCubeRenderer = new ColoredCubeRenderer();

        videoRenderer = new VideoRenderer();
        VideoPlayer player = new VideoPlayer(activity);
        videoRenderer.setVideoPlayer(player);
        player.openVideo("VideoSample.mp4");

        chromaKeyVideoRenderer = new ChromaKeyVideoRenderer();
        player = new VideoPlayer(activity);
        chromaKeyVideoRenderer.setVideoPlayer(player);
        player.openVideo("ShutterShock.mp4");

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
        boolean blocksDetected = false;
        boolean assetDetected = false;


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        for (int i = 0; i < trackingResult.getCount(); i++) {
            Trackable trackable = trackingResult.getTrackable(i);

            //Log.i(TAG, "Image width : " + trackable.getWidth() + ", height : " + trackable.getHeight());

            switch (trackable.getName()) {
                case "Lego":
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
                case "Glacier":
                    blocksDetected = true;
                    if (chromaKeyVideoRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
                            chromaKeyVideoRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
                        chromaKeyVideoRenderer.getVideoPlayer().start();
                    }
                    chromaKeyVideoRenderer.setProjectionMatrix(projectionMatrix);
                    chromaKeyVideoRenderer.setTransform(trackable.getPoseMatrix());
                    chromaKeyVideoRenderer.setTranslate(0.0f, 0.0f, 0.0f);
                    chromaKeyVideoRenderer.setScale(trackable.getWidth(), trackable.getHeight(), 1.0f);
                    chromaKeyVideoRenderer.draw();
                    break;
                case "Blocks":
                    texturedCubeRenderer.setProjectionMatrix(projectionMatrix);
                    texturedCubeRenderer.setTransform(trackable.getPoseMatrix());
                    texturedCubeRenderer.setTranslate(0, 0, -trackable.getHeight()*0.25f*0.25f);
                    texturedCubeRenderer.setScale(0.48f, 1.2f, trackable.getHeight()*0.25f*0.5f);
                    texturedCubeRenderer.draw();
                    break;
                case "CarmenRicardo":

                    switch (filename.split("\\.")[1]){
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
                default:
                    coloredCubeRenderer.setProjectionMatrix(projectionMatrix);
                    coloredCubeRenderer.setTransform(trackable.getPoseMatrix());
                    coloredCubeRenderer.setTranslate(0, 0, -trackable.getHeight()*0.25f*0.25f);
                    coloredCubeRenderer.setScale(trackable.getWidth()*0.25f, trackable.getHeight()*0.25f, trackable.getHeight()*0.25f*0.5f);
                    coloredCubeRenderer.draw();
            }
        }

        if (!legoDetected) {
            if (videoRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PLAYING) {
                videoRenderer.getVideoPlayer().pause();
            }
        }

        if(assetRenderer!= null){
            if (!assetDetected) {
                if (assetRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PLAYING) {
                    assetRenderer.getVideoPlayer().pause();
                }
            }
        }

        if (!blocksDetected) {
            if (chromaKeyVideoRenderer.getVideoPlayer().getState() == VideoPlayer.STATE_PLAYING) {
                chromaKeyVideoRenderer.getVideoPlayer().pause();
            }
        }
    }

    void destroyVideoPlayer() {
        videoRenderer.getVideoPlayer().destroy();
        chromaKeyVideoRenderer.getVideoPlayer().destroy();
    }
}