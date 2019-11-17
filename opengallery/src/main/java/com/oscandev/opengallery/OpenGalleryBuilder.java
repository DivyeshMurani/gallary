package com.oscandev.opengallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.oscandev.opengallery.helper.Constance;

public class OpenGalleryBuilder {
    private Activity activity;
    public static final int OPEN_REQUEST_CODE = 1_000;

    private int showContent = -1;

    public OpenGalleryBuilder(Activity activity) {
        this.activity = activity;
    }

    public OpenGalleryBuilder showContent(int showContent) {
        this.showContent = showContent;
        return this;
    }


    public void build() {

        if (activity == null) {
            Log.d("Gallery: ", "Context is null");
            return;
        }

        Intent intent = new Intent(activity, MainContentActivity.class);
        intent.putExtra(Constance.Key.KEY_SHOW_CONTENT, showContent);
        activity.startActivityForResult(intent, OPEN_REQUEST_CODE);
    }
}
