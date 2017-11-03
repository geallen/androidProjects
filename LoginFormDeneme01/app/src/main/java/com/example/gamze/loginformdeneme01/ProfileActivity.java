package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProfileActivity extends NavigationDrawerActivity {

    EditText password, phoneNumber, location;
    TextView passwTxt, phonTxt, locationTxt;
    Button changeButton, showButton, cancelButton;
  //  ProgressBar pbbar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pwTxt, phoneTxt, locTxt;

    int uId = 0;
    String pw, phoneNum, loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_profile, null, false);
        drawer.addView(contentView, 0);

       // connectionClass = new ConnectionClass();
        passwTxt = (TextView) findViewById(R.id.pwTxt);
        phonTxt = (TextView) findViewById(R.id.phoneTxt);
        locationTxt = (TextView) findViewById(R.id.locationTxt);

       // pbbar = (ProgressBar) findViewById(R.id.pbbar);
       // pbbar.setVisibility(View.GONE);
        password = (EditText) findViewById(R.id.password);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        location = (EditText) findViewById(R.id.location);
        changeButton = (Button) findViewById(R.id.btn_change);
        showButton = (Button) findViewById(R.id.btn_show);
        cancelButton = (Button) findViewById(R.id.cancelBtn);

     //   showButton.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        phoneNumber.setVisibility(View.INVISIBLE);
        location.setVisibility(View.INVISIBLE);
        changeButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        passwTxt.setVisibility(View.INVISIBLE);
        phonTxt.setVisibility(View.INVISIBLE);
        locationTxt.setVisibility(View.INVISIBLE);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();


        final String uname = preferences.getString("username", "");
        final String passwor = preferences.getString("password", "");


        final List<User> userList = new ArrayList<>();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

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
//        try {
//            Connection con = connectionClass.CONN();
//            if (con == null) {
//                Toast.makeText(ProfileActivity.this, "Error in connection with SQL server", Toast.LENGTH_SHORT).show();
//
//            } else {
//                String query = "select UserId,Password, Phone, Location from [User] where Username = '"+ uname +"' AND  Password = '"+ passwor +"'";
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery(query);
//                if(rs.next()) {
//                    uId = rs.getInt(1);
//                    pw = rs.getString(2);
//                    phoneNum = rs.getString(3);
//                    loc = rs.getString(4);
//
//                }
//            }
//        }
//        catch (Exception ex)
//        {
//            Toast.makeText(ProfileActivity.this, "Güncelleme sırasında hata meydana geldi: " + ex, Toast.LENGTH_SHORT).show();
//
//        }
//
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i< userList.size(); i++) {
                    if (userList.get(i).getUsername().toString().equals(uname) && userList.get(i).getPassword().toString().equals(passwor)) {
                        pwTxt = userList.get(i).getPassword().toString();
                        phoneTxt = userList.get(i).getPhone().toString();
                        locTxt = userList.get(i).getLocation().toString();
                        break;
                    }
                }

                showButton.setVisibility(View.GONE);
                password.setText(pwTxt);
                phoneNumber.setText(phoneTxt);
                location.setText(locTxt);

                passwTxt.setVisibility(View.VISIBLE);
                phonTxt.setVisibility(View.VISIBLE);
                locationTxt.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                phoneNumber.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                changeButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
            }
        });






        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UpdateUser updateUser = new UpdateUser();
//                updateUser.execute("");

                DatabaseReference dbRef_add_user = firebaseDatabase.getReference("users");
                String primaryKey = uname+ passwor;

                try {
                    dbRef_add_user.child(primaryKey).child("password").setValue(password.getText().toString());
                    dbRef_add_user.child(primaryKey).child("location").setValue(location.getText().toString());
                    dbRef_add_user.child(primaryKey).child("phone").setValue(phoneNumber.getText().toString());
                    Toast.makeText(ProfileActivity.this, "Değişiklikleriniz başarı ile kaydoldu!", Toast.LENGTH_SHORT).show();

                    Intent in2 = new Intent(ProfileActivity.this, ShowPostsActivity.class);
                    startActivity(in2);
                }catch (Exception ex){
                    Toast.makeText(ProfileActivity.this, "Hata olustu:"+ ex, Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(ProfileActivity.this, ShowPostsActivity.class);
                startActivity(in1);
            }
        });

    }


//    public class UpdateUser extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//
//        String passwordTxt = password.getText().toString();
//        String locationTxt = location.getText().toString();
//        String phoneNumberTxt = phoneNumber.getText().toString();
//
//        @Override
//        protected void onPreExecute() {
//            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//            pbbar.setVisibility(View.GONE);
//            if(r == "Değişiklikleriniz başarı ile kaydoldu!"){
//                password.setVisibility(View.INVISIBLE);
//                phoneNumber.setVisibility(View.INVISIBLE);
//                location.setVisibility(View.INVISIBLE);
//                changeButton.setVisibility(View.INVISIBLE);
//                cancelButton.setVisibility(View.INVISIBLE);
//                showButton.setVisibility(View.VISIBLE);
//            }
//            Toast.makeText(ProfileActivity.this,r,Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            try {
//                Connection con = connectionClass.CONN();
//                if (con == null) {
//                    z = "Error in connection with SQL server";
//                } else {
//
//                        String query1 = "UPDATE [User] SET Password = '"+ passwordTxt +"', Location = '" + locationTxt + "', Phone = '"+  phoneNumberTxt +"' WHERE UserId = '" + uId +"'";
//                        Statement stmt1 = con.createStatement();
//                        stmt1.executeUpdate(query1);
//                        isSuccess=true;
//                        z = "Değişiklikleriniz başarı ile kaydoldu!";
//                    }
//
//            }
//            catch (Exception ex)
//            {
//                isSuccess = false;
//                z = "Güncelleme sırasında hata meydana geldi: "+ ex;
//            }
//
//            return z;
//        }
//
//    }



}
