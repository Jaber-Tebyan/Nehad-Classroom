package com.tebyan.nehadclassroom.Adapter;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.tebyan.nehadclassroom.data.CommandType;
import com.tebyan.nehadclassroom.Listeners.OnCommandSuccessFailListener;
import com.tebyan.nehadclassroom.R;
import com.tebyan.nehadclassroom.data.TaskView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherTaskParentChildRecyclerViewAdapter extends RecyclerView.Adapter<TeacherTaskParentChildRecyclerViewAdapter.CategorySubTaskRecyclerViewHolder> {

    private final String TAG = TeacherTaskParentChildRecyclerViewAdapter.class.toString();


    public TeacherTaskParentChildRecyclerViewAdapter(TaskView[] taskViewList) {
        if(taskViewList==null){
            this.taskViewList=new ArrayList<>();
        }
        else{
            this.taskViewList = new ArrayList<>(Arrays.asList(taskViewList));
        }
    }

    public List<TaskView> getTaskViewList() {
        return taskViewList;
    }

    public void setTaskViewList(List<TaskView> taskViewList) {
        this.taskViewList = taskViewList;
    }

    public TaskView getRootView() {
        return rootView;
    }

    public void setRootView(TaskView rootView) {
        this.rootView = rootView;
    }

    public OnCommandSuccessFailListener getOnCommandSuccessFailListener() {
        return onCommandSuccessFailListener;
    }

    public void setOnCommandSuccessFailListener(OnCommandSuccessFailListener onCommandSuccessFailListener) {
        this.onCommandSuccessFailListener = onCommandSuccessFailListener;
    }

    List<TaskView> taskViewList;
    TaskView rootView;
    OnCommandSuccessFailListener onCommandSuccessFailListener;
    TaskView selectedItem;

    public TaskView getSelectedItem(){
        return selectedItem;
    }
    public void addItem(TaskView taskView){
        if(taskView.parent==null){
            if(rootView==null){
                taskViewList.add(taskView);
                rootView=taskView;
                if(onCommandSuccessFailListener !=null){
                    onCommandSuccessFailListener.onSuccess(taskView, CommandType.ADD);
                }
                notifyItemInserted(0);
            }else{
                if(onCommandSuccessFailListener !=null){
                    onCommandSuccessFailListener.onFailed(taskView,new Exception("Already have a root view"),CommandType.ADD);
                }
            }

        }
        else{
            if(taskViewList.contains(taskView.parent)){
                if(onCommandSuccessFailListener!=null)onCommandSuccessFailListener.onSuccess(taskView,CommandType.ADD);
                int index= taskViewList.indexOf(taskView.parent);

                Log.i(TAG, "addItem: "+taskView.parent.children);

                taskViewList.add(index+taskView.parent.getAllChildrenCount(),taskView);
                notifyItemInserted(index+taskView.parent.getAllChildrenCount());

            }
            else{
                if(onCommandSuccessFailListener !=null){
                    onCommandSuccessFailListener.onFailed(taskView,new Exception("Parent of the item does not exist"),CommandType.ADD);
                }
            }
        }

    }
    public void removeItem(int pos){

        TaskView parent=taskViewList.get(pos);
        if(onCommandSuccessFailListener!=null)onCommandSuccessFailListener.onSuccess(parent,CommandType.REMOVE);
        Log.i(TAG, "removeItem: "+pos+"   "+(pos+parent.getAllChildrenCount()+1));
        if(parent==rootView)rootView=null;
        if(selectedItem==parent)selectedItem=null;
        int startPos=pos;
        int endPos=pos+parent.getAllChildrenCount()+1;
        parent.removeSelf();
        taskViewList.subList(startPos,endPos).clear();
        notifyItemRangeRemoved(startPos,endPos);
    }
    public void removeItem(TaskView taskView){
        removeItem(taskViewList.indexOf(taskView));
    }
    public void clearAll(){
        if(taskViewList.size()==0)return;
        removeItem(0);
    }
    @NonNull
    @Override
    public CategorySubTaskRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategorySubTaskRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySubTaskRecyclerViewHolder holder, int position) {
        final TaskView taskView = taskViewList.get(position);


        holder.SetText(taskView.text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                selectedItem=taskView;
                holder.itemView.setSelected(true);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(holder.getBindingAdapterPosition());
            }
        });
        TaskView parentView = taskView.parent;
        int margin = 0;
        while (parentView != null) {
            margin += 20;
            parentView = parentView.parent;
        }
        ((ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams()).setMarginStart((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, holder.itemView.getContext().getResources().getDisplayMetrics()));
    }

    @Override
    public int getItemCount() {
        return taskViewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return taskViewList.get(position).taskViewType.toInt();
    }

    static class CategorySubTaskRecyclerViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textView;
        ImageButton button;

        public CategorySubTaskRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.sub_task_text_view);
            button = itemView.findViewById(R.id.sub_task_image_btn);

        }

        void SetText(String text) {
            textView.setText(text);
        }
    }


}








