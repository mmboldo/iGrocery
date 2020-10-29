package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputEditText registerEmail;
    private TextInputEditText registerPass;
    private TextInputEditText registerPassConfirm;
    private AppCompatButton registerBtn;
    private AppCompatButton alreadyUserBtn;
    private AppCompatButton addAddressBtn;
    private ProgressBar progressBar;
    private TextInputLayout registerInputLayoutEmail;
    private TextInputLayout registerInputLayoutPass;
    private TextInputLayout registerInputLayoutPassConfirm;

    private static final String TAG = "FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get the firebase obj
        auth = FirebaseAuth.getInstance();

        //find the id
        registerEmail = (TextInputEditText) findViewById(R.id.registrationEmail);
        registerPass = (TextInputEditText) findViewById(R.id.registrationPassword);
        registerPassConfirm = (TextInputEditText) findViewById(R.id.registrationPassConfirm);
        registerBtn = (AppCompatButton) findViewById(R.id.registerBtn);
        alreadyUserBtn = (AppCompatButton) findViewById(R.id.alreadyUserBtn);
        addAddressBtn = (AppCompatButton) findViewById(R.id.addAddress);
        registerInputLayoutEmail = (TextInputLayout) findViewById(R.id.registerInputLayoutEmail);
        registerInputLayoutPass = (TextInputLayout) findViewById(R.id.registerInputLayoutPass);
        registerInputLayoutPassConfirm = (TextInputLayout) findViewById(R.id.registerInputLayoutPassConfirm);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Go to Login
        alreadyUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Register.this, Login.class);
                startActivity(myIntent);
            }
        });

        //send registration
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        String email = registerEmail.getText().toString().trim();
        String password = registerPass.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }
        if(!checkPasswordConfirm()) {
            return;
        }

        registerInputLayoutEmail.setErrorEnabled(false);
        registerInputLayoutPass.setErrorEnabled(false);
        registerInputLayoutPassConfirm.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        //create a user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "user creation: Complete");

                        progressBar.setVisibility(View.GONE);

                        if(!task.isSuccessful()) {
                            Log.d(TAG, "Creation failure." + task.getException());
                        } else {
                            startActivity(new Intent(Register.this, Login.class));
                            finish();
                        }
                    }
                });
        Toast.makeText(getApplicationContext(), "Thank you for register!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private boolean checkEmail() {
        String email = registerEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {
            registerInputLayoutEmail.setErrorEnabled(true);
            registerInputLayoutEmail.setError(getString(R.string.emailError));
            registerEmail.setError(getString(R.string.requiredMsg));
            requestFocus(registerEmail);
            return false;
        }
        registerInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {
        String password = registerPass.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {
            registerInputLayoutPass.setError(getString(R.string.passError));
            registerPass.setError(getString(R.string.requiredMsg));
            requestFocus(registerPass);
            return false;
        }
        registerInputLayoutPass.setErrorEnabled(false);
        return true;
    }

    private boolean checkPasswordConfirm() {
        String passwordConfirm = registerPassConfirm.getText().toString().trim();
        String password = registerPass.getText().toString().trim();
        if (!passwordConfirm.equals(password)) {
            registerInputLayoutPassConfirm.setError(getString(R.string.passErrorConfir));
            registerPassConfirm.setError(getString(R.string.requiredMsg));
            requestFocus(registerPassConfirm);
            return false;
        }
        registerInputLayoutPassConfirm.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password){
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



}