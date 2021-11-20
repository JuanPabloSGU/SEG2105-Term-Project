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

    public void loadUsers() throws ExecutionException, InterruptedException {
        ArrayList<UserView> users = UserView.getAllUsers();
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