package com.example.beckytang.finalproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beckytang.finalproject.R;

public class FragmentYourPhotos extends Fragment {

    public static final String TAG = "FragmentYourPhotos";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_your_photos, null);

        return rootView;
    }
}
