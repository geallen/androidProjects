package com.example.gamze.sessiondeneme01;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences, preferences1;
    SharedPreferences.Editor editor;
    String mail_str,pw_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        if(preferences.getBoolean("login", false)){
            Intent i = new Intent(getApplicationContext(),HomePageActivity.class);
            startActivity(i);
            finish();
        }

        final EditText username = (EditText) findViewById(R.id.input_name);
        final EditText password = (EditText) findViewById(R.id.input_password);
        Button btnLogin = (Button) findViewById(R.id.loginButton);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail_str = username.getText().toString();
                pw_str = password.getText().toString();

                if(mail_str.matches("") || pw_str.matches("")){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Fill all blanks!");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                        }
                    });
                    alertDialog.show();
                }else{
                    String email = preferences.getString("email", "");
                    String pw = preferences.getString("password", "");
                    if(email.matches(mail_str) && pw.matches(pw_str)){
                        editor.putBoolean("login", true);
                        Intent i = new Intent(getApplicationContext(),HomePageActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        editor = preferences.edit();


                        editor.putString("email", mail_str);
                        editor.putString("password", pw_str);

                        editor.putBoolean("login", true);
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(),HomePageActivity.class);
                        startActivity(i);
                        finish();
                    }
                }


            }
        });
    }
}
