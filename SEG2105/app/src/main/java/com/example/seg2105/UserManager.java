package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class UserManager extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Context temp_this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        temp_this = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_manager);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

//        createDemoUser();
        View add_user = findViewById(R.id.add_user_button);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Creating user");
                createDemoUser();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("calling");
                try {
                    loadUsers();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void createDemoUser(){
        addUser("adam3", "adam3@databending.ca", "admin123", "instructor");
    }

    public void deleteUser(String user_id) {
        db.collection("users").document(user_id).delete();
    }

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
        mAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println("Created user successfully");
                String user_id = task.getResult().getUser().getUid();
                Map<String, Object> data1 = new HashMap<>();
                data1.put("user_id", user_id);
                data1.put("username", user_name);
                data1.put("role", finalUser_role1);
                db.collection("users").add(data1);
            }
        });
    }

    public void loadUsers() throws ExecutionException, InterruptedException {
        System.out.println("loading users");
        ArrayList<UserView> users = new ArrayList<UserView>();

        QuerySnapshot task = Tasks.await(db.collection("users").get());

        for (DocumentSnapshot document : task.getDocuments()) {

            DocumentSnapshot role_task = Tasks.await(document.getDocumentReference("role").get());
            String user_name = document.get("username").toString();
            String user_id = document.get("user_id").toString();
            String user_role = role_task.get("name").toString();
            UserView temp_user = new UserView(user_name, user_role, user_id);
            users.add(temp_user);
        }

        for(int i = 0; i < users.size(); i++){
            System.out.println("User name: " + users.get(i).name);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//
//                // Lookup the recyclerview in activity layout
                    RecyclerView rvContacts = (RecyclerView) findViewById(R.id.user_recycler_view);
//
//                // Initialize contacts
//                // Create adapter passing in the sample user data
                    UserManagerAdapter adapter = new UserManagerAdapter(users);
//                // Attach the adapter to the recyclerview to populate items
                    rvContacts.setAdapter(adapter);
//                // Set layout manager to position the items
                    rvContacts.setLayoutManager(new LinearLayoutManager(temp_this));
//                // That's all!
            }
        });

    }
}