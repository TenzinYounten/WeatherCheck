package com.example.weathercheck.Utility;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PermissionChecker {

    public static final int PERMISSION_REQUEST_CODE = 200;
    private Activity activity;

    public PermissionChecker(Activity activity) {
        this.activity = activity;
    }

    public Boolean globalCheckPermission() {
        int result = ContextCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(activity, CAMERA);
        int result2 = ContextCompat.checkSelfPermission(activity, READ_CONTACTS);
        int result3 = ContextCompat.checkSelfPermission(activity, CALL_PHONE);
        int result4 = ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED
                && result4 == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {

        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION,
                        CAMERA, READ_CONTACTS, CALL_PHONE, WRITE_EXTERNAL_STORAGE}
        , PERMISSION_REQUEST_CODE);

    }

}
