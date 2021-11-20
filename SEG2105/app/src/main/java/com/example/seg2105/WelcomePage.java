package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

public class WelcomePage extends AppCompatActivity {
    String userRoleInformation;
    TextView userInfo, userRole;
    Button cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        cont=findViewById(R.id.cont);
        userInfo = findViewById(R.id.user_name_info);
        userRole = findViewById(R.id.user_role_info);

        Bundle bundle = getIntent().getExtras();
        String userInformation = bundle.getString("Name");
        userRoleInformation = bundle.getString("Role");

        userInfo.setText(userInformation);
        userRole.setText(userRoleInformation);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userRoleInformation.equals("instructor")){
                    Intent intent=new Intent(WelcomePage.this, InstrPage.class);
                    WelcomePage.this.startActivity(intent);
                }
                else if(userRoleInformation.equals("member")){
                    Intent intent=new Intent(WelcomePage.this, Member.class);
                    WelcomePage.this.startActivity(intent);
                }
                else{
                    System.out.println("no role found");
                }

            }
        });
    }





}