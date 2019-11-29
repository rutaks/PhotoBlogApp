package com.example.photoblogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbarMain;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        setToolbarMain();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isLoggedIn()){
            goToLogin();
        }
    }
    //MARK: -MENU SETUP
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_account_settings:
                return true;
            case R.id.button_logout:
                logout();
                return true;
            default:
                return false;
        }
    }

    /**
     * Binds XML Toolbar elements To Code Toolbar
     * @return      void
     */
    public void setToolbarMain() {
        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle("Ifoto");
    }

    /**
     * Directs User To Login Form in case of user not logged in
     *
     * @return void
     */
    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Re-directs User To Login Screen & Invalidates User's Session
     *
     * @return void
     */
    public void logout(){
        auth.signOut();
        goToLogin();
    }

    /**
     * Returns an indicator stating if user logged in or not
     *
     * @return Boolean
     */
    public Boolean isLoggedIn() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser != null ? true : false;
    }
}
