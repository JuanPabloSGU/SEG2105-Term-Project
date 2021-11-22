package com.example.seg2105;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

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
        ArrayList<ScheduledClass> classes;
        Bundle bundle = getIntent().getExtras();
        String which_page = bundle.getString("search_page");
        if(which_page.equals("all")) {
            classes = ScheduledClass.getAllScheduledClasses();
        }
        else if(which_page.equals("instructor")) {
            classes = ScheduledClass.searchByInstructorUsername(bundle.getString("search_page_instructor_username"));
        }
        else if(which_page.equals("class_type")) {
            classes = ScheduledClass.searchByClassTypeName(bundle.getString("search_page_class_type_name"));
        } else {
            classes = ScheduledClass.getAllScheduledClasses();
        }
        runOnUiThread(new Runnable() { // pop up of class successfully created
            @Override
            public void run() {
                Toast.makeText(SearchViewPage.this, "Found " + classes.size() + " results", Toast.LENGTH_SHORT).show();
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//
//                // Lookup the recyclerview in activity layout
                RecyclerView rvContacts = (RecyclerView) findViewById(R.id.search_recycler_view);
//
//                // Initialize contacts
//                // Create adapter passing in the sample user data
                SearchPageAdapter adapter = new SearchPageAdapter(classes);
//                // Attach the adapter to the recyclerview to populate items
                rvContacts.setAdapter(adapter);
//                // Set layout manager to position the items
                rvContacts.setLayoutManager(new LinearLayoutManager(temp_this));
//                // That's all!
            }
        });
    }
}
