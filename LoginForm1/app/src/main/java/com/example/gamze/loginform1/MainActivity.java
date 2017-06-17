package com.example.gamze.loginform1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = ((Button) findViewById(R.id.login_button));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = ((EditText) findViewById(R.id.editText));
                EditText password =  ((EditText) findViewById(R.id.editText2));
                Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("password", password.getText().toString());
                startActivity(intent);
            }
        });
    }
}
