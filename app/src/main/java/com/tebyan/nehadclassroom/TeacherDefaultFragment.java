package com.tebyan.nehadclassroom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.tebyan.nehadclassroom.Adapter.TeacherDefaultRecyclerViewAdapter;
import com.tebyan.nehadclassroom.data.User;
import com.tebyan.nehadclassroom.databinding.FragmentTeacherDefaultBinding;
import com.tebyan.nehadclassroom.utility.FirebaseHelper;

import java.util.List;


public class TeacherDefaultFragment extends NavHostFragment {

    FragmentTeacherDefaultBinding binding;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTeacherDefaultBinding.inflate(inflater);
        // View Initialization-------------------------------
        recyclerView = binding.teacherDefaultRecyclerView;
        View root = binding.getRoot();
        swipeRefreshLayout = binding.swipeRefreshLayout;
        database = FirebaseDatabase.getInstance();
        //----------------------------------------------


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // RecyclerView Initialization-------------

        recyclerView.setAdapter(new TeacherDefaultRecyclerViewAdapter(new String[0], new String[0], new TeacherDefaultRecyclerViewAdapter.AdapterOnClickListener() {
            @Override
            public void onClick(View view, String name, String id) {
                Intent intent = new Intent(getContext(), StudentInformationActivity.class);

                getContext().startActivity(intent);

            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //---------------------------------------
        return root;
    }
}