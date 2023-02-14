package com.tebyan.nehadclassroom.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskView {
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    static final String TAG=TaskView.class.getName();
    boolean checked =false;
    public TaskView parent;
    public List<TaskView> children;

    public TaskView(TaskView parent, String text, TaskViewType taskViewType) {
        this.parent = parent;
        this.text = text;
        this.taskViewType = taskViewType;
        children =new ArrayList<>();
        if(parent!=null)
            if(!parent.children.contains(this))

                parent.addChild(this);
    }
    public TaskView(String text,TaskViewType taskViewType){
        this.text=text;
        this.taskViewType=taskViewType;
        children=new ArrayList<>();

    }


    public String text;
    public TaskViewType taskViewType;

    public void addChild(TaskView taskView){
        children.add(taskView);
        Log.i(TAG, "addChild: "+children.size());
    }
    public void removeChild(TaskView taskView){
        children.remove(taskView);
    }

    public void removeChild(int i){
        children.remove(i);
    }
    public void removeChildren(int startIndex,int endIndex){
        for (int i = startIndex; i < endIndex; i++) {
            removeChild(i);
        }
    }
    public void removeChildren(){
        TaskView[] taskViews=this.runThroughChildren().toArray(new TaskView[0]);
        for (int i = taskViews.length-1; i>=0; i--) {
            TaskView taskView=taskViews[i];
            taskView.removeFromParent();
        }
    }
    public void removeFromParent(){
        if(parent==null)return;
        parent.removeChild(this);
    }
    public void removeSelf(){

        removeChildren();
        removeFromParent();
    }


    public int getAllChildrenCount(){
        Log.i(TAG, "getAllChildrenCount: "+runThroughChildren().size());
        return runThroughChildren().size();
    }
    public List<TaskView> getChildren(){
        return children;
    }

    public List<TaskView> runThroughChildren(){
        return walkThroughTasks(children.toArray(new TaskView[0]));
    }

    public List<TaskView> runThroughChildrenAndSelf(){

        return walkThroughTasks(new TaskView[]{this});
    }

    public static void walkThroughTasks(TaskView[] taskViews, List<TaskView> list) {
        if (taskViews.length == 0) return;
        for (TaskView view : taskViews) {
            list.add(view);
            if (view.children.size() != 0)
                walkThroughTasks(view.children.toArray(new TaskView[0]), list);
        }

    }

    public static List<TaskView> walkThroughTasks(TaskView[] taskViews) {
        List<TaskView> list = new ArrayList<>();
        walkThroughTasks(taskViews, list);
        return list;
    }


    public enum TaskViewType {
        TYPE_TEXT, TYPE_CHECKBOX;

        public static TaskViewType from(int i) {
            if (i == 0) return TYPE_TEXT;
            else return TYPE_CHECKBOX;

        }

        public static int toInt(TaskViewType taskViewType) {
            if (taskViewType == TYPE_TEXT) return 0;
            else
                return 1;

        }
        public int toInt(){
            return TaskViewType.toInt(this);
        }
    }
}

