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
    //onCreate for all the buttons on the Admin Page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // When Clicked, goes to the "View all Users Page", or the UserManager class
        View user_manager_button = findViewById(R.id.viewUsers);
        user_manager_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(AdminPage.this ,
                        UserManager.class); //sets the intent from here to a new instance, the User Manager class
                AdminPage.this.startActivity(intentMain);
            }
        });
        // When Clicked, goes to the "View all Users Page", or the ClassTypesActivity class
        View class_type_button = findViewById(R.id.viewClasses);
        class_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(AdminPage.this ,
                        ClassTypesActivity.class); //sets the intent from here to a new instance, the ClassTypesActivity class
                AdminPage.this.startActivity(intentMain);
            }
        });
        // When Clicked, goes to the "Create Scheduled Class" page, or the CreateScheduleClass class
        View create_scheduled_class_button = findViewById(R.id.create_scheduled_class);
        create_scheduled_class_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(AdminPage.this ,
                        CreateScheduledClass.class);//sets the intent from here to a new instance, the CreateScheduleClass class
                AdminPage.this.startActivity(intentMain);
            }
        });

    }


}