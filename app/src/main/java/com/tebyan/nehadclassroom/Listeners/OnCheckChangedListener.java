package com.tebyan.nehadclassroom.Listeners;

import android.widget.CompoundButton;

import com.tebyan.nehadclassroom.data.TaskView;

public interface OnCheckChangedListener {
    void onCheckChanged(CompoundButton button, boolean checked, TaskView taskView);
}
