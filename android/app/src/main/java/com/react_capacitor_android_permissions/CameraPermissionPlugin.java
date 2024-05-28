package com.react_capacitor_android_permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

@CapacitorPlugin(name = "CameraPermission", permissions = {
        @Permission(
                alias = "camera",
                strings = {Manifest.permission.CAMERA}
        )
})
public class CameraPermissionPlugin extends Plugin {
    private void loadCamera(PluginCall call) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getBridge().getActivity().getPackageManager()) != null) {
            startActivityForResult(call, takePictureIntent, "cameraResultCallback");
        } else {
            Log.i("debug", "En loadCamera no ha entrado en el if");
            call.reject("No camera available");
        }
    }

    @PluginMethod()
    public void takePhoto(PluginCall call) {
        if (!hasRequiredPermissions()) {
            requestAllPermissions(call, "cameraPermsCallback");
        } else {
            loadCamera(call);
        }
    }

    @PermissionCallback
    private void cameraPermsCallback(PluginCall call) {
        if (getPermissionState("camera") == PermissionState.GRANTED) {
            loadCamera(call);
        } else {
            call.reject("Permission is required to take a picture");
        }
    }

    @ActivityCallback
    private void cameraResultCallback(PluginCall call, ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            assert data != null;
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            assert photo != null;
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String photoBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            JSObject ret = new JSObject();
            ret.put("value", photoBase64);
            call.success(ret);
        } else {
            call.reject("Failed to take picture");
        }
    }
}
