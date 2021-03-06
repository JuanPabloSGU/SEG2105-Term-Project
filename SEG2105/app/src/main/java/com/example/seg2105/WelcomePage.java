package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// the Welcome page for members and instructors
public class WelcomePage extends AppCompatActivity {
    String userRoleInformation;
    static String userInformation;
    TextView userInfo, userRole;
    Button cont;
    // creates continue button and text views for the role and username of the current user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        cont=findViewById(R.id.cont);
        userInfo = findViewById(R.id.user_name_info);
        userRole = findViewById(R.id.user_role_info);
        // pulls the users name and role from the current bundle and displays on the screen
        User signed_in_user = User.getCurrentUser();
        System.out.println(signed_in_user);
        userRoleInformation = signed_in_user.getRole().getName();
        userInformation = signed_in_user.getUsername();

        userInfo.setText(userInformation);
        userRole.setText(userRoleInformation);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userRoleInformation.equals("instructor")){ // sends to instructor page if instructor
                    Intent intent=new Intent(WelcomePage.this, InstrPage.class);
                    WelcomePage.this.startActivity(intent);
                }
                else if(userRoleInformation.equals("member")){ // sends to member page if member
                    Intent intent=new Intent(WelcomePage.this, Member.class);
                    WelcomePage.this.startActivity(intent);
                }
                else{
                    System.out.println("no role found");
                }

            }
        });
    }
    public static String getCurrentUser(){
        return userInformation;
    }






}