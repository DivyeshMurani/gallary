package com.oscandev.opengallery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oscandev.gallery.R;

import java.io.File;
import java.util.List;

public class ItemShowAdapter extends BaseRecyclerViewAdapter {
    private List<String> list;

    public ItemShowAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_show, viewGroup, false);
        return new ItemShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        Log.d("TAG_", "File Path: " + list.get(i));

        ItemShowViewHolder holder = (ItemShowViewHolder) viewHolder;
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage(new File(list.get(i)).getAbsolutePath(),holder.img);


        Glide.with(holder.img).load(new File(list.get(i))).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        ItemShowViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
