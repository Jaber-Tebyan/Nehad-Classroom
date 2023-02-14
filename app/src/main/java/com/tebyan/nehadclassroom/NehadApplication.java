package com.tebyan.nehadclassroom;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tebyan.nehadclassroom.data.User;
import com.tebyan.nehadclassroom.utility.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NehadApplication extends Application {
    FirebaseDatabase database;
    FirebaseAuth auth;



    List<Integer> teacherIDs;
    public User userData;
    public List<User> students;
    public List<OnUserValidateCallback> onUserValidateCallbacks=new ArrayList<>();
    //region Get Set

    public List<Integer> getTeacherIDs() {
        return teacherIDs;
    }
    public void setTeacherIDs(List<Integer> teacherIDs) {
        this.teacherIDs = teacherIDs;
    }
    public void addOnUserValidateCallback(OnUserValidateCallback on){
        onUserValidateCallbacks.add(on);
    }
    public void removeOnUserValidateCallback(OnUserValidateCallback on){
        onUserValidateCallbacks.remove(on);
    }
    //endregion

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    runCallbacks(null,null);
                }
                else{

                }
            }
        });
        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(teacherIDs==null)teacherIDs=new ArrayList<>();
                teacherIDs.clear();
                for (DataSnapshot child :
                        snapshot.getChildren()) {

                    int id=Integer.parseUnsignedInt(child.getKey());
                    teacherIDs.add(id);
                }
                Toast.makeText(NehadApplication.this, teacherIDs.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void runCallbacks(FirebaseUser user, User userData){
        for (OnUserValidateCallback callback :
                onUserValidateCallbacks) {
            if(callback!=null)
                callback.onUserValidateCallback(user,userData);
        }
    }
    interface OnUserValidateCallback{
        void onUserValidateCallback(FirebaseUser user,User userData);
    }

}

