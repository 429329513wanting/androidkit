package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.Utils;

public class UpMainActivity extends AppCompatActivity {

    private Button oneBtn;
    private Button twoBtn;
    private Button currBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_main);

        Utils.init(this);

        oneBtn = findViewById(R.id.up_one_btn);
        twoBtn = findViewById(R.id.up_two_btn);
        currBtn = findViewById(R.id.up_curr_btn);

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toListVC(v);

            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toListVC(v);
            }
        });

        currBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toListVC(v);
            }
        });

    }
    private void toListVC(View view){


        Intent intent = new Intent(this,TicketListActivity.class);
        if (view.getId() == R.id.up_one_btn){

            intent.putExtra(Constraint.UP_TYPE,"1");

        }else if (view.getId() == R.id.up_two_btn){

            intent.putExtra(Constraint.UP_TYPE,"2");

        }else {

            intent.putExtra(Constraint.UP_TYPE,"0");
        }

        startActivity(intent);
    }
}
