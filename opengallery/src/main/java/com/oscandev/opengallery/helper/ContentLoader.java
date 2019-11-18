package com.oscandev.opengallery.helper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ContentLoader {

    final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date

    public void build(Context context) {
        final String[] columns = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME
        };//get all columns of type images


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


    public List<String> getAllFolder(Context context, int media) {
        List<String> list = new ArrayList<>();
        final String[] projection = Constance.MediaStoreQuery.getMediaAllFolder(media);

        list.add("All");

        String BUCKET_GROUP_BY =
                "1) GROUP BY 1,(2";
        String BUCKET_ORDER_BY = projection[1];
//        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

        Cursor cursor = context.getContentResolver().query(
                Constance.MediaStoreQuery.getUri(media),
                projection,
                BUCKET_GROUP_BY,
                null,
                BUCKET_ORDER_BY
        );

        if (cursor.moveToFirst()) {
            int bucketColumn = cursor.getColumnIndex(
                    projection[1]);
            int idColumn = cursor.getColumnIndex(
                    projection[0]);
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


    public List<GalleryContent> getFolderItemContent(Context context, String folderName, int media) {
        final String[] columns = Constance.MediaStoreQuery.getMediaStore(media);
        ArrayList<GalleryContent> list = new ArrayList<>();
        Cursor imagecursor = context.getContentResolver()
                .query(Constance.MediaStoreQuery.getUri(media), columns,
                        columns[0] + " like ? ",
                        new String[]{"%/" + folderName + "/%"}, columns[4] + " DESC");
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(columns[0]);//get column index
            list.add(new GalleryContent(imagecursor.getString(dataColumnIndex), false));
        }
        return list;
    }

    public ArrayList<GalleryContent> getAllImages(Context context) {


        final String[] columns = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME
        };

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


    public ArrayList<GalleryContent> getAllImages(Context context, int media) {


        final String[] columns = Constance.MediaStoreQuery.getMediaStore(media);

        ArrayList<GalleryContent> list = new ArrayList<>();
        Cursor imagecursor = context.getContentResolver().query(
                Constance.MediaStoreQuery.getUri(media), columns, null,
                null, orderBy + " DESC");

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
//            int displayNameId = imagecursor.getColumnIndex(columns[2]);
//            String strFolderName = imagecursor.getString(displayNameId);

            int dataColumnIndex = imagecursor.getColumnIndex(columns[0]);//get column index
            String dataColumn = imagecursor.getString(dataColumnIndex);
            list.add(new GalleryContent(dataColumn, false));

        }
        return list;
    }

    public static void getAllMedia(Activity activity){

        String[] columns = { MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE,
        };
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        final String orderBy = MediaStore.Files.FileColumns.DATE_ADDED;
        Uri queryUri = MediaStore.Files.getContentUri("external");

        @SuppressWarnings("deprecation")
        Cursor imagecursor = activity.managedQuery(queryUri,
                columns,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );


        int image_column_index = imagecursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        int count = imagecursor.getCount();
        Bitmap[] thumbnails = new Bitmap[count];
        String [] arrPath = new String[count];
        int[] typeMedia = new int[count];

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
