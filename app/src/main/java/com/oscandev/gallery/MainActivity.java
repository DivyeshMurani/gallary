package com.oscandev.gallery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oscandev.opengallery.OpenGalleryBuilder;
import com.oscandev.opengallery.helper.Constance;

import java.util.ArrayList;
import java.util.List;

import static com.oscandev.opengallery.OpenGalleryBuilder.OPEN_REQUEST_CODE;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        openGallery();

        init();
    }

    private void openGallery() {

        new OpenGalleryBuilder(MainActivity.this)
                .showContent(Constance.Key.VIDEO)
                .selectionLimit(10)
                .build();


    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle("MainActivity");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (OPEN_REQUEST_CODE == requestCode && data != null) {
            ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra("value");

            String link = "";
            for (String path :
                    list) {
                link += "/n" + path;

                Log.d("TAG_", "Path: " + path);
            }
            ((TextView)findViewById(R.id.txt_list)).setText(link);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
