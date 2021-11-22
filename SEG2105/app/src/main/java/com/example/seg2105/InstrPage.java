package com.example.seg2105;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
// The instructor Page from the instr page xml
public class InstrPage extends AppCompatActivity {

    // creates the buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instr_page);

        View addClass = findViewById(R.id.addClass);

        addClass.setOnClickListener(new View.OnClickListener(){ // goes to the Create Classes page

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, CreateClasses.class);
                InstrPage.this.startActivity(intent);
            }
        });

        View viewClass = findViewById(R.id.viewClasses);
        viewClass.setOnClickListener(new View.OnClickListener(){ // goes to the ClassTypesActivity Page
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, ClassTypesActivity.class);
                InstrPage.this.startActivity(intent);
            }
        });


        View create_scheduled_class_button = findViewById(R.id.create_scheduled_class);
        create_scheduled_class_button.setOnClickListener(new View.OnClickListener() { // goes to the Create Scheduled Class Page
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(InstrPage.this,
                        CreateScheduledClass.class);
                InstrPage.this.startActivity(intentMain);
            }
        });

        View search_class_button = findViewById(R.id.SearchByClass);
        search_class_button.setOnClickListener(new View.OnClickListener() { // goes to Search Class Name Page
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, SearchClassNamePage.class);
                InstrPage.this.startActivity(intent);
            }
        });

        View search_Instr_button = findViewById(R.id.SearchByInstr);
        search_Instr_button.setOnClickListener(new View.OnClickListener() { // goes to the Search Instructor Page
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, SearchInstrPage.class);
                InstrPage.this.startActivity(intent);
            }
        });
    }
}
