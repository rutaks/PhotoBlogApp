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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBar;
    private Button buttonLogin;
    private Button buttonSignup;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        setupForm();
        loginButtonOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isLoggedIn()){
            goToMain();
        }
    }

    /**
     * Login Button onClick Handler, will attempt to login on click if email & password
     * are valid. Any Error Found Will be displayed as a Toast
     * @return      void
     */
    public void loginButtonOnClick(){
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextEmail.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                System.out.println("worked");
                                progressBar.setVisibility(View.INVISIBLE);
                                goToMain();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Make Sure All Fields Are Valid", Toast.LENGTH_SHORT).show();
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
     * Binds XML Layout elements To Code Variables
     * @return      void
     */
    private void setupForm(){
        editTextEmail = findViewById(R.id.edit_text_login_email);
        editTextPassword = findViewById(R.id.edit_text_login_password);
        progressBar = findViewById(R.id.progressBar_login);
        buttonLogin = findViewById(R.id.button_login);
        buttonSignup = findViewById(R.id.button_register);
    }

    /**
     * Returns an indicator stating if user logged in or not
     * @return      Boolean
     */
    public Boolean isLoggedIn(){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        return firebaseUser != null ? true : false;
    }
}
