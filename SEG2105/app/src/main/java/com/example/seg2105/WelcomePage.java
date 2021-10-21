package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        userInfo = findViewById(R.id.user_name_info);
        userRole = findViewById(R.id.user_role_info);

        Bundle bundle = getIntent().getExtras();
        String userInformation = bundle.getString("Name");
        String userRoleInformation = bundle.getString("Role");

        userInfo.setText(userInformation);
        userRole.setText(userRoleInformation);
    }

    TextView userInfo, userRole;
}