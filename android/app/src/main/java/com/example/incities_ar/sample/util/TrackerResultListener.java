package com.example.incities_ar.sample.util;

public interface TrackerResultListener {
    void sendData(String metaData);
    void sendFusionState(int state);
}
