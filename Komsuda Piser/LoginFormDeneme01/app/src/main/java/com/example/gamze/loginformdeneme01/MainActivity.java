package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText useridText, passwordText, locationText, phoneNumberText;
    TextView link_to_login;
    Button loginButton;
    ProgressBar pbbar;

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef1 = database.getReference("db");
        dbRef1.setValue("Food Order App Db");




        final List<User> userList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, User>> t = new GenericTypeIndicator<HashMap<String, User>>() {};
                HashMap<String, User> dataset = dataSnapshot.getValue(t);

                if (dataset != null) {
                    for (String id : dataset.keySet()) {

                        String us = dataset.get(id).getUsername().toString();
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

        //connectionClass = new ConnectionClass();
        useridText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumber);
        locationText = (EditText) findViewById(R.id.location);
        loginButton = (Button) findViewById(R.id.btn_Login);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        link_to_login = (TextView) findViewById(R.id.tw_to_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                token = sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");
              //  Toast.makeText(MainActivity.this, token , Toast.LENGTH_SHORT).show();

                // DoRegister  doRegister = new DoRegister();
               // doRegister.execute("");
                String uname = useridText.getText().toString();
                String pw = passwordText.getText().toString();
                int j = 0;
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getUsername().toString().equals(uname) && userList.get(i).getPassword().toString().equals(pw)) {
                        useridText.setText("");
                        passwordText.setText("");
                        phoneNumberText.setText("");
                        locationText.setText("");
                        Toast.makeText(MainActivity.this, "Farklı kullanıcı adı ve şifre deneyiniz.", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        j++;
                    }
                }
                if (j == userList.size()) {
                    DatabaseReference dbRef_add_user = database.getReference("users");
                    String primaryKey = useridText.getText().toString()+passwordText.getText().toString();

                    dbRef_add_user.child(primaryKey).child("username").setValue(useridText.getText().toString());
                    dbRef_add_user.child(primaryKey).child("password").setValue(passwordText.getText().toString());
                    dbRef_add_user.child(primaryKey).child("location").setValue(locationText.getText().toString());
                    dbRef_add_user.child(primaryKey).child("phone").setValue(phoneNumberText.getText().toString());
                    dbRef_add_user.child(primaryKey).child("tokenid").setValue(token);

                    useridText.setText("");
                    passwordText.setText("");
                    locationText.setText("");
                    phoneNumberText.setText("");
                    Toast.makeText(MainActivity.this, "Başarı ile kayıt oldunuz!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Login2Activity.class);
                    startActivity(i);

                }




            }
        });

        link_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login2Activity.class);
                startActivity(i);
            }
        });
    }
//    public class DoRegister extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//        String userid = useridText.getText().toString();
//        String password = passwordText.getText().toString();
//        String location = locationText.getText().toString();
//        String phoneNumber = phoneNumberText.getText().toString();
//
//        @Override
//        protected void onPreExecute() {
//            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//            pbbar.setVisibility(View.GONE);
//            boolean d = isSuccess;
//            if(isSuccess) {
//
//                Intent intent = new Intent(MainActivity.this, Login2Activity.class);
//                startActivity(intent);
//                Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
//            }else{
//                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent1);
//                Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            if(userid.trim().equals("")|| password.trim().equals("") || location.trim().equals(""))
//                z = "Kullanıcı Adı, Şifre ve Adres alanlarını boş geçemezsiniz!";
//            else
//            {
//
//                try {
//                    Connection con = connectionClass.CONN();
//                    if (con == null) {
//                        z = "Error in connection with SQL server";
//                    } else {
//
//                        String query = "select Username from [User] where Password = '" + password+"'";
//                        Statement stmt = con.createStatement();
//                        ResultSet rs = stmt.executeQuery(query);
//
//                        if(rs.next()){
//                            String lastName = rs.getString("Username");
//
//                            if(lastName.equals(userid)){
//                                z = "Farklı kullanıcı adı ve şifre deneyiniz.";
//                                isSuccess = false;
//                            }
//                            else{
//                                String query1 = "insert into [User](Username, Password, Location, Phone) values('" + userid + "','" + password + "','" + location + "','" + phoneNumber+"')";
//                                Statement stmt1 = con.createStatement();
//                                stmt1.executeUpdate(query1);
//                                z = "Başarı ile kayıt oldunuz!";
//                            }
//                        }
//                        else{
//                            String query2 = "insert into [User](Username, Password, Location, Phone) values('" + userid + "','" + password + "','" + location + "','" + phoneNumber+"')";
//                            Statement stmt2 = con.createStatement();
//                            stmt2.executeUpdate(query2);
//                            z = "Başarı ile kayıt oldunuz!";
//                            isSuccess = true;
//                        }
//                    }
//
//                }
//                catch (Exception ex)
//                {
//                    isSuccess = false;
//                    z = "Kayıt sırasında hata meydana geldi: "+ ex;
//                }
//            }
//            return z;
//        }
//
//    }

}
