package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private AppCompatButton loginBtn, newUserBtn, goToCalendar;
    private FirebaseAuth auth;
    private TextInputEditText emailInput, passInput;
    private TextInputLayout emailLayout, passLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get instance of Firebase
        auth = FirebaseAuth.getInstance();

        //find the ids
        emailLayout = (TextInputLayout) findViewById(R.id.nameInputLayout);
        passLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);
        emailInput = (TextInputEditText) findViewById(R.id.email);
        passInput = (TextInputEditText) findViewById(R.id.password);
        loginBtn = (AppCompatButton)findViewById(R.id.saveBtn);
        newUserBtn = (AppCompatButton) findViewById(R.id.newUserBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Go to new User Registration
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, Register.class);
                startActivity(myIntent);
            }
        });

        //login btn call method
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginForm();
            }
        });
    }

    private void loginForm() {
        String email = emailInput.getText().toString().trim();
        String pass = passInput.getText().toString().trim();

        if(!checkEmail()){
            return;
        }
        if(!checkPassword()){
            return;
        }
        emailLayout.setErrorEnabled(false);
        passLayout.setErrorEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if(!task.isSuccessful()){
                            Toast.makeText(Login.this,
                                    getString(R.string.loginError), Toast.LENGTH_SHORT).show();
                        } else{
                            startActivity(new Intent(Login.this, CreateList.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private boolean checkEmail() {
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {
            emailLayout.setErrorEnabled(true);
            emailLayout.setError(getString(R.string.emailError));
            emailInput.setError(getString(R.string.requiredMsg));
            requestFocus(emailInput);
            return false;
        }
        emailLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {
        String password = passInput.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {
            passLayout.setError(getString(R.string.passError));
            passInput.setError(getString(R.string.requiredMsg));
            requestFocus(passInput);
            return false;
        }
        passLayout.setErrorEnabled(false);
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