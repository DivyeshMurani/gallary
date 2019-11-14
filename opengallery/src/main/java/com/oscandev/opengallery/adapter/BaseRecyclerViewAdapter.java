package com.oscandev.opengallery.adapter;

import android.support.v7.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter {

    protected OnClickLis clcik;

    public void addOnClickLis(OnClickLis onClickLis) {
        this.clcik= onClickLis;
    }

    public interface OnClickLis{
        void addOnClick(int position, String folder_name);
    }

}
