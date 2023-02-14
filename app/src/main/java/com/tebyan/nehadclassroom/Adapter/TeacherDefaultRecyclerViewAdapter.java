package com.tebyan.nehadclassroom.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.tebyan.nehadclassroom.R;
import com.tebyan.nehadclassroom.TeacherActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherDefaultRecyclerViewAdapter extends RecyclerView.Adapter<TeacherDefaultRecyclerViewAdapter.TeacherDefaultViewHolder> {

    public interface AdapterOnClickListener{
        void onClick(View view,String name,String id);
    }
    final String TAG = TeacherDefaultRecyclerViewAdapter.class.toString();
    List<String> studentNames, studentIDs;
    AdapterOnClickListener listener;

    public TeacherDefaultRecyclerViewAdapter(String[] names, String[] ids,AdapterOnClickListener onClickListener) {
        studentIDs = new ArrayList<>(Arrays.asList(ids));
        studentNames = new ArrayList<>(Arrays.asList(names));
        this.listener=onClickListener;
    }

    @NonNull
    @Override
    public TeacherDefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_information_list_item, parent, false);


        return new TeacherDefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherDefaultViewHolder holder, int index) {
        holder.student_id.setText(studentIDs.get(index));
        holder.student_name.setText(studentNames.get(index));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClick(view, studentNames.get(holder.getBindingAdapterPosition()),studentIDs.get(holder.getBindingAdapterPosition()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentNames.size();
    }

    static class TeacherDefaultViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView student_name, student_id;

        public TeacherDefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            student_name = itemView.findViewById(R.id.student_name_text);
            student_id = itemView.findViewById(R.id.student_id_text);

        }
    }
}
