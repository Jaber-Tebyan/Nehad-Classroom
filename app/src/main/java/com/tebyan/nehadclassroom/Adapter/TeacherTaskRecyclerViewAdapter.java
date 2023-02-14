package com.tebyan.nehadclassroom.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.tebyan.nehadclassroom.R;
import com.tebyan.nehadclassroom.data.TaskView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherTaskRecyclerViewAdapter extends RecyclerView.Adapter<TeacherTaskRecyclerViewAdapter.TeacherTaskViewHolder> {


    List<TaskView> taskViewList;


    public TeacherTaskRecyclerViewAdapter(TaskView[] taskViews){
        taskViewList=new ArrayList<>(Arrays.asList(taskViews));
    }
    @NonNull
    @Override
    public TeacherTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycler_view_item, parent, false);
        return new TeacherTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherTaskViewHolder holder, int position) {
        TaskView taskView = taskViewList.get(position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(holder.getBindingAdapterPosition());
            }
        });

    }

    public void removeItem(TaskView taskView) {
        removeItem(taskViewList.indexOf(taskView));
    }

    public void removeItem(int i) {
        taskViewList.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public int getItemCount() {
        return taskViewList.size();
    }

    static class TeacherTaskViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView textView;
        ImageButton button;

        public TeacherTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.sub_task_text_view);
            button = itemView.findViewById(R.id.sub_task_image_btn);

        }

        void SetText(String text) {
            textView.setText(text);
        }
    }
}

