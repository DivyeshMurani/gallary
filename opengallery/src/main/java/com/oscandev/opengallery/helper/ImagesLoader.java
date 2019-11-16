package com.oscandev.opengallery.helper;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ImagesLoader {


    final String[] columns = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DISPLAY_NAME
    };//get all columns of type images
    final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date

    public void build(Context context) {
        Cursor imagecursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int displayNameId = imagecursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            String strFolderName = imagecursor.getString(displayNameId);


        }
    }

    public List<String> getAllFolder(Context context) {
        List<String> list = new ArrayList<>();
        final String[] projection = {
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };


        list.add("All");

        String BUCKET_GROUP_BY =
                "1) GROUP BY 1,(2";
        String BUCKET_ORDER_BY = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
//        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                BUCKET_GROUP_BY,
                null,
                BUCKET_ORDER_BY
        );

        if (cursor.moveToFirst()) {
            int bucketColumn = cursor.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int idColumn = cursor.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_ID);
            do {
                // Get the field values
                String bucket = cursor.getString(bucketColumn);
                String id = cursor.getString(idColumn);
                Log.d("TAG_", "Folder Name " + bucket);

                list.add(bucket);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<String> getFolderItem(Context context, String folderName) {
        List<String> list = new ArrayList<>();

        Cursor imagecursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                        MediaStore.Images.Media.DATA + " like ? ",
                        new String[]{"%/" + folderName + "/%"}, null);

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            list.add(imagecursor.getString(dataColumnIndex));//get Image from column index
        }
        return list;
    }

    public List<GalleryContent> getFolderItemContent(Context context, String folderName) {
        ArrayList<GalleryContent> list = new ArrayList<>();
        Cursor imagecursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                        MediaStore.Images.Media.DATA + " like ? ",
                        new String[]{"%/" + folderName + "/%"}, orderBy + " DESC");
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            list.add(new GalleryContent(imagecursor.getString(dataColumnIndex), false));
        }
        return list;
    }

    public ArrayList<GalleryContent> getAllImages(Context context) {
        ArrayList<GalleryContent> list = new ArrayList<>();
        Cursor imagecursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int displayNameId = imagecursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            String strFolderName = imagecursor.getString(displayNameId);

            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            String dataColumn = imagecursor.getString(dataColumnIndex);
            list.add(new GalleryContent(dataColumn, false));

        }
        return list;
    }

    private void loadWithRX() {
        Observable.just("").flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return null;
            }
        });
    }
}
