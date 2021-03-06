package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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
// The Sign in Page!
public class SignIn extends AppCompatActivity {
    // creates the text view for the inout of the users name and password
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText username, password;
    Button login;
    //creates the login button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        username = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println(username.getText().toString() + ", " + password.getText().toString());
                try {
                    // Detects if attempting to sign in with e-mail or username or not, sends down
                    // appropriate pathway to signing in with either.
                    if(username.getText().toString().indexOf('@') != -1){
                        System.out.println("using email sign in");
                        signIn(username.getText().toString(), password.getText().toString());
                    } else {
                        signInWithUsername(username.getText().toString(), password.getText().toString());
                    }
                // Generic exception catchers
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    //Searches the users database for a valid user
    private void authCheck(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        TextView login_details = findViewById(R.id.login_details);
//        TextView role_details = findViewById(R.id.role_details);
        if(currentUser != null){
            //login_details.setText("logged in as: " + currentUser.getEmail());
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
                                            //role_details.setText("role: " + task.getResult().getData().get("name"));

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
            //login_details.setText("Not logged in");
            //role_details.setText("Not logged in");
        }
    }
    // Signs in when a user's username is inputted
    private void signInWithUsername(String username, String password) throws InterruptedException, ExecutionException, IllegalAccessException, InvocationTargetException {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                User found_user = null;
                try {
                    User found_user = User.getUserByUsername(username);
                    System.out.println("FOUND USER");
                    System.out.println(found_user.getUsername());
                    signIn(found_user.getEmail(), password);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //signs in when a users Email is inputted
    private void signIn(String email, String password) {

                // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                reload();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            User signed_in_user = User.getUserByUserId(user.getUid());
                            System.out.println("setting signed in user to: ");
                            System.out.println(signed_in_user.getRole());
                            User.setCurrentUser(signed_in_user);
                            Intent intent;
                            if (signed_in_user.getRole().getName().equals("admin")) {
                                intent = new Intent(getApplicationContext(), AdminPage.class);
                            } else {
                                // Welcome page
                                intent = new Intent(getApplicationContext(), WelcomePage.class);

                            }
                            runOnUiThread(new Runnable() { // pop up of class successfully created
                                @Override
                                public void run() {
                                    Toast.makeText(SignIn.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            SignIn.this.startActivity(intent);
                            // Catchers for common exceptions appear below
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                ;

            } else {
                System.out.println("logged in failed");

                // If sign in fails, display a message to the user.
                Toast.makeText(SignIn.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                reload();
            }
        });
        // [END sign_in_with_email]
    }


    private void reload() {
        authCheck();
    }

}