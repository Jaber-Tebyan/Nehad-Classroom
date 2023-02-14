package com.tebyan.nehadclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.Change;
import com.tebyan.nehadclassroom.data.InstructionData;
import com.tebyan.nehadclassroom.databinding.ActivityStudentBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "Student Activity";
    ActivityStudentBinding binding;
    List<InstructionData> instructionDataList;
    FirebaseAuth auth;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentContainerView fragmentContainerView;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        database = FirebaseDatabase.getInstance();
        toolbar = binding.toolbar;
        navigationView = binding.navigationDrawer;
        fragmentContainerView=binding.fragmentContainerView;
        drawerLayout = binding.drawerLayout;
        auth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavigation, R.string.closeNavigation);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.less_white));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Here I should use saving and loading

        instructionDataList = new ArrayList<>();
        instructionDataList.add(new InstructionData());
        auth.getCurrentUser();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.my_information){

        }
        else if(id==R.id.sign_out_menu_item){
            if (auth.getCurrentUser() == null) return false;
            auth.signOut();
            startActivity(new Intent(StudentActivity.this, LoginActivity.class));
            finish();
        }
        else if(id==R.id.teacher_info_menu_item){

        }
        else if(id==R.id.tasks_menu_item){
            ChangeFragment(R.id.studentTaskFragment);
        }
        else if(id==R.id.messages_menu_item){
        }
        else if(id==R.id.about_developer_menu_item){
            ChangeFragment(R.id.aboutDeveloperFragment2);
        }
        drawerLayout.close();
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    void ChangeFragment(int id){
        NavHostFragment.findNavController(fragmentContainerView.getFragment()).navigate(id);

    }
}