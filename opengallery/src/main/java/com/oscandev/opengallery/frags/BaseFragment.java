package com.oscandev.opengallery.frags;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oscandev.opengallery.MainContentActivity;

public class BaseFragment extends Fragment {

    MainContentActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainContentActivity) getActivity();

    }
}
