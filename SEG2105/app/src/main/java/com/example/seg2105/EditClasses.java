package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// EDIT Classes page from the edit_classes xml
public class EditClasses extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_classes);

        newDescription = (EditText) findViewById(R.id.changeText);
        confirmText = (Button) findViewById(R.id.confirmText);

        confirmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // takes the string of the textview edits the class description
                String newEdit = newDescription.getText().toString();
                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    String value = bundle.getString("classId");
                    ClassTypes.editClassDescription(value, newEdit, UserView.getCurrentUser());
                }

            }
        });
    }



    EditText newDescription;
    Button confirmText;

}