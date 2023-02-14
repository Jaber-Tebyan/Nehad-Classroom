package com.tebyan.nehadclassroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tebyan.nehadclassroom.databinding.FragmentStudentDefaultBinding;


public class StudentDefaultFragment extends Fragment {


    FragmentStudentDefaultBinding binding;

    private final String TAG = StudentDefaultFragment.class.toString();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStudentDefaultBinding.inflate(inflater);
        View view = binding.getRoot();

        return view;
    }



}