package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        signIn = findViewById(R.id.SignIn);
        signUp = findViewById(R.id.SignUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onSetSignInButton(v);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetSignUpButton(v);
            }
        });

    }

    public void onSetSignUpButton(View view){
        // sign up button which appears on main, all users able to access
        // brings to SignUp.class
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        MainActivity.this.startActivity(intent);
    }

    public void onSetSignInButton(View view){
        // sign in button for users with pre existing accounts to sign in
        // brings to SignIn.class
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        MainActivity.this.startActivity(intent);
    }


}