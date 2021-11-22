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

public class SearchInstrPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_instr);


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

        View search_button = findViewById(R.id.SearchInstr);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchInstrPage.this, SearchViewPage.class);
                SearchInstrPage.this.startActivity(intent);
            }
        });
    }


    public void loadClassTypes(Context context) throws ExecutionException, InterruptedException {

        ArrayList<UserView> users = UserView.getAllUsers();


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> class_types_name = new ArrayList<String>();
                for (UserView user : users) {
                    class_types_name.add(user.getUsername());
                }
                Spinner spinner = (Spinner) findViewById(R.id.spinnerInstr);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, class_types_name);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
            }
        });

    }

}

