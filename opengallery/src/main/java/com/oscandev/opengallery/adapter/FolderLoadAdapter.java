package com.oscandev.opengallery.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oscandev.opengallery.R;

import java.util.List;

public class FolderLoadAdapter extends BaseRecyclerViewAdapter {

    private List<String> list;

    public FolderLoadAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_folder, viewGroup, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        FolderViewHolder holder = (FolderViewHolder) viewHolder;
        holder.txt_folder_name.setText(list.get(i));

        if (list.size() - 1 == i) {
            holder.view_line.setVisibility(View.GONE);
        }else {
            holder.view_line.setVisibility(View.VISIBLE);
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clcik != null) {
                    clcik.addOnClick(i, list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_folder_name;
        private LinearLayout root;
        private View view_line;

        /**
         * @param itemView
         */

        FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_folder_name = itemView.findViewById(R.id.txt_folder_name);
            root = itemView.findViewById(R.id.root);
            view_line = itemView.findViewById(R.id.view_line);
        }
    }
}
