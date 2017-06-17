package com.example.gamze.loginform1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("password");
        if(name.equals("gamze") && password.equals("sen")){
            setContentView(R.layout.activity_success);
        }
        else{
            setContentView(R.layout.activity_main);
        }
    }
}
