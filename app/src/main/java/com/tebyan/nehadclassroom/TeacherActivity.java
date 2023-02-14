package com.tebyan.nehadclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.tebyan.nehadclassroom.data.TaskView;
import com.tebyan.nehadclassroom.databinding.ActivityTeacherBinding;

public class TeacherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Teacher Activity";
    public ActivityTeacherBinding binding;
    // region Views
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomAppBar bottomAppBar;
    FloatingActionButton sendFab;
    FragmentContainerView fragmentContainerView;
    BottomSheetBehavior bottomSheetBehavior;
    View bottomSheetView;
    MaterialButton addSubBtn;
    TextInputLayout subTaskTextInputLayout, sendMessageTextInputLayout;

    View includedSendTaskBottomSheetView;
    View includedSendMessageBottomSheetView;
    //endregion
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        // Initialize Views-----------------------------------------
        bottomAppBar = binding.bottomAppBar;
        drawerLayout = binding.teacherDrawerLayout;
        navigationView = binding.navigationView;
        sendFab = binding.sendFab;
        includedSendTaskBottomSheetView = binding.includeBottomSheet.getRoot();
        includedSendMessageBottomSheetView = binding.includeSendMessageBottomSheet.getRoot();
        fragmentContainerView = binding.fragmentContainerView;
        sendMessageTextInputLayout = binding.includeSendMessageBottomSheet.sendMessageTextInputLayout;
        bottomSheetView = binding.cardView;
        addSubBtn = binding.includeBottomSheet.addSub;
        subTaskTextInputLayout = binding.includeBottomSheet.subTaskTextInputLayout;
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        database = FirebaseDatabase.getInstance();
        // --------------------------------------------------------
        // Listeners -------------------------------------------


        sendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getCurrentFragment(R.id.fragmentContainerView);
                if (fragment instanceof TeacherSendTaskFragment) {
                    LoseFocusAndHideBottomSheetBehavior();
                    ((TeacherSendTaskFragment) fragment).ClearAll();

                } else if (fragment instanceof TeacherAnnouncementSendFragment) {
                    LoseFocusAndHideBottomSheetBehavior();
                    sendMessageTextInputLayout.getEditText().setText("");
                }
            }
        });
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                SetFabVisibilityThroughBottomSheetState(newState);
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    ((TextInputLayout) bottomSheetView.findViewById(R.id.sub_task_text_input_layout)).getEditText().setText("");
                    LoseFocusAndHideBottomSheetBehavior();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                sendFab.hide();

            }
        });
        addSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = subTaskTextInputLayout.getEditText().getText().toString();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                Fragment fragment = getCurrentFragment(R.id.fragmentContainerView);
                if (fragment instanceof TeacherSendTaskFragment && !text.isEmpty()) {
                    ((TeacherSendTaskFragment) fragment).addItem(text, TaskView.TaskViewType.TYPE_CHECKBOX);
                }

            }
        });

        // -------------------------------------------
        setSupportActionBar(bottomAppBar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, bottomAppBar, R.string.openNavigation, R.string.closeNavigation);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        SetupLayoutWithFragment(R.id.teacherDefaultFragment);
        bottomAppBar.getBehavior().
                setAdditionalHiddenOffsetY(bottomAppBar, 200);

    }

    void LoseFocusAndHideBottomSheetBehavior() {

        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(),
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
            );
            getCurrentFocus().clearFocus();

        }
    }

    // Sets the layouts according to which fragment currently is active
    void SetupLayoutWithFragment(int id) {

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        if (id == R.id.teacherDefaultFragment) {
            includedSendTaskBottomSheetView.setVisibility(View.INVISIBLE);
            includedSendMessageBottomSheetView.setVisibility(View.INVISIBLE);
            sendFab.hide();
        } else if (id == R.id.teacherSendTaskFragment) {
            includedSendTaskBottomSheetView.setVisibility(View.VISIBLE);
            includedSendMessageBottomSheetView.setVisibility(View.GONE);
            sendFab.show();
            SetFabVisibilityThroughBottomSheetState();
        } else if (id == R.id.teacherAnnouncementSendFragment) {

            includedSendTaskBottomSheetView.setVisibility(View.GONE);
            includedSendMessageBottomSheetView.setVisibility(View.VISIBLE);
            sendFab.show();
            SetFabVisibilityThroughBottomSheetState();
        } else if (id == R.id.aboutDeveloperFragment) {
            includedSendTaskBottomSheetView.setVisibility(View.GONE);
            includedSendMessageBottomSheetView.setVisibility(View.GONE);
            sendFab.hide();
        }
    }

    public Fragment getCurrentFragment(int id) {
        return getSupportFragmentManager().findFragmentById(id).getChildFragmentManager().getFragments().get(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about_developer_menu_item) {
            ChangeCurrentFragmentTo(R.id.aboutDeveloperFragment);
        } else if (id == R.id.default_fragment_menu_item) {
            ChangeCurrentFragmentTo(R.id.teacherDefaultFragment);
        } else if (id == R.id.send_announcement_menu_item) {
            ChangeCurrentFragmentTo(R.id.teacherAnnouncementSendFragment);
        } else if (id == R.id.send_task_menu_item) {
            ChangeCurrentFragmentTo(R.id.teacherSendTaskFragment);
        }
        else if(id==R.id.tasks_menu_item){
            ChangeCurrentFragmentTo(R.id.teacherTasksInformationFragment);
        }

        drawerLayout.close();
        return false;
    }

    void ChangeCurrentFragmentTo(int id) {

        NavHostFragment.findNavController(fragmentContainerView.getFragment()).navigate(id);
        SetupLayoutWithFragment(id);
    }

    void SetFabVisibilityThroughBottomSheetState(int newState) {
        if (newState == BottomSheetBehavior.STATE_SETTLING) {
            bottomAppBar.performHide();
            sendFab.hide();
        } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
            bottomAppBar.performHide();
            sendFab.hide();
        } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
            bottomAppBar.performShow();
            sendFab.show();
        }
    }

    void SetFabVisibilityThroughBottomSheetState() {

        SetFabVisibilityThroughBottomSheetState(bottomSheetBehavior.getState());
    }


}