package com.example.gamze.firebasedemo02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button userEkleBtn, siparisSayfasiBtn, showUsersBtn;
    EditText usernameTxt, passwordTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference("db");

        userEkleBtn = (Button) findViewById(R.id.btnUserEkle);

        usernameTxt = (EditText) findViewById(R.id.userName);
        passwordTxt = (EditText) findViewById(R.id.password);
        siparisSayfasiBtn = (Button) findViewById(R.id.btnSiparisSayfasi);
        showUsersBtn = (Button) findViewById(R.id.show_userbtn);

        dbRef.setValue("Firebase App Db");


        showUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });
        userEkleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // primary key olusturuluyor
//                String primaryKey = dbRef.push().getKey();

                DatabaseReference dbRef_add = database.getReference("users");

                String primaryKey = usernameTxt.getText().toString() + passwordTxt.getText().toString();
                // primary key e child ekliyoruz
                String uname = usernameTxt.getText().toString();
                String pw = passwordTxt.getText().toString();
                dbRef_add.child(primaryKey).child("username").setValue(uname);
                dbRef_add.child(primaryKey).child("password").setValue(pw);

                usernameTxt.setText("");
                passwordTxt.setText("");
                Toast.makeText(MainActivity.this, "User Eklendi", Toast.LENGTH_SHORT).show();
            }
        });

        siparisSayfasiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SiparisEkleActivity.class);
                startActivity(intent);
            }
        });
    }
}
