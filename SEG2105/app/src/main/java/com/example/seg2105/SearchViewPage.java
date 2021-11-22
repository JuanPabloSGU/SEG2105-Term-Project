package com.example.seg2105;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class SearchViewPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Context temp_this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        temp_this=this;

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
