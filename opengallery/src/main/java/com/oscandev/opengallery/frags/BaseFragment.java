package com.oscandev.opengallery.frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.oscandev.opengallery.MainContentActivity;

public class BaseFragment extends Fragment {

    MainContentActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainContentActivity) getActivity();

    }
}
