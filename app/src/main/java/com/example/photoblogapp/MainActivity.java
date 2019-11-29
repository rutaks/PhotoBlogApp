package com.example.photoblogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isLoggedIn()){
            goToLogin();
        }
    }

    /**
     * Directs User To Login Form in case of user not logged in
     * @return      void
     */
    public void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Returns an indicator stating if user logged in or not
     * @return      Boolean
     */
    public Boolean isLoggedIn(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser != null ? true : false;
    }
}
