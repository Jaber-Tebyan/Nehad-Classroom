package com.tebyan.nehadclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.tebyan.nehadclassroom.data.User;
import com.tebyan.nehadclassroom.databinding.ActivitySplashBinding;
import com.tebyan.nehadclassroom.utility.FirebaseHelper;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());

        NehadApplication app=(NehadApplication) getApplication();
        app.addOnUserValidateCallback(new NehadApplication.OnUserValidateCallback() {
            @Override
            public void onUserValidateCallback(FirebaseUser user, User userData) {
                onUser(user,userData);
                app.removeOnUserValidateCallback(this);
            }
        });


    }

    void onUser(FirebaseUser user,User userData){
        if(user==null){
            startActivity(new Intent(this,LoginActivity.class));
        }
        else{
            if(userData.getUserType()== User.UserType.TEACHER){
                startActivity(new Intent(this,TeacherActivity.class));
            }else{
                startActivity(new Intent(this,StudentActivity.class));
            }
        }

        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
