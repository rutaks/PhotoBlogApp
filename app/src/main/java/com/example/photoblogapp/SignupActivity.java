package com.example.photoblogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private ProgressBar progressBar;
    private Button buttonSignup;
    private Button buttonGoToLogin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        setupForm();

        handleSignUpButtonClicked();
        handleGoToLoginButtonClicked();
    }

    /**
     * Go to login Button onClick Handler, will return to previous view `Login View`
     * @return      void
     */
    private void handleGoToLoginButtonClicked() {
        buttonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Sign Up Button onClick Handler, will attempt to create user's account on click if email & password
     * are valid. Any Error Found Will be displayed as a Toast
     * @return      void
     */
    private void handleSignUpButtonClicked() {
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String passwordConfirm = editTextConfirmPassword.getText().toString();

                if (!email.trim().isEmpty() && !password.trim().isEmpty() && !passwordConfirm.trim().isEmpty()){
                    if(password.equals(passwordConfirm)){
                        progressBar.setVisibility(View.VISIBLE);

                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.INVISIBLE);

                                if(task.isSuccessful()) goToSetup();
                                else {
                                    String message = task.getException().getMessage();
                                    Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Make Sure All Fields Are Not Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Directs User To Main View in case of user logged in
     * @return      void
     */
    public void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Directs User To Setup View in case of user registration success
     * @return      void
     */
    public void goToSetup(){
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Binds XML Form Layout elements To Code Variables
     * @return      void
     */
    private void setupForm(){
        editTextEmail = findViewById(R.id.edit_text_signup_email);
        editTextPassword = findViewById(R.id.edit_text_signup_password);
        editTextConfirmPassword = findViewById(R.id.edit_text_signup_password_confirm);
        progressBar = findViewById(R.id.progressBar_signup);
        buttonSignup = findViewById(R.id.button_signup);
        buttonGoToLogin = findViewById(R.id.button_go_to_login);
    }
}
