package com.example.gamze.loginformdeneme01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Login2Activity extends AppCompatActivity {

    EditText edtuserid, edtpass;
    Button btnlogin;
  //  ProgressBar pbbar;
    SharedPreferences preferences, preferences1;
    SharedPreferences.Editor editor;
    String uname, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        if(preferences.getBoolean("login", false)){
            Intent i = new Intent(getApplicationContext(),IlanEkleActivity.class);
            startActivity(i);
            finish();
        }else {
            edtuserid = (EditText) findViewById(R.id.input_name);
            edtpass = (EditText) findViewById(R.id.input_password);
            btnlogin = (Button) findViewById(R.id.login2Button);
            //pbbar = (ProgressBar) findViewById(R.id.progressbar2);
//            pbbar.setVisibility(View.GONE);


            TextView registerLink = ((TextView) findViewById(R.id.linkto_register));
            registerLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login2Activity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            final List<User> userList = new ArrayList<>();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            firebaseDatabase.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<HashMap<String, User>> t = new GenericTypeIndicator<HashMap<String, User>>() {};
                    HashMap<String, User> dataset = dataSnapshot.getValue(t);


                    if (dataset != null) {
                        for (String id : dataset.keySet()) {

                            User user = new User();
                            user.setUsername(dataset.get(id).getUsername());
                            user.setPassword(dataset.get(id).getPassword());
                            user.setLocation(dataset.get(id).getLocation());
                            user.setPhone(dataset.get(id).getPhone());
                            user.setTokenid(dataset.get(id).getTokenid());
                            userList.add(user);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    DoLogin doLogin = new DoLogin();
//                    doLogin.execute("");
                    uname = edtuserid.getText().toString();
                    pw = edtpass.getText().toString();
                 //   pbbar.setVisibility(View.VISIBLE);

                    int j=0;
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().toString().equals(uname) && userList.get(i).getPassword().toString().equals(pw)) {

                            String user_name = preferences.getString("username", "");
                            String pass = preferences.getString("password", "");

                            if (user_name.equals(uname) && pass.equals(pw)) {
                                editor.putBoolean("login", true);
                      //          pbbar.setVisibility(View.GONE);
                                Intent i1 = new Intent(getApplicationContext(), IlanEkleActivity.class);
                                startActivity(i1);
                                finish();
                            } else {
                                preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                editor = preferences.edit();
                           //     pbbar.setVisibility(View.GONE);

                                editor.putString("username", uname);
                                editor.putString("password", pw);

                                editor.putBoolean("login", true);
                                editor.commit();
                                Intent i2 = new Intent(getApplicationContext(), IlanEkleActivity.class);
                                startActivity(i2);
                                finish();
                            }
                        } else {
                            j++;
                        }
                    }
                    if(j == userList.size()){
                            edtpass.setText("");
                            edtuserid.setText("");
                            Toast.makeText(Login2Activity.this, "Kullanıcı Adı veya Şifreniz yanlış!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        }
    }


//    public class DoLogin extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//        String userid = edtuserid.getText().toString();
//        String password = edtpass.getText().toString();
//
//
//        @Override
//        protected void onPreExecute() {
//            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//            pbbar.setVisibility(View.GONE);
//            Toast.makeText(Login2Activity.this,r,Toast.LENGTH_SHORT).show();
//
//            if(isSuccess) {
//                Toast.makeText(Login2Activity.this,r,Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//
//        @Override
//        protected String doInBackground(String... params) {
//            if(userid.trim().equals("")|| password.trim().equals(""))
//                z = "Please enter User Id and Password";
//            else
//            {
//                try {
//                    Connection con = connectionClass.CONN();
//                    if (con == null) {
//                        z = "Error in connection with SQL server";
//                    } else {
//                        String query = "select Username from [User] where Password = '"+ password +"'";
//                        Statement stmt = con.createStatement();
//                        ResultSet rs = stmt.executeQuery(query);
////                            int columnCount = rs.getMetaData().getColumnCount();
//
//                        if(rs.next())
//                        {
//                            String lastName = rs.getString("Username");
//                            z = "Login successfull";
//                            z = z + " " + lastName;
//                            isSuccess=true;
//                            if(isSuccess == true){
//                                String uname = preferences.getString("username", "");
//                                String pw = preferences.getString("password", "");
//
//                                if(uname.matches(userid) && pw.matches(password)){
//                                    editor.putBoolean("login", true);
//                                    Intent i = new Intent(getApplicationContext(),IlanEkleActivity.class);
//                                    startActivity(i);
//                                    finish();
//                                }else {
//                                    preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                    editor = preferences.edit();
//
//
//                                    editor.putString("username", userid);
//                                    editor.putString("password", password);
//
//                                    editor.putBoolean("login", true);
//                                    editor.commit();
//                                    Intent i = new Intent(getApplicationContext(), IlanEkleActivity.class);
//                                    startActivity(i);
//                                    finish();
//                                }
//                            }
//                        }
//                        else
//                        {
//                            z = "Kullanıcı Adı veya Şifreniz yanlış!";
//
//                            isSuccess = false;
//                        }
//
//                    }
//                }
//                catch (Exception ex)
//                {
//                    isSuccess = false;
//                    z = "Exceptions burda mi "+ ex;
//                }
//            }
//            return z;
//        }
//    }

}