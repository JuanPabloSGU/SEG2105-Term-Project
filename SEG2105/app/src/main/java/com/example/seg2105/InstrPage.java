package com.example.seg2105;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class InstrPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instr_page);

        View addClass = findViewById(R.id.addClass);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, CreateClasses.class);
                InstrPage.this.startActivity(intent);
            }
        });

        View viewClass = findViewById(R.id.viewClasses);
        viewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, ClassTypesActivity.class);
                InstrPage.this.startActivity(intent);
            }
        });


        View create_scheduled_class_button = findViewById(R.id.create_scheduled_class);
        create_scheduled_class_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(InstrPage.this,
                        CreateScheduledClass.class);
                InstrPage.this.startActivity(intentMain);
            }
        });

        View search_class_button = findViewById(R.id.SearchByClass);
        search_class_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, SearchClassNamePage.class);
                InstrPage.this.startActivity(intent);
            }
        });

        View search_Instr_button = findViewById(R.id.SearchByInstr);
        search_Instr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstrPage.this, SearchInstrPage.class);
                InstrPage.this.startActivity(intent);
            }
        });
    }
}
