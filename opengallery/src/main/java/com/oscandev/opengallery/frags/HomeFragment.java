package com.oscandev.opengallery.frags;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oscandev.opengallery.ListItemActivity;

import com.oscandev.opengallery.R;
import com.oscandev.opengallery.adapter.BaseRecyclerViewAdapter;
import com.oscandev.opengallery.adapter.FolderLoadAdapter;
import com.oscandev.opengallery.helper.ImagesLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private FolderLoadAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        initRecyclerView();
        loadFiles();
    }


    private void initRecyclerView() {

        adapter = new FolderLoadAdapter(list);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        adapter.addOnClickLis(new BaseRecyclerViewAdapter.OnClickLis() {
            @Override
            public void addOnClick(int position, String folder_name) {

                Intent intent = new Intent(activity, ListItemActivity.class);
                intent.putExtra("folder_name", folder_name);
//                startActivity(intent);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id.content_frame, ListItemFragment.getInstance(folder_name))
                        .commit();

            }
        });
    }

    private void loadFiles() {
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
                    Toast.makeText(activity, "Permission is not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void imgLoad() {
        list.addAll(new ImagesLoader().getAllFolder(activity));
        adapter.notifyDataSetChanged();
    }
}
