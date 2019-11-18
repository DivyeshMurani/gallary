package com.oscandev.opengallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.oscandev.opengallery.adapter.BaseRecyclerViewAdapter;
import com.oscandev.opengallery.adapter.FolderLoadAdapter;
import com.oscandev.opengallery.frags.HomeFragment;

import com.oscandev.opengallery.frags.ListItemFragment;
import com.oscandev.opengallery.helper.Constance;
import com.oscandev.opengallery.helper.ImagesLoader;

import java.util.ArrayList;
import java.util.List;

public class MainContentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FolderLoadAdapter adapter;
    private List<String> list = new ArrayList<>();

    private int showContent = Constance.Key.IMAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);


        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        initToolbar();

        Intent intent = getIntent();
        showContent = intent.getIntExtra(Constance.Key.KEY_SHOW_CONTENT, Constance.Key.IMAGE);

        if (showContent == Constance.Key.BOTH) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_frame, ListItemFragment.getInstance(Constance.Key.VALUE_ALL_MEDIA, Constance.Key.BOTH))
                    .commit();
        } else {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_frame, HomeFragment.getInstance(showContent))
                    .commit();

        }

    }

    private void initToolbar() {
        toolbar.setTitle("Gallery");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {

        adapter = new FolderLoadAdapter(list);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.addOnClickLis(new BaseRecyclerViewAdapter.OnClickLis() {
            @Override
            public void addOnClick(int position, String folder_name) {

//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainContentActivity.this,recyclerView);
                Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                intent.putExtra("folder_name", folder_name);
                startActivity(intent);
//                startActivity(intent,optionsCompat.toBundle());
            }
        });
    }

    private void loadFiles() {
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkCallingPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    &&
                    checkCallingPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permission, 100);
            } else {
                imgLoad();
            }
        } else {
            imgLoad();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    imgLoad();
                } else {
                    Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void imgLoad() {
        list.addAll(new ImagesLoader().getAllFolder(getApplicationContext()));
        adapter.notifyDataSetChanged();
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        setToolbarTitle("Gallery");
        super.onBackPressed();
    }


}
