package com.example.photoblogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SetupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        toolbar = findViewById(R.id.toolbar_setup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account Settings");

        imageView = findViewById(R.id.profile_image);

        handleImageViewClicked();
    }

    private void handleImageViewClicked(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(SetupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(SetupActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(SetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        //TODO: -OPEN GALLERY
                        Toast.makeText(SetupActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
