package com.example.maktabhw13task.controller.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.maktabhw13task.R;
import com.example.maktabhw13task.controller.activity.TaskViewPagerActivity;
import com.example.maktabhw13task.model.UserModel;
import com.example.maktabhw13task.repository.UserRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

public class SignInFragment extends Fragment {

    private UserRepository mUserRepository;
    private Button mButtonSignIn, mButtonSignUp;
    private LottieAnimationView mLottieAnimationView;

    private TextInputEditText mEditTextUsername, mEditTextPassword;
    private TextInputLayout mTextInputLayoutUsername, mTextInputLayoutPassword;
    private CheckBox mCheckBoxAdmin;

    public static SignInFragment newInstance() {

        Bundle args = new Bundle();

        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        findViews(view);
        setListeners();
        return view;
    }

    private void findViews(View view) {
        mButtonSignIn = view.findViewById(R.id.buttonSignIn);
        mButtonSignUp = view.findViewById(R.id.buttonSignUp);
        mEditTextUsername = view.findViewById(R.id.editTextUsername);
        mEditTextPassword = view.findViewById(R.id.editTextPassword);
        mCheckBoxAdmin = view.findViewById(R.id.checkBoxAdmin);
        mTextInputLayoutUsername = view.findViewById(R.id.textInputLayoutUsername);
        mTextInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        mLottieAnimationView = view.findViewById(R.id.lottie_done);

        setCheckBoxAdmin();
    }

    private void setListeners(){

        mTextInputLayoutUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0)
                    mTextInputLayoutUsername.setErrorEnabled(false);
                else
                    mTextInputLayoutUsername.setErrorEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mTextInputLayoutPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0)
                    mTextInputLayoutPassword.setErrorEnabled(false);
                else
                    mTextInputLayoutPassword.setErrorEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!textValidation() || !signInValidation())
                    return;

                startActivity(TaskViewPagerActivity.newIntent(getActivity()));
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!textValidation())
                    return;

                if (usernameValidation()) {
                    Toast.makeText(getActivity(), "There is an user with this 'Username'.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mUserRepository.getUserList().size() == 0){
                    if (!mCheckBoxAdmin.isChecked()){
                        Toast.makeText(getActivity(), "First of all 'Admin user' should be set.\nPlease check the admin checkbox to sign up. ", Toast.LENGTH_LONG).show();
                    }else
                    {
                        mUserRepository.addUser(new UserModel(mEditTextUsername.getText().toString(),mEditTextPassword.getText().toString()));
                        mLottieAnimationView.playAnimation();
                        setCheckBoxAdmin();
                    }
                    return;
                }

                mUserRepository.addUser(new UserModel(mEditTextUsername.getText().toString(),mEditTextPassword.getText().toString()));
                setCheckBoxAdmin();
                mLottieAnimationView.playAnimation();
            }
        });

    }

    private boolean textValidation() {

        if (mEditTextUsername.getText().toString().length() == 0 && mEditTextPassword.getText().toString().length() == 0) {
            mTextInputLayoutUsername.setError("Username should be fill");
            mTextInputLayoutPassword.setError("Password should be fill");
            return false;
        } else if (mEditTextUsername.getText().toString().length() == 0) {
            mTextInputLayoutUsername.setError("Username should be fill");
            return false;
        } else if (mEditTextPassword.getText().toString().length() == 0) {
            mTextInputLayoutPassword.setError("Password should be fill");
            return false;
        }
        return true;
    }

    private boolean signInValidation() {

        for (int i = 0; i < mUserRepository.getUserList().size(); i++) {
            if (mUserRepository.getUserList().get(i).getUsername().equals(mEditTextUsername.getText().toString())){
                for (int j = 0; j < mUserRepository.getUserList().size(); j++) {
                    if (mUserRepository.getUserList().get(j).getPassword().equals(mEditTextPassword.getText().toString())) {
                        mUserRepository.setCurrentUserIndex(i);
                        return true;
                    }
                }
                Toast.makeText(getActivity(), "Oops! Password is wrong.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(getActivity(), "There is no such user", Toast.LENGTH_SHORT).show();
        return false;

    }

    private boolean usernameValidation(){
        for (int i = 0; i < mUserRepository.getUserList().size(); i++) {
            if (mUserRepository.getUserList().get(i).getUsername().equals(mEditTextUsername.getText().toString())){
                return true;
            }
        }
        return false;
    }

    private void setCheckBoxAdmin(){
        if (mUserRepository.getUserList().size() > 0) {
            mCheckBoxAdmin.setChecked(false);
            mCheckBoxAdmin.setEnabled(false);
        }
    }

}