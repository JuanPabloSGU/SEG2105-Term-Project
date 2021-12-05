package com.example.seg2105;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Member extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_page);

        View viewClass = findViewById(R.id.MemViewClasses);
        viewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.setEnrollementStatus(true);
                Intent intent = new Intent(Member.this, SearchViewPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("search_page", "all");
                intent.putExtras(bundle);
                Member.this.startActivity(intent);
            }
        });

        View view_enroll_button = findViewById(R.id.ViewEnrolled);
        view_enroll_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                User.setEnrollementStatus(false);
                Intent intent = new Intent(Member.this, SearchViewPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("search_page", "enrolled");
                intent.putExtras(bundle);
                Member.this.startActivity(intent);
            }
        });

        View view_joinclass = findViewById(R.id.JoinClass);
        view_joinclass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                User.setEnrollementStatus(true);

                Intent intent = new Intent(Member.this, JoinClass.class);
                Member.this.startActivity(intent);
            }
        });

    }

}
