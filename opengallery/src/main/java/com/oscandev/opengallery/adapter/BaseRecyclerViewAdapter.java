package com.oscandev.opengallery.adapter;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter {

    OnClickLis clcik;

    public void addOnClickLis(OnClickLis onClickLis) {
        this.clcik= onClickLis;
    }

    public interface OnClickLis{
        void addOnClick(int position, String folder_name);
    }

}
