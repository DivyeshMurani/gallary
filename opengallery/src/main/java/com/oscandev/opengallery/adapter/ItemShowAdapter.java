package com.oscandev.opengallery.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oscandev.opengallery.R;
import com.oscandev.opengallery.helper.GalleryContent;

import java.io.File;
import java.util.List;

public class ItemShowAdapter extends BaseRecyclerViewAdapter {
    private List<String> list;
    private List<GalleryContent> galleryContentList;

    public ItemShowAdapter(List<String> list) {
        this.list = list;
    }

    public ItemShowAdapter(List<GalleryContent> galleryContentList, String name) {
        this.galleryContentList = galleryContentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_show_one, viewGroup, false);
        return new ItemShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final ItemShowViewHolder holder = (ItemShowViewHolder) viewHolder;

        GalleryContent content = galleryContentList.get(i);

//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage(new File(list.get(i)).getAbsolutePath(),holder.img);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.person_placeholder);
//        requestOptions.error(R.drawable.ic_error);

        Glide.with(holder.img)
                .setDefaultRequestOptions(requestOptions)
                .load(new File(content.getImageFullPth()))
                .into(holder.img);
//        Glide.with(holder.img).load(new File(list.get(i))).into(holder.img);


        boolean isSelected = content.isSelectImage();
        if (isSelected) {
            holder.rl_select.setVisibility(View.VISIBLE);
        } else {
            holder.rl_select.setVisibility(View.GONE);
        }

        holder.root_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectImage != null) {
                    selectImage.selectImage(i, holder.rl_select);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryContentList.size();
    }

    public class ItemShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private RelativeLayout rl_select;
        private RelativeLayout root_one;

        ItemShowViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            root_one = itemView.findViewById(R.id.root_one);
            rl_select = itemView.findViewById(R.id.rl_select);

        }
    }

    private SelectImage selectImage;

    public void setSelectImage(SelectImage selectImage) {
        this.selectImage = selectImage;
    }

    public interface SelectImage {
        void selectImage(int position, RelativeLayout img_selected);
    }
}
