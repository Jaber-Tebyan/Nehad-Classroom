package com.tebyan.nehadclassroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.tebyan.nehadclassroom.data.AccountData;
import com.tebyan.nehadclassroom.Listeners.AccountValidate;
import com.tebyan.nehadclassroom.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    public AccountData accountData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        emailTextInputLayout = binding.emailTextInputLayout;
        passwordTextInputLayout = binding.passwordTextInputLayout;
        emailTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                accountData.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                accountData.setPass(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        accountData = new AccountData();
        AccountValidate accountValidate = new AccountValidate() {
            @Override
            public boolean onValidate(AccountData data) {
                return validate(data);
            }
        };
        accountData.setAccountValidate(accountValidate);
        return binding.getRoot();
    }

    public boolean validate(AccountData data) {
        boolean valid = true;
        if (data.getEmail().isEmpty()) {
            emailTextInputLayout.setError("Required");
            valid = false;

        } else if (!data.CheckEmailForPattern()) {
            emailTextInputLayout.setError("Pattern Mismatch");
            valid = false;
        } else {
            emailTextInputLayout.setError(null);
        }
        if (data.getPass().isEmpty()) {
            passwordTextInputLayout.setError("Required");
            valid = false;
        } else if (!data.CheckPasswordForPattern()) {
            passwordTextInputLayout.setError("Can not be less than 6 characters");
            valid = false;
        } else {
            passwordTextInputLayout.setError(null);
        }
        return valid;
    }

}