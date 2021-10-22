package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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


public class ClassTypesActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Context temp_this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        temp_this = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_types);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        View create_class_type = findViewById(R.id.create_class_button);

        create_class_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Creating class");
                Intent intent = new Intent(getApplicationContext(), CreateClasses.class);
                ClassTypesActivity.this.startActivity(intent);
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

    // Loads all users to display to screen for admin
    public void loadUsers() throws ExecutionException, InterruptedException {
        System.out.println("loading users");
        ArrayList<ClassTypes> class_types = new ArrayList<ClassTypes>();

        QuerySnapshot task = Tasks.await(db.collection("class_types").get());

        for (DocumentSnapshot document : task.getDocuments()) {

            String class_type_name = document.get("name").toString();
            String class_type_description = document.get("description").toString();
            String class_type_day = document.get("day").toString();
            int class_type_capacity = Integer.parseInt(document.get("capacity").toString());
            ClassTypes temp_class = new ClassTypes(document.getId(),class_type_name, class_type_description, class_type_day, class_type_capacity);
            class_types.add(temp_class);
        }

        // Prints all users to screen
        for(int i = 0; i < class_types.size(); i++){
            System.out.println("User name: " + class_types.get(i).name);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Lookup the recyclerview in activity layout
                RecyclerView rvContacts = (RecyclerView) findViewById(R.id.class_recycler_view);

                // Initialize contacts
                // Create adapter passing in the sample user data
                ClassTypesAdapter adapter = new ClassTypesAdapter(class_types);
                // Attach the adapter to the recyclerview to populate items
                rvContacts.setAdapter(adapter);
                // Set layout manager to position the items
                rvContacts.setLayoutManager(new LinearLayoutManager(temp_this));
                // That's all!
            }
        });

    }
}