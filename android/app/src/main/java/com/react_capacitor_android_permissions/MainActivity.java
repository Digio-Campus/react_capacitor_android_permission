package com.react_capacitor_android_permissions;

import android.os.Bundle;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(CameraPermissionPlugin.class);
        super.onCreate(savedInstanceState);
    }
}
