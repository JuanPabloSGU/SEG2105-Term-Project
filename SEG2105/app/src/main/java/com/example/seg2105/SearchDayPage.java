package com.example.seg2105;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class SearchDayPage extends AppCompatActivity {

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
                Intent intent = new Intent(SearchDayPage.this, SearchViewPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("search_page", "day");
                bundle.putString("search_page_class_day_of_the_week", ((Spinner) findViewById(R.id.spinnerClass)).getSelectedItem().toString());
                intent.putExtras(bundle);
                SearchDayPage.this.startActivity(intent);
            }
        });
    }


    public void loadClassTypes(Context context) throws ExecutionException, InterruptedException {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Resources res = getResources();
                ArrayList<String> days = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.days_of_the_week_array)));
                Spinner spinner = (Spinner) findViewById(R.id.spinnerClass);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, days);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
            }
        });
    }
}
