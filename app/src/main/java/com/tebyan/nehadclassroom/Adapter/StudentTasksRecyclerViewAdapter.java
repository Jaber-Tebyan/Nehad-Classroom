package com.tebyan.nehadclassroom.Adapter;


import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.tebyan.nehadclassroom.Listeners.OnCheckChangedListener;
import com.tebyan.nehadclassroom.R;
import com.tebyan.nehadclassroom.data.TaskView;

import java.util.ArrayList;
import java.util.List;

public class StudentTasksRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final static String TAG = StudentTasksRecyclerViewAdapter.class.getName();
    List<TaskView> taskViewList;




    public StudentTasksRecyclerViewAdapter(TaskView[] taskViews) {
        super();
        taskViewList = TaskView.walkThroughTasks(taskViews);


    }

    public StudentTasksRecyclerViewAdapter(List<TaskView> taskViewList, OnCheckChangedListener onCheckChangedListener) {
        this.taskViewList = taskViewList;
        this.onCheckChangedListener = onCheckChangedListener;
    }

    public OnCheckChangedListener getOnCheckChangedListener() {
        return onCheckChangedListener;
    }

    public void setOnCheckChangedListener(OnCheckChangedListener onCheckChangedListener) {
        this.onCheckChangedListener = onCheckChangedListener;
    }

    OnCheckChangedListener onCheckChangedListener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TaskView.TaskViewType type = TaskView.TaskViewType.from(viewType);
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (type == TaskView.TaskViewType.TYPE_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_layout, parent, false);
            viewHolder = new StudentTaskTextParent(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_box_layout, parent, false);
            viewHolder = new StudentTaskCheckBoxChild(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TaskView taskView = taskViewList.get(position);

        TaskView.TaskViewType viewType = taskView.taskViewType;
        if (viewType == TaskView.TaskViewType.TYPE_TEXT) {
            StudentTaskTextParent studentTaskTextParent = (StudentTaskTextParent) holder;
            studentTaskTextParent.textView.setText(taskView.text);
        } else {

            StudentTaskCheckBoxChild studentTaskCheckBoxChild = (StudentTaskCheckBoxChild) holder;
            studentTaskCheckBoxChild.checkBox.setOnCheckedChangeListener(null);
            studentTaskCheckBoxChild.checkBox.setChecked(taskView.isChecked());
            studentTaskCheckBoxChild.checkBox.setText(taskView.text);


            studentTaskCheckBoxChild.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    taskView.setChecked(b);
                    if (onCheckChangedListener != null) {
                        onCheckChangedListener.onCheckChanged(compoundButton, b, taskView);
                    }
                }
            });

        }

        TaskView parentView = taskView.parent;
        int margin = 0;
        while (parentView != null) {
            margin += 20;
            parentView = parentView.parent;
        }
        ((ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams()).setMarginStart((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, holder.itemView.getContext().getResources().getDisplayMetrics()));
    }


    @Override
    public int getItemViewType(int position) {
        return taskViewList.get(position).taskViewType.toInt();
    }

    @Override
    public int getItemCount() {
        return taskViewList.size();
    }

    static class StudentTaskCheckBoxChild extends RecyclerView.ViewHolder {

        MaterialCheckBox checkBox;


        public StudentTaskCheckBoxChild(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.ugh_checkbox);

        }
    }

    static class StudentTaskTextParent extends RecyclerView.ViewHolder {

        MaterialTextView textView;

        public StudentTaskTextParent(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}

