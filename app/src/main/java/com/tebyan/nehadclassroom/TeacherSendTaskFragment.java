package com.tebyan.nehadclassroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.tebyan.nehadclassroom.Adapter.TeacherTaskParentChildRecyclerViewAdapter;
import com.tebyan.nehadclassroom.data.TaskView;
import com.tebyan.nehadclassroom.databinding.FragmentTeacherSendTaskBinding;


public class TeacherSendTaskFragment extends NavHostFragment {

    FragmentTeacherSendTaskBinding binding;
    BottomSheetBehavior bottomSheetBehavior;
    View bottomSheetView;
    MaterialButton addSubCategoriesBtn;
    RecyclerView subTaskRecyclerView;
    TeacherTaskParentChildRecyclerViewAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTeacherSendTaskBinding.inflate(inflater);
        View root=binding.getRoot();
        // View Initialization ---------------------------------

        bottomSheetView = getActivity().findViewById(R.id.card_view);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        addSubCategoriesBtn=binding.addSubCategoriesButton;
        subTaskRecyclerView= binding.subTaskRecyclerView;

        // -------------------------------------------------
        adapter=new TeacherTaskParentChildRecyclerViewAdapter(null);
        subTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subTaskRecyclerView.setAdapter(adapter);
        // add Listeners------------------------------------
        addSubCategoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
        });

        // ----------------------------------------------
        return root;
    }


    public void ClearAll(){
        adapter.clearAll();
    }
    public void addItem(String text, TaskView.TaskViewType viewType){

        adapter.addItem(new TaskView(adapter.getSelectedItem(),text,viewType));
    }


}