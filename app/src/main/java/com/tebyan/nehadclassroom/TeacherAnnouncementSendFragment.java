package com.tebyan.nehadclassroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.tebyan.nehadclassroom.databinding.FragmentTeacherAnnouncementSendBinding;


public class TeacherAnnouncementSendFragment extends Fragment {

    FragmentTeacherAnnouncementSendBinding binding;
    Button sendImageBtn, sendTextBtn;
    View bottomSheetView;
    BottomSheetBehavior bottomSheetBehavior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTeacherAnnouncementSendBinding.inflate(inflater);
        // View Initialization--------------------------------
        View root = binding.getRoot();
        sendImageBtn = binding.sendImageBtn;
        sendTextBtn = binding.sendTextBtn;
        bottomSheetView = getActivity().findViewById(R.id.card_view);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        // --------------------------------------
        sendTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        sendImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Sending Image", Toast.LENGTH_SHORT).show();
            }
        });
        return root;

    }

}