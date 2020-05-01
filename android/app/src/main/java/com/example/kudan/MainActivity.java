package com.example.kudan;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

public class MainActivity extends FlutterActivity {


    private static final String CHANNEL = "com.example.kudan";

  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    GeneratedPluginRegistrant.registerWith(flutterEngine);

    new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
            @Override
      public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                if (call.method.equals("showNativeView")) {
                        // Sending image id to FullScreenActivity
                        Intent i = new Intent(getApplicationContext(), IntentActivity.class);
                        // passing array index
                        i.putExtra("id", "");
                        startActivity(i);
                        result.success(true);
                } else {
                    result.notImplemented();
                }
            } 
          });

  }


}
