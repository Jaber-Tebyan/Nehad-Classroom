package com.tebyan.nehadclassroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tebyan.nehadclassroom.Adapter.TeacherDefaultRecyclerViewAdapter;
import com.tebyan.nehadclassroom.Adapter.TeacherTaskRecyclerViewAdapter;
import com.tebyan.nehadclassroom.data.TaskView;
import com.tebyan.nehadclassroom.databinding.FragmentTeacherTasksInformationBinding;


public class TeacherTasksInformationFragment extends Fragment {

    FragmentTeacherTasksInformationBinding binding;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTeacherTasksInformationBinding.inflate(inflater);
        recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TeacherTaskRecyclerViewAdapter(new TaskView[]{
                new TaskView("Jaber", TaskView.TaskViewType.TYPE_CHECKBOX),
                new TaskView("Jaber", TaskView.TaskViewType.TYPE_CHECKBOX),
                new TaskView("Jaber", TaskView.TaskViewType.TYPE_CHECKBOX),
                new TaskView("Jaber", TaskView.TaskViewType.TYPE_CHECKBOX),
                new TaskView("Jaber", TaskView.TaskViewType.TYPE_CHECKBOX),
                new TaskView("Jaber", TaskView.TaskViewType.TYPE_CHECKBOX)
        }));
        return binding.getRoot();
    }
}