package com.oscandev.opengallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.oscandev.opengallery.adapter.ItemShowAdapter;
import com.oscandev.opengallery.helper.ImagesLoader;

import java.util.ArrayList;
import java.util.List;

public class ListItemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ItemShowAdapter adapter;
    private List<String> list = new ArrayList<>();
    private String folder_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        folder_name = getIntent().getStringExtra("folder_name");
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
        toolbar.setTitle(folder_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initRecyclerView() {

        adapter = new ItemShowAdapter(list);
        recyclerView.setHasFixedSize(false);
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerView.setAdapter(adapter);
    }

    private void loadFiles() {
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkCallingPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    &&
                    checkCallingPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permission, 100);
            } else {
                ImageLoad();
            }
        } else {
            ImageLoad();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    ImageLoad();
                } else {
                    Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ImageLoad() {
        list.addAll(new ImagesLoader().getFolderItem(getApplicationContext(), folder_name));
        adapter.notifyDataSetChanged();
        Log.d("TAG_", "list size: " + list.size());
    }
}
