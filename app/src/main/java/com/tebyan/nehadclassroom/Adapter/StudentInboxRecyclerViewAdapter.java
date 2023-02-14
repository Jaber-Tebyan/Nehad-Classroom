package com.tebyan.nehadclassroom.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tebyan.nehadclassroom.R;
import com.tebyan.nehadclassroom.data.MsgViewItem;
import com.tebyan.nehadclassroom.data.MsgViewType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentInboxRecyclerViewAdapter extends RecyclerView.Adapter {

    List<MsgViewItem> msgViewItems;

    public StudentInboxRecyclerViewAdapter(MsgViewItem[] msgViewItems) {
        this.msgViewItems = new ArrayList<>(Arrays.asList(msgViewItems));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        switch (MsgViewType.from(viewType)) {
            case TEXT_TYPE:
                return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_text_format_list_item, parent, false));
            case IMAGE_TYPE:
                return new ImageViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_image_format_list_item, parent, false)
                );
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return MsgViewType.to(msgViewItems.get(position).viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MsgViewType type = msgViewItems.get(position).viewType;
        switch (type) {
            case IMAGE_TYPE:
                ((ImageViewHolder) holder).getImageView().setImageBitmap(msgViewItems.get(position).image);
                break;
            case TEXT_TYPE:
                ((TextViewHolder) holder).getTextView().setText(msgViewItems.get(position).text);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return msgViewItems.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView getImageView() {
            return imageView;
        }

        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.msg_image);
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.msg_text);
        }

        public TextView getTextView() {
            return textView;
        }

    }

}

