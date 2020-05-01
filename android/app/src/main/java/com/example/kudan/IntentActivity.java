package com.example.kudan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kudan.ARActivity.MainActivity;
import com.example.kudan.model.Book;

import eu.kudan.kudan.ARAPIKey;
import io.flutter.app.FlutterActivity;

public class IntentActivity extends FlutterActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey("QDJFL+/L+a6KPmYOyGKdmxsaW45VNzMNWuP7ovEZMFzuBcw6IwDglo+a2pOwOV60bEMfNfEAFtmv/AaKWDGfC3V93MwJk3o74tC2Lh4OFokEWXqJmGGwrx7hAayiUdOGpz37ptsvlXHdap9Nl6cMGPe+cc2sVxBpGO60z5QES8VQvA3k7SnMNm0sYO1gJCb+Ryx/3EOuPHr8C506WbOjIqxrCWXOdp+wnQUQ4kZVI6KnXWEiZUu9Hr9/61PPEy+l62lgBneO0bFwTH6yriz+JuFaZIQW6NQmSEuin40HbVHpLJKPqWWVwOuocgExMkv7FBtfeonCM/zHGzxGRCXTlfPKQiVi5O3q5ArLXP1SJEFM0c4XqSREUcUnBQv6sqNht8nBPkg/qAeZGiIceIvoA6w8yTWV6BfBrnF76kbmgtkdNQ1XikmA73CsA3/upcxcpL4Rp2is4ZQyZvNgbZO7fvhqEIKbb0Cixm3b8uL6G45OQHyOYQBh+AolvOXUmQrYPZgXGXlWGPWPKJTrZg4rWrwceHvn+yGdtnaWCuLghL2BKBBziWb0d9AnQ8xgS16JNDlxVdFjZgucimffdDKFCJfpFVzktCPt6rQLGsuU6BUvpdImPnHOlL/pFX34IQPqZZ7Y2XrV+XxHaoJi3bWym5YESmUsrJkhAwKELv0obU0=");
        permissionsRequest();
        // Sending image id to FullScreenActivity
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        /*if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }*/
    }



    // Requests app permissions
    public void permissionsRequest() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 111);
        }
    }

    private void permissionsNotSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setTitle("Permissions Required");
        builder.setMessage("Please enable the requested permissions in the app settings in order to use this demo app");
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener () {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                System.exit(1);
            }
        });
        AlertDialog noInternet = builder.create();
        noInternet.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                /*Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                permissionsNotSelected();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Toast.makeText(this, "Hellow", Toast.LENGTH_LONG).show();
        }
    }
}
