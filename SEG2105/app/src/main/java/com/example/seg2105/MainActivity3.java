package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity3 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View login_button = findViewById(R.id.login);
        View logout_button = findViewById(R.id.logout_button);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Wahtever");
                signIn("admin@databending.ca", "admin123");
            }
        });
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                reload();
            }
        });
        View user_manager_button = findViewById(R.id.user_manager);
        user_manager_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(MainActivity3.this ,
                        UserManager.class);
                MainActivity3.this.startActivity(intentMain);
            }
        });

        View class_type_button = findViewById(R.id.class_type_button);
        class_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(MainActivity3.this ,
                        ClassTypesActivity.class);
                MainActivity3.this.startActivity(intentMain);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        reload();
    }

    private void authCheck(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView login_details = findViewById(R.id.login_details);
        TextView role_details = findViewById(R.id.role_details);
        if(currentUser != null){
            login_details.setText("logged in as: " + currentUser.getEmail());
            db.collection("users").whereEqualTo("user_id", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    System.out.println(document.getId() + " => " + document.getData());
                                    System.out.println("username: " + document.get("username"));
                                    document.getDocumentReference("role").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            System.out.println("role: " + task.getResult().getData().get("name"));
                                            role_details.setText("role: " + task.getResult().getData().get("name"));
                                        }
                                    });

                                }
                            } else {
                                System.out.println(task.getException());
                                System.out.println("Unable to query database");
                            }
                        }
                    });
        } else {
            login_details.setText("Not logged in");
            role_details.setText("Not logged in");
        }
    }

    private void signIn(String email, String password) {
        TextView login_details = findViewById(R.id.login_details);
        TextView role_details = findViewById(R.id.role_details);
        login_details.setText("loading...");
        role_details.setText("loading...");
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("logged in success");
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            reload();
//                            updateUI(user);
                        } else {
                            System.out.println("logged in failed");

                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity3.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            reload();
//                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }


    private void reload() {

        authCheck();
    }
}