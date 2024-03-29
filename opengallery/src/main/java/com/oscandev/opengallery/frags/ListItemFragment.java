package com.oscandev.opengallery.frags;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oscandev.opengallery.R;
import com.oscandev.opengallery.adapter.ItemShowAdapter;
import com.oscandev.opengallery.helper.Constance;
import com.oscandev.opengallery.helper.ContentLoader;
import com.oscandev.opengallery.helper.GalleryContent;

import java.util.ArrayList;
import java.util.List;

public class ListItemFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ItemShowAdapter adapter;
    private List<GalleryContent> list = new ArrayList<>();
    private String folder_name = "";
    private ArrayList<String> selectedList = new ArrayList<>();
    private LinearLayout ll_bottom_info;
    private TextView txt_select_count;


    private int media = Constance.Key.IMAGE;


    public static ListItemFragment getInstance(String folderName, int media) {
        Bundle bundle = new Bundle();
        bundle.putString("folderName", folderName);
        bundle.putInt("media", media);
        ListItemFragment fragment = new ListItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        ll_bottom_info = view.findViewById(R.id.ll_bottom_info);
        txt_select_count = view.findViewById(R.id.txt_select_count);

        ll_bottom_info.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        folder_name = bundle.getString("folderName");
        media = bundle.getInt("media", Constance.Key.IMAGE);
        activity.setToolbarTitle(folder_name);

        initRecyclerView();

        view.findViewById(R.id.rl_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String name : selectedList) {
                    Log.d("TAG_", "Selected name: " + name);
                }

                if (selectedList.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("value", selectedList);
//                    activity.setResult(Activity.RESULT_OK, intent);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();

//                    activity.finish();
                }
            }
        });
        loadFiles();
    }

    private void initRecyclerView() {
        adapter = new ItemShowAdapter(list, "");
        recyclerView.setHasFixedSize(false);

//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.setAdapter(adapter);

        adapter.setSelectImage(new ItemShowAdapter.SelectImage() {
            @Override
            public void selectImage(int position, RelativeLayout img_selected) {
                GalleryContent content = list.get(position);

                boolean isSelected = content.isSelectImage();


                if (!isSelected && Constance.SELECT_CONTENT_LIMIT == selectedList.size()) {
                    Toast.makeText(activity, "Must be select "+selectedList.size(), Toast.LENGTH_SHORT).show();
                    return;
                }


                if (isSelected) {
                    img_selected.setVisibility(View.GONE);
                    content.setSelectImage(false);
                    list.set(position, content);
                    if (selectedList.size() > 0) {
                        selectedList.remove(content.getImageFullPth());
                    }

                } else {
                    img_selected.setVisibility(View.VISIBLE);
                    content.setSelectImage(true);
                    list.set(position, content);
                    selectedList.add(content.getImageFullPth());
                }

                if (selectedList.size() > 0) {
                    ll_bottom_info.setVisibility(View.VISIBLE);
                    txt_select_count.setText(String.valueOf(selectedList.size()));

                } else {
                    ll_bottom_info.setVisibility(View.GONE);
                }

//                adapter.notifyDataSetChanged();
                adapter.notifyItemChanged(position);
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
                    Toast.makeText(activity, "Permission is not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ImageLoad() {
        if (folder_name.equals("All")) {
            list.addAll(new ContentLoader().getAllImages(activity, media));
        } else if (folder_name.equals(Constance.Key.VALUE_ALL_MEDIA)) {
//            list.addAll(new ContentLoader().getAllImages(activity, media));
        } else {
            list.addAll(new ContentLoader().getFolderItemContent(activity, folder_name, media));
        }

//        list.addAll(new ImagesLoader().getFolderItemContent(activity, folder_name));

        adapter.notifyDataSetChanged();

        Log.d("TAG_", "list size: " + list.size());

    }


}
