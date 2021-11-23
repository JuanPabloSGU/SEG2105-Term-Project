package com.example.seg2105;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seg2105.databinding.ActivityCreateScheduledClassBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CreateScheduledClass extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private AppBarConfiguration appBarConfiguration;
    private ActivityCreateScheduledClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_scheduled_class);
        Context current_context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("calling");
                try {
                    loadClassTypes(current_context);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_the_week_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        Spinner spinner2 = (Spinner) findViewById(R.id.spinner4);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String day_of_the_week = ((Spinner) findViewById(R.id.spinner2)).getSelectedItem().toString();
        String difficulty = ((Spinner) findViewById(R.id.spinner4)).getSelectedItem().toString();

        User current_user = User.getCurrentUser();

        System.out.println(current_user);

        View createClassButton = findViewById(R.id.createScheduledClassButton);
        createClassButton.setOnClickListener(new View.OnClickListener() {
        // on Click of the "create a new class" button
            @Override
            public void onClick(View v) {
                String class_name = ((Spinner) findViewById(R.id.spinner3)).getSelectedItem().toString();

                Integer capacityOfClass =  Integer.parseInt(((EditText) findViewById(R.id.capacity)).getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        customCallback cb = new customCallback<ScheduledClass>() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() { // pop up of class successfully created
                                    @Override
                                    public void run() {
                                        Toast.makeText(CreateScheduledClass.this, "Class created!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onSuccess(Task<AuthResult> task) {

                            }

                            @Override
                            public void onError(Exception err) {

                            }
                            // pop up for error
                            @Override
                            public void onError(String err) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("Invalid class inputs");
                                        Toast.makeText(CreateScheduledClass.this, err, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        };
                        // prints the class type to the terminal
                        try {
                            System.out.println("CLASS NAME: " + class_name);
                            System.out.println("CURRENT USER ID: " + current_user.getId());
                            ClassType class_type = ClassType.searchByClassName(class_name);
                            ScheduledClass.create(day_of_the_week,capacityOfClass, difficulty,current_user,class_type, cb); // creates the class
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }
    // Loads all the class types names and put them into the spinner
    public void loadClassTypes(Context context) throws ExecutionException, InterruptedException {
        //pulls all the class types
        ArrayList<ClassType> class_types = ClassType.getAllClassTypes();


        runOnUiThread(new Runnable() {
            @Override
            public void run() { //puts the names into another arraylist
                ArrayList<String> class_types_name = new ArrayList<String>();
                for(ClassType class_type : class_types){
                    class_types_name.add(class_type.name);
                }
                Spinner spinner = (Spinner) findViewById(R.id.spinner3);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, class_types_name);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
            }
        });
    }

}