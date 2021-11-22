package com.example.seg2105;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class SearchClassNamePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_classname);


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


        View search_button = findViewById(R.id.SearchClass);
        search_button.setOnClickListener(new View.OnClickListener() { // goes to Search Class Name Page
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchClassNamePage.this, SearchViewPage.class);
                SearchClassNamePage.this.startActivity(intent);
            }
        });
    }


    public void loadClassTypes(Context context) throws ExecutionException, InterruptedException {

        ArrayList<ClassType> class_types = ClassType.getAllClassTypes();


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> class_types_name = new ArrayList<String>();
                for (ClassType class_type : class_types) {
                    class_types_name.add(class_type.name);
                }
                Spinner spinner = (Spinner) findViewById(R.id.spinnerClass);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, class_types_name);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
            }
        });
    }
}
