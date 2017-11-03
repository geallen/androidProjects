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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class IlanEkleActivity extends NavigationDrawerActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String uname, upw, user_phone, user_location, user_token;
    String password;
    Button btnResimEkle, btnFinishPost;
    EditText twSellingItems, twItemCost;
    ProgressBar pbbar;
    private static final DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_ilan_ekle);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_ilan_ekle, null, false);
        drawer.addView(contentView, 0);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        uname = preferences.getString("username", "");
        password = preferences.getString("password", "");
        pbbar = (ProgressBar) findViewById(R.id.pbbar_ilan_ekle);
        pbbar.setVisibility(View.GONE);

        upw = uname + password;

//        btnResimEkle = ((Button) findViewById(R.id.add_image));
//
//        btnResimEkle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editor.clear();
//                editor.commit();
//
//                Intent i = new Intent(getApplicationContext(), Login2Activity.class);
//                startActivity(i);
//                finish();
//
//            }
//        });

        twSellingItems = ((EditText) findViewById(R.id.selling_items));

        twItemCost = ((EditText) findViewById(R.id.items_cost));

        btnFinishPost = ((Button) findViewById(R.id.finish_post));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference dbref = database.getReference("users");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, User>> t = new GenericTypeIndicator<HashMap<String, User>>() {};
                HashMap<String, User> dataset = dataSnapshot.getValue(t);
                if (dataset != null) {
                    for (String id : dataset.keySet()) {
                        if(dataset.get(id).getUsername().toString().equals(uname) && dataset.get(id).getPassword().toString().equals(password)){
                            user_phone = dataset.get(id).getPhone().toString();
                            user_location = dataset.get(id).getLocation().toString();
                            user_token = dataset.get(id).getTokenid().toString();
                            break;

                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnFinishPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (twSellingItems.getText().toString().equals("") || twItemCost.getText().toString().equals("") || twSellingItems.getText().toString().equals(" ") || twItemCost.getText().toString().equals(" ")) {
                    Toast.makeText(IlanEkleActivity.this, "Alanlar boş geçilemez!", Toast.LENGTH_SHORT).show();
                } else {

                    pbbar.setVisibility(View.VISIBLE);
                    DatabaseReference dbRef_add_post = database.getReference("orders");

                    Date date = new Date();

                    String primaryKey = sdf.format(date);

                    dbRef_add_post.child(primaryKey).child("menucontent").setValue(twSellingItems.getText().toString());
                    dbRef_add_post.child(primaryKey).child("sellerkey").setValue(uname + password);
                    dbRef_add_post.child(primaryKey).child("cost").setValue(twItemCost.getText().toString());
                    dbRef_add_post.child(primaryKey).child("addingdate").setValue(primaryKey);
                    dbRef_add_post.child(primaryKey).child("status").setValue("0");
                    // status 0 means it added new.
                    dbRef_add_post.child(primaryKey).child("buyerid").setValue("0");
                    // buyerid 0 means no one bought it
                    dbRef_add_post.child(primaryKey).child("takingtime").setValue("0");
                    // takingtime 0 means it is not bought yet
                    dbRef_add_post.child(primaryKey).child("ownerphone").setValue(user_phone);
                    dbRef_add_post.child(primaryKey).child("ownerlocation").setValue(user_location);
                    dbRef_add_post.child(primaryKey).child("ownertoken").setValue(user_token);
                    twSellingItems.setText("");
                    twItemCost.setText("");
                    Toast.makeText(IlanEkleActivity.this, "Ürünleriniz Eklendi!", Toast.LENGTH_SHORT).show();
                    //pbbar.setVisibility(View.INVISIBLE);
                    closeProgressBar();
                }
            }
        });





    }
    public void closeProgressBar() {
        pbbar.setVisibility(View.GONE);
    }

//    public class AddPost extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//        String items = twSellingItems.getText().toString();
//        String cost = twItemCost.getText().toString();
//
//        @Override
//        protected void onPreExecute() {
//            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//            pbbar.setVisibility(View.GONE);
//            // isSuccess should be false if there is no mistake. should go else.
//            Boolean x = isSuccess;
//            if(isSuccess) {
////                Intent intent = new Intent(IlanEkleActivity.this, ShowPostsActivity.class);
////                startActivity(intent);
//                Toast.makeText(IlanEkleActivity.this,r,Toast.LENGTH_SHORT).show();
//            }else{
////                Intent intent1 = new Intent(IlanEkleActivity.this, IlanEkleActivity.class);
////                startActivity(intent1);
//                Toast.makeText(IlanEkleActivity.this,r,Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            int uId = 0;
//            if(items.trim().equals("")|| cost.trim().equals(""))
//                z = "Menu ve Fiyat alanlarını boş geçemezsiniz!";
//            else
//            {
//                try {
//                    Connection con = connectionClass.CONN();
//                    if (con == null) {
//                        z = "Error in connection with SQL server";
//                    } else {
//                        String query = "select UserId from [User] where Username = '"+ uname +"' AND  Password = '"+ password +"'";
//                        Statement stmt = con.createStatement();
//                        ResultSet rs = stmt.executeQuery(query);
//                        if(rs.next()) {
//                            uId = rs.getInt(1);
//                            String query1 = "insert into [SellingItem](MenuContent, SellerId, Cost) values('" + items + "','" + uId + "','" + cost + "')";
//                            Statement stmt1 = con.createStatement();
//                            stmt1.executeUpdate(query1);
//                            isSuccess=true;
//                            z = "Urunleriniz Eklendi!";
//                        }
//                    }
//                }
//                catch (Exception ex)
//                {
//                    isSuccess = false;
//                    z = "Hata meydana geldi: "+ ex;
//                }
//            }
//            return z;
//        }
//    }
}

