package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CreateClasses extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText descriptionOfClass;
    EditText dayOfClass;
    EditText capacityOfClass;
    EditText userID;
    Button createClassButton;

    @Override
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

        /**Bundle bundle = getIntent().getExtras();
        String userID_info = bundle.getString("Name");
        userID.setText(userID_info); */

        createClassButton = findViewById(R.id.createClassButton);
        createClassButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                int capacityOfTheClass = Integer.parseInt(capacityOfClass.getText().toString());
                // THE USER ID PROVIDED IS FOR A PLACEHOLDER, PLEASE ADD FUNCTIONALITY IN THE UI
                // SO THAT THE USER CAN CHOOSE WHO IS THE INSTRUCTOR FOR THAT CLASS
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(dayOfClass.toString().equals("-1") || capacityOfTheClass < 0){
                                    throw new IllegalStateException();
                                }
                                ClassTypes.create(db, nameOfClass.getText().toString(), descriptionOfClass.getText().toString(), dayOfClass.getText().toString(), capacityOfTheClass, userID.getText().toString());

                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IllegalStateException e) {
                                System.out.println("Invalid class inputs");

                            }
                        }
                    }).start();
            }
        });

    }

}