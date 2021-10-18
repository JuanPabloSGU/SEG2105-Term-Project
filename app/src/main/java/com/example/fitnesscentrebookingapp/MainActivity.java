package com.example.fitnesscentrebookingapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher <Intent> adminActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){

            }
        }
    });

    ActivityResultLauncher <Intent> instructorActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){

            }
        }
    });

    ActivityResultLauncher <Intent> memberActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){

            }
        }
    });

    public void OnOpenInstructorLogin(View view){
        Intent intent = new Intent(getApplicationContext(), Instructor.class);
        instructorActivityResultLauncher.launch(intent);

    }

    public void OnOpenMemberLogin(View view){
        Intent intent = new Intent(getApplicationContext(), Member.class);
        memberActivityResultLauncher.launch(intent);
    }

    public void OnOpenAdminLogin(View view){
        Intent intent = new Intent(getApplicationContext(), Administrator.class);
        adminActivityResultLauncher.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = findViewById(R.id.admin);
        instructor = findViewById(R.id.instructor);
        member = findViewById(R.id.member);

        admin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnOpenAdminLogin(v);
            }
        });

        instructor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnOpenInstructorLogin(v);
            }
        });

        member.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnOpenMemberLogin(v);
            }
        });

    }

    Button admin, instructor, member;
}