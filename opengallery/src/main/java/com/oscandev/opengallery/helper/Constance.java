package com.oscandev.opengallery.helper;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class Constance {

    public static int SELECT_CONTENT_LIMIT = 100;

    public static class Key {
        public static final String KEY_VIDEO = "video";
        public static final String KEY_IMAGE = "image";
        public static final String KEY_SIZE = "size";
        public static final String KEY_LIMIT = "limit";
        public static final String KEY_SHOW_CONTENT = "show_content";
        public static final String VALUE_ALL_MEDIA = "all_media";


        public static final int IMAGE = 0;
        public static final int VIDEO = 1;
        public static final int BOTH = 2;
    }

    static class MediaStoreQuery {

        static String[] getMediaAllFolder(int media) {
            ArrayList<String[]> list = new ArrayList<>();

            final String[] image = {
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            };

            final String[] videos = {
                    MediaStore.Video.Media.BUCKET_ID,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME
            };


            list.add(image);
            list.add(videos);
            return list.get(media);
        }

        static String[] getMediaStore(int media) {

            ArrayList<String[]> list = new ArrayList<>();

            String[] imageColumns = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_TAKEN
            };

            String[] videoColumns = {
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DATE_TAKEN
            };



            list.add(imageColumns);
            list.add(videoColumns);

            return list.get(media);
        }

        static Uri getUri(int media) {
            ArrayList<Uri> list = new ArrayList<>();
            list.add(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            list.add(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

            return list.get(media);
        }




    }



}
