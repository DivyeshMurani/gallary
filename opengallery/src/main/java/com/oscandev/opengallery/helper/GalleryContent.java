package com.oscandev.opengallery.helper;

public class GalleryContent {

    private String imageFullPth;
    private boolean isSelectImage;

    public GalleryContent(String imageFullPth, boolean isSelectImage) {
        this.imageFullPth = imageFullPth;
        this.isSelectImage = isSelectImage;
    }

    public String getImageFullPth() {
        return imageFullPth;
    }

    public void setImageFullPth(String imageFullPth) {
        this.imageFullPth = imageFullPth;
    }

    public boolean isSelectImage() {
        return isSelectImage;
    }

    public void setSelectImage(boolean selectImage) {
        isSelectImage = selectImage;
    }
}
