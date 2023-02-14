package com.tebyan.nehadclassroom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tebyan.nehadclassroom.Adapter.StudentInboxRecyclerViewAdapter;
import com.tebyan.nehadclassroom.data.MsgViewItem;
import com.tebyan.nehadclassroom.databinding.FragmentMessagesInboxBinding;


public class MessagesInboxFragment extends Fragment {

    public RecyclerView recyclerView;
    public FragmentMessagesInboxBinding binding;
    public FloatingActionButton fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentMessagesInboxBinding.inflate(inflater);
        View root = binding.getRoot();

        recyclerView = binding.recyclerView;
        fab = binding.backFab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(MessagesInboxFragment.this).navigate(R.id.action_inboxFragment_to_studentDefaultFragment);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(150, 150, 150, paint);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(150, 150, 75, paint);
        StudentInboxRecyclerViewAdapter adapter = new StudentInboxRecyclerViewAdapter(new MsgViewItem[]{new MsgViewItem("Hello"), new MsgViewItem("Hi"), new MsgViewItem(bitmap), new MsgViewItem(bitmap), new MsgViewItem(bitmap)});
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        return root;
    }
}