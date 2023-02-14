package com.tebyan.nehadclassroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.tebyan.nehadclassroom.Adapter.StudentTasksRecyclerViewAdapter;
import com.tebyan.nehadclassroom.Listeners.OnCheckChangedListener;
import com.tebyan.nehadclassroom.data.TaskView;
import com.tebyan.nehadclassroom.databinding.FragmentStudentTaskBinding;

import java.util.ArrayList;
import java.util.List;


public class StudentTaskFragment extends Fragment {

    FragmentStudentTaskBinding binding;
    RecyclerView studentTasksRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentStudentTaskBinding.inflate(inflater);
        studentTasksRecyclerView=binding.studentTaskRecyclerView;

        RecyclerView recyclerView=new RecyclerView(getContext());


        List<TaskView> schematics=new ArrayList<>();
        TaskView namaz=new TaskView(null,"Namaz", TaskView.TaskViewType.TYPE_TEXT);
        TaskView saturday=new TaskView(namaz,"Saturday", TaskView.TaskViewType.TYPE_TEXT);
        TaskView sunday=new TaskView(namaz,"Sunday", TaskView.TaskViewType.TYPE_TEXT);
        TaskView monday=new TaskView(namaz,"Monday", TaskView.TaskViewType.TYPE_TEXT);
        TaskView tuesday=new TaskView(namaz,"Tuesday", TaskView.TaskViewType.TYPE_TEXT);
        TaskView wednesday=new TaskView(namaz,"Wednesday", TaskView.TaskViewType.TYPE_TEXT);
        TaskView thursday=new TaskView(namaz,"Thursday", TaskView.TaskViewType.TYPE_TEXT);
        TaskView friday=new TaskView(namaz,"Friday", TaskView.TaskViewType.TYPE_TEXT);
        new TaskView(saturday,"Subh", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(saturday,"Chasht", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(saturday,"Digar", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(saturday,"Sham", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(saturday,"Khuftan", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(sunday,"Subh", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(sunday,"Chasht", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(sunday,"Digar", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(sunday,"Sham", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(sunday,"Khuftan", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(monday,"Subh", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(monday,"Chasht", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(monday,"Digar", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(monday,"Sham", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(monday,"Khuftan", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(tuesday,"Subh", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(tuesday,"Chasht", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(tuesday,"Digar", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(tuesday,"Sham", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(tuesday,"Khuftan", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(wednesday,"Subh", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(wednesday,"Chasht", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(wednesday,"Digar", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(wednesday,"Sham", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(wednesday,"Khuftan", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(thursday,"Subh", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(thursday,"Chasht", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(thursday,"Digar", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(thursday,"Sham", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(thursday,"Khuftan", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(friday,"Subh", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(friday,"Chasht", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(friday,"Digar", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(friday,"Sham", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(friday,"Khuftan", TaskView.TaskViewType.TYPE_CHECKBOX);
        schematics.add(namaz);
        TaskView zakat=new TaskView(null,"Zakat", TaskView.TaskViewType.TYPE_TEXT);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        new TaskView(zakat,"Saturday", TaskView.TaskViewType.TYPE_CHECKBOX);
        schematics.add(zakat);
        StudentTasksRecyclerViewAdapter adapter=new StudentTasksRecyclerViewAdapter(schematics.toArray(new TaskView[0]));
        adapter.setOnCheckChangedListener(new OnCheckChangedListener() {
            @Override
            public void onCheckChanged(CompoundButton button, boolean checked, TaskView taskView) {
                StringBuilder path= new StringBuilder();
                path.append(taskView.text+"/");

                TaskView parentView=taskView.parent;
                while(parentView!=null){
                    path.append(parentView.text+"/");
                    parentView=parentView.parent;
                }


            }
        });
        studentTasksRecyclerView.setAdapter(adapter);
        studentTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }
}
