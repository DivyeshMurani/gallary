# select image and video from custom gallery


Use this snipped code in activity or fragment 

``` 
new OpenGalleryBuilder(MainActivity.this)
                .showContent(Constance.Key.VIDEO)
                .selectionLimit(10)
                .build();
```
