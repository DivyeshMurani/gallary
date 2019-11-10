package com.oscandev.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.oscandev.gallery.adapter.BaseRecyclerViewAdapter;
import com.oscandev.gallery.adapter.FolderLoadAdapter;
import com.oscandev.gallery.helper.ImagesLoader;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FolderLoadAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);


//        call methods
        initRecyclerView();
        initToolbar();
        loadFiles();
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
                Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                intent.putExtra("folder_name", folder_name);
                startActivity(intent);
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
}