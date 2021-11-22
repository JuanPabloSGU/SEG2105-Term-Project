package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
// The Sign up page
public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Only instructor and member accounts can be created by normal users.
        instructor = findViewById(R.id.instructor_button);
        gymMember = findViewById(R.id.gymMember_button);
        signUp = findViewById(R.id.signup_button);

        instructor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                role = "instructor";
            }
        });

        gymMember.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                role = "member";
            }
        });

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                EditText username = (EditText) findViewById(R.id.username);
                EditText email = (EditText) findViewById(R.id.email);
                EditText password = (EditText) findViewById(R.id.password);

                System.out.println(username.getText().toString() + ", " + email.getText().toString() + ", " + password.getText().toString() + ", " + role);
                if (username.toString() == null || email.toString() == null || password.toString() == null){ // will show error if the input is nothing
                    Toast.makeText(SignUp.this, "Input is null!.",
                            Toast.LENGTH_SHORT).show();
                }else {
                    addUser(username.getText().toString(), email.getText().toString(), password.getText().toString(), role);
                    System.out.println("User Created");
                }
            }
        });

    }

    Button instructor, gymMember, signUp;

    // adds the user to the User Database
    public void addUser(String user_name, String user_email, String user_password, String user_role){
        DocumentReference finalUser_role = null;
        switch(user_role){
            case "instructor":
                finalUser_role = db.document("roles/3xQrDfZhc7Kdjr9TTueY");
                break;
            case "member":
                finalUser_role = db.document("/roles/KhXfzrrVCK2dJtSoQeWX");
                break;
        }
        DocumentReference finalUser_role1 = finalUser_role;
        // Creating user in FireBase with inputs from user
        mAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            // After creating user, it adds user to FireBase
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println("Created user successfully");
                String user_id = task.getResult().getUser().getUid();
                Map<String, Object> data1 = new HashMap<>();
                data1.put("user_id", user_id);
                data1.put("username", user_name);
                data1.put("role", finalUser_role1);
                data1.put("email", user_email);
                db.collection("users").add(data1); // addes to the user Database
            }
        });
    }

}