package com.tebyan.nehadclassroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tebyan.nehadclassroom.databinding.FragmentAboutDeveloperBinding;


public class AboutDeveloperFragment extends Fragment {

    FragmentAboutDeveloperBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutDeveloperBinding.inflate(inflater);
        // View Initialization------------------------------------
        View root = binding.getRoot();
        // ----------------------------------------------------


        return root;
    }
}