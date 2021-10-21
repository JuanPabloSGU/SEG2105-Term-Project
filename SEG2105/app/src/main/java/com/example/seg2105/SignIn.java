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

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText username, password;
    Button login;

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
                signIn(username.getText().toString(), password.getText().toString());

            }
        });
    }

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


    private void signIn(String email, String password) {
//        TextView login_details = findViewById(R.id.login_details);
//        TextView role_details = findViewById(R.id.role_details);
//        login_details.setText("loading...");
//        role_details.setText("loading...");
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
                            try {
                                UserView.getUser(user.getUid(),  new UserView.GetUserInterface() {

                                    @Override
                                    public void onSuccess(UserView user) {
                                        System.out.println("USER ROLE!!: " + user.role);
                                        if(user.role.equals("admin")){
                                            Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                                            SignIn.this.startActivity(intent);
                                        }else{
                                            // Welcome page
                                            Intent intent = new Intent(getApplicationContext(), WelcomePage.class);

                                            String userInformation = user.getUsername();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("Role", user.role);
                                            bundle.putString("Name", user.getUsername());
                                            intent.putExtras(bundle);

                                            SignIn.this.startActivity(intent);
                                        }
                                    }

                                });
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            };

                        } else {
                            System.out.println("logged in failed");

                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",
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