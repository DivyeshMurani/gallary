package com.oscandev.opengallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OpenGalleryBuilder {
    private Activity activity;
    public static final int OPEN_REQUEST_CODE = 1_000;

    public OpenGalleryBuilder(Activity activity) {
        this.activity = activity;
    }

    public void build() {
        if (activity == null) {
            Log.d("Gallery: ", "Context is null");
            return;
        }
        Intent intent = new Intent(activity, MainContentActivity.class);
        activity.startActivityForResult(intent, OPEN_REQUEST_CODE);
    }
}
