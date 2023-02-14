package com.tebyan.nehadclassroom.data;

import android.graphics.Bitmap;

public class MsgViewItem {
    public MsgViewType viewType;
    public String text;
    public Bitmap image;

    public MsgViewItem(String text) {
        viewType = MsgViewType.TEXT_TYPE;
        this.text = text;
    }

    public MsgViewItem(Bitmap image) {
        viewType = MsgViewType.IMAGE_TYPE;
        this.image = image;
    }

    public MsgViewItem(MsgViewType viewType) {
        this.viewType = viewType;
    }
}
