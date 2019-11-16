package com.oscandev.opengallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OpenGalleryBuilder {
    private Context context;
    public OpenGalleryBuilder(Context context) {
        this.context = context;
    }

    public void build() {
        if (context == null) {
            Log.d("Gallery: ", "Context is null");
            return;
        }

        Intent intent = new Intent(context, MainContentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
