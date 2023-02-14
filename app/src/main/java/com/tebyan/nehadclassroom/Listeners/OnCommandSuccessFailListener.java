package com.tebyan.nehadclassroom.Listeners;

import com.tebyan.nehadclassroom.data.CommandType;
import com.tebyan.nehadclassroom.data.TaskView;

public interface OnCommandSuccessFailListener {
    void onSuccess(TaskView taskView, CommandType commandType);

    void onFailed(TaskView taskView, Exception exception,CommandType commandType);
}
