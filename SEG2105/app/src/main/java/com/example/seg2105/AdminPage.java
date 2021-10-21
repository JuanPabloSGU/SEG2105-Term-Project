package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        View user_manager_button = findViewById(R.id.viewUsers);
        user_manager_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(AdminPage.this ,
                        UserManager.class);
                AdminPage.this.startActivity(intentMain);
            }
        });

        View class_type_button = findViewById(R.id.viewClasses);
        class_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(AdminPage.this ,
                        ClassTypesActivity.class);
                AdminPage.this.startActivity(intentMain);
            }
        });

    }


}