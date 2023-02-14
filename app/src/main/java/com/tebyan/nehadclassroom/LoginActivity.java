package com.tebyan.nehadclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.tebyan.nehadclassroom.Adapter.MFragmentPagerAdapter;
import com.tebyan.nehadclassroom.data.AccountData;
import com.tebyan.nehadclassroom.data.User;
import com.tebyan.nehadclassroom.databinding.ActivityLoginBinding;
import com.tebyan.nehadclassroom.utility.ActivityManager;
import com.tebyan.nehadclassroom.utility.FirebaseHelper;


import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    //region Firebase
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    //endregion

    //region View Declaration
    ActivityLoginBinding binding;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    MFragmentPagerAdapter mFragmentPagerAdapter;
    ExtendedFloatingActionButton googleEFab;
    ExtendedFloatingActionButton deleteFab;
    //endregion

    boolean reloadSuccessful = false;


    // Firebase Retrieved Objects


    ActivityState state=ActivityState.NONE;


    boolean networkConnected;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        //region Variables Init
        //region View Init
        viewPager2 = binding.viewPager2;
        tabLayout = binding.tabLayout2;
        googleEFab = binding.floatingActionButton;
        deleteFab = binding.deleteFab;
        //endregion
        //region Firebase Init
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        //endregion
        mFragmentPagerAdapter = new MFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        //endregion
        //region Buttons Listeners Setup
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteFabClick(view);
            }
        });
        googleEFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoogleFabClick(view);
            }
        });
        //endregion
        //region PagerAdapter Setup
        mFragmentPagerAdapter.SetFragments(new Fragment[]{new SignupFragment(), new LoginFragment()}, new String[]{"Sign up", "Login"});
        viewPager2.setAdapter(mFragmentPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mFragmentPagerAdapter.names.get(position));
            }
        }).attach();
        //endregion




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            reload();
        }
    }

    void onGoogleFabClick(View view) {

        if ((reloadSuccessful) && auth.getCurrentUser() != null) {
            User data=((NehadApplication)getApplication()).userData;
            ActivityManager.startActivityDelayed(LoginActivity.this, data.getUserType() == User.UserType.STUDENT ? StudentActivity.class : TeacherActivity.class, 0);


            return;
        }
        AccountData accountData;
        // Signup Fragment
        switch (viewPager2.getCurrentItem()) {
            case 0:
                if (state==ActivityState.SIGNING_UP) return;

                SignupFragment _temp = (SignupFragment) mFragmentPagerAdapter.GetFragment(0);
                accountData = _temp.accountData;
                boolean valid = accountData.validate();
                if (!valid) {
                    Toast.makeText(this, "Check your email and password please", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isTeacher = accountData.getEmail().toLowerCase(Locale.ROOT).startsWith("teacher123:".toLowerCase(Locale.ROOT));

                AttemptSignupAccount(isTeacher, isTeacher ? accountData.getEmail().split(":")[1] : accountData.getEmail(), accountData.getPass(), accountData.getTeacherID());


                break;
            case 1:
                LoginFragment _temp1 = (LoginFragment) mFragmentPagerAdapter.GetFragment(1);
                accountData = _temp1.accountData;
                boolean valid2 = accountData.validate();
                if (!valid2) return;
                SignIn(accountData.getEmail(), accountData.getPass());

                break;
            default:
                accountData = new AccountData();
        }

    }
    void onDeleteFabClick(View view) {
        if (viewPager2.getCurrentItem() == 1) {
            LoginFragment loginFragment = (LoginFragment) mFragmentPagerAdapter.GetFragment(1);
            AccountData accountData = loginFragment.accountData;

            if (accountData.getEmail().equals("-deleteAll")) {

                database.getReference().removeValue().addOnCompleteListener(task -> {
                    Toast.makeText(LoginActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                });

            } else {
                boolean valid = accountData.validate();
                if (!valid) return;
                deleteAccount(accountData.getEmail(), accountData.getPass());
            }
            showProgressBar(true);
        }
    }
    void SetToLoginRightAway(boolean value) {
        if (value) {
            googleEFab.setText(R.string.login_rightaway);
            reloadSuccessful = true;
        } else {
            googleEFab.setText(R.string.login);
            reloadSuccessful = false;

        }
    }
    void setToSignup(boolean value) {
        if(value){
            state=ActivityState.SIGNING_UP;
        }
        else{
            state=ActivityState.NONE;
        }
        showProgressBar(value);
    }
    void AttemptSignupAccount(boolean isTeacher, String email, String password, int teacherID) {


        if(((NehadApplication)getApplication()).getTeacherIDs()==null){
            Toast.makeText(this, "Haven't Initialized Yet", Toast.LENGTH_SHORT).show();
            return;
        }
        setToSignup(true);
        FirebaseHelper.SignupAccount(this, email, password, teacherID, isTeacher, new FirebaseHelper.OnTransactionResult() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                setToSignup(false);
                SetToLoginRightAway(true);
            }

            @Override
            public void onFail(@NonNull Exception exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                SetToLoginRightAway(false);
                setToSignup(false);
            }
        }, new FirebaseHelper.OnDataExists() {
            @Override
            public boolean onTeacherExists(int teacherID) {
                List<Integer> teacherIDs=((NehadApplication)getApplication()).getTeacherIDs();
                if(teacherIDs!=null)
                    return teacherIDs.contains(teacherID);
                return false;
            }
        });

    }


    void deleteAccount(String email, String pass) {
        FirebaseHelper.deleteUser(this, database,auth,email, pass, new FirebaseHelper.OnTransactionResult() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                SetToLoginRightAway(false);
            }

            @Override
            public void onFail(@NonNull Exception exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    void SignIn(String email, String password) {
        showProgressBar(true);
        FirebaseHelper.SignInAccount(this,auth,email, password,  new FirebaseHelper.OnTransactionResult() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                SetToLoginRightAway(true);
                showProgressBar(false);
            }
            @Override
            public void onFail(@NonNull Exception exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                SetToLoginRightAway(false);
                showProgressBar(false);
            }
        });

    }

    void reload() {
        if(true)return;
        showProgressBar(true);
        Objects.requireNonNull(auth.getCurrentUser()).reload().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Reload Successful, With email: " + auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                SetToLoginRightAway(true);
            } else {
                Toast.makeText(LoginActivity.this, "Reload Failed", Toast.LENGTH_SHORT).show();
                showProgressBar(false);

                SetToLoginRightAway(false);
            }
        });
    }

    void showProgressBar(boolean value) {
        if (value) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
    enum ActivityState{
        NONE,SIGNING_UP,SIGNING_IN
    }

}











