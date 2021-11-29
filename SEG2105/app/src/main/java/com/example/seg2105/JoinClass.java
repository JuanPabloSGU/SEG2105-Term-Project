package com.example.seg2105;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class JoinClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);

        View searchDay = findViewById(R.id.SearchByDay);
        searchDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(JoinClass.this, MemberSearchDayPage.class);
                JoinClass.this.startActivity(intent);
            }
        });

        View searchClass = findViewById(R.id.SearchByClass);
        searchClass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(JoinClass.this, MemberSearchClassPage.class);
                JoinClass.this.startActivity(intent);
            }
        });

    }

}
