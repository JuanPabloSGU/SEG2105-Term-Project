package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
// Create Classes Pages for
public class CreateClasses extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText descriptionOfClass;
    EditText dayOfClass;
    EditText capacityOfClass;
    EditText userID;
    Button createClassButton;

    @Override // creates edit texts for the name of the class, the description of the class, the day of the class, and the capacity of the class, and the ID of the user
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classes);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        EditText nameOfClass = (EditText) findViewById(R.id.nameOfClass);
        EditText descriptionOfClass = (EditText) findViewById(R.id.descriptionClass);
        EditText dayOfClass = (EditText) findViewById(R.id.day);
        EditText capacityOfClass = (EditText) findViewById(R.id.capacity);
        EditText userID = (EditText) findViewById(R.id.userID);

        createClassButton = findViewById(R.id.createClassButton);
        createClassButton.setOnClickListener(new View.OnClickListener() {
        // when the create a new class button is pressed.
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try { // throws if nothing is submitted
                            int capacityOfTheClass = Integer.parseInt(capacityOfClass.getText().toString());
                            if (nameOfClass.getText().toString() == "" || descriptionOfClass.getText().toString() == "" || userID.getText().toString() == "" || capacityOfClass.getText().toString() == "") {
                                throw new IllegalStateException();
                            }
                            boolean flag = checkClasses(nameOfClass.getText().toString(), dayOfClass.getText().toString());

                            if(flag == true) {
                                ClassTypes.create(db, nameOfClass.getText().toString(), descriptionOfClass.getText().toString(), dayOfClass.getText().toString(), capacityOfTheClass, userID.getText().toString());
                            }

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) { // pop ups for the class inputs
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Invalid class inputs");
                                    Toast.makeText(CreateClasses.this, "Invalid Inputs for Class!.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (NumberFormatException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Invalid class inputs");
                                    Toast.makeText(CreateClasses.this, "Invalid Inputs for Class!.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
    // checks classes to see if any have the same day and same type of class
    public boolean checkClasses( String name ,  String day) {
        boolean flag = true;
        ArrayList<ClassTypes> allClasses = null;
        try {
            allClasses = ClassTypes.getAllClassTypes();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < allClasses.size(); i++) {
            ClassTypes temp = allClasses.get(i);
            if (temp.day.equals(day) && temp.name.equals(name)) {
                flag = false; //OVERRIDDEN SCHEDULE
                System.out.println("Class already scheduled for that day!");
                //popup
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreateClasses.this, "Class already scheduled.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        return flag;
    }

}