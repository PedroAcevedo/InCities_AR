package com.example.kudan.ARActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kudan.R;
import com.example.kudan.model.Book;

import java.util.ArrayList;

import eu.kudan.kudan.ARAPIKey;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARImageTrackable;
import eu.kudan.kudan.ARImageTrackableListener;
import eu.kudan.kudan.ARImageTracker;
import eu.kudan.kudan.ARTextureMaterial;
import eu.kudan.kudan.ARVideoNode;
import eu.kudan.kudan.ARVideoTexture;

public class MainActivity extends ARActivity implements ARImageTrackableListener
{

    private ArrayList<ARImageTrackable> trackables;
    private Book currentBook;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_visor);
        //this.trackables = new ArrayList<>();
        //this.currentBook = (Book) getIntent().getSerializableExtra("Book");
    }


    public void setup()
    {
        super.setup();

        /*// Get the trackable Manager singleton
        ARImageTracker trackableManager = ARImageTracker.getInstance();

        trackableManager.initialise();

        ArrayList<String> elements = currentBook.getAugmentedReferences();
        for (String augmented: elements){
            // Create our trackable with an image
            ARImageTrackable trackable = createTrackable(augmented + "", augmented + "");
            trackables.add(trackable);
        }
        for (int i = 0; i < trackables.size(); i++){
            trackableManager.addTrackable(trackables.get(i));
            // Create an image node using an image of the kudan cow
            String currentElement = currentBook.getMultimediaFile(elements.get(i));
            if (i==2){
                addVideoNode(i,currentElement);
                Log.i("VIDEO","hola");
            }else{
                addImageNode(i,currentElement);
            }

            trackables.get(i).addListener(this);
        }*/
    }

    /*private void addImageNode(int reference, String element){
        ARImageNode imageNode = new ARImageNode(element);
        trackables.get(reference).getWorld().addChild(imageNode);
        // Image scale
        ARTextureMaterial textureMaterial = (ARTextureMaterial)imageNode.getMaterial();
        float scale = trackables.get(reference).getWidth() / textureMaterial.getTexture().getWidth();
        imageNode.scaleByUniform(scale);
    }

    private void addVideoNode(int reference, String element) {

            ARVideoTexture videoTexture = new ARVideoTexture();
            videoTexture.loadFromAsset(element);
            ARVideoNode videoNode = new ARVideoNode(videoTexture);
            trackables.get(reference).getWorld().addChild(videoNode);
            float scale = trackables.get(reference).getWidth() / videoTexture.getWidth();
            videoNode.scaleByUniform(scale);
    }

    private ARImageTrackable createTrackable(String trackableName, String assetName)
    {
        // Create a new trackable instance with a name
        ARImageTrackable trackable = new ARImageTrackable(trackableName);
        // Load the  image for this marker
        trackable.loadFromAsset(assetName);

        return trackable;
    }*/

    // ARImageTrackableListener interface functions, these are called in response to various tracking events
    @Override
    public void didDetect(ARImageTrackable arImageTrackable) {
        Log.i("Marker","Did Detect");
    }

    @Override
    public void didLose(ARImageTrackable arImageTrackable) {
        Log.i("Marker","Did Lose");
    }

    @Override
    public void didTrack(ARImageTrackable arImageTrackable) {
        Log.i("Marker","Did Track");
    }


}