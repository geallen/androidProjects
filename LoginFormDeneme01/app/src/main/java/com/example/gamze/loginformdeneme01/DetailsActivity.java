package com.example.gamze.loginformdeneme01;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailsActivity extends NavigationDrawerActivity {

    private String[] times = {"08 09 arası", "09 10 arası", "11 12 arası", "12 13 arası", "13 14 arası","14 15 arası", "15 16 arası", "16 17 arası","17 18 arası", "18 19 arası", "19 20 arası", "20 21 arası", "22 23 arası", "23 00 arası", "01 02 arası","02 03 arası", "03 04 arası", "04 05 arası", "05 06 arası" , "06 07 arası", "07 08 arası"};
    Button timeButton, completeBtn, cancelBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String postId, uname, password, upw, token;
    Post post;
    String url = "http://10.0.2.2/loginform01/send_notification.php";
    private static final DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_details);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_details, null, false);
        drawer.addView(contentView, 0);
        final List<User> userList = new ArrayList<>();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        TextView contentTw = ((TextView) findViewById(R.id.menucontent));
        TextView dateTw = ((TextView) findViewById(R.id.date));
        TextView ownerNameTw = ((TextView) findViewById(R.id.ownerName));
        final TextView ownerPhoneTw = ((TextView) findViewById(R.id.ownerPhone));
        ImageView phoneIcon = ((ImageView) findViewById(R.id.phoneIcon));
        final TextView ownerLocationTw = ((TextView) findViewById(R.id.ownerLocation));
        ImageView mapImage =  ((ImageView) findViewById(R.id.imageView));
        timeButton = (Button) findViewById(R.id.timeSelectBtn);
        completeBtn = (Button) findViewById(R.id.completeBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn2);
        final TextView tt = (TextView) findViewById(R.id.tt);
        tt.setVisibility(View.INVISIBLE);
        Intent i = getIntent();
        post= (Post) i.getSerializableExtra("postDetailsId");
        postId = post.getAddingdate();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        uname = preferences.getString("username", "");
        password = preferences.getString("password", "");
        upw = uname + password;
        contentTw.setText(post.getMenucontent());
        String datee = post.getAddingdate().toString();

        datee = datee.substring(0, datee.length() - 2);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        try {
             date = format.parse(datee);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        String formattedDate = format2.format(date);

        dateTw.setText(formattedDate);
        ownerNameTw.setText(post.getSellerkey());
        ownerPhoneTw.setText(post.getOwnerphone());
        ownerLocationTw.setText(post.getOwnerlocation());
        token = post.getOwnertoken();

        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(DetailsActivity.this);
        builderSingle.setTitle("Alacağınız Saati Seçiniz");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailsActivity.this, android.R.layout.select_dialog_singlechoice);
        for (int j =0; j< times.length; j++) {
            arrayAdapter.add(times[j]);
        }
        builderSingle.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                timeButton.setText(strName);
            }
        });


        ownerPhoneTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ownerPhoneTw.equals("")){
                    Toast.makeText(DetailsActivity.this, "Telefon numarası eklenmemiş", Toast.LENGTH_SHORT).show();
                }else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ownerPhoneTw.getText().toString()));

                    if (ActivityCompat.checkSelfPermission(DetailsActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                }
            }
        });

        phoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ownerPhoneTw.equals("")) {
                    Toast.makeText(DetailsActivity.this, "Telefon numarası eklenmemiş", Toast.LENGTH_SHORT).show();
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ownerPhoneTw.getText().toString()));

                    if (ActivityCompat.checkSelfPermission(DetailsActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                }
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderSingle.show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, ShowPostsActivity.class);
                startActivity(i);
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference dbRef = firebaseDatabase.getReference("orders");
                String primaryKey = postId;
                String timeButtonTxt = timeButton.getText().toString();
                try {
                    dbRef.child(primaryKey).child("status").setValue("2");
                    dbRef.child(primaryKey).child("buyerid").setValue(upw);
                    dbRef.child(primaryKey).child("takingtime").setValue(timeButtonTxt);

                    Toast.makeText(DetailsActivity.this, "İsteğiniz Gönderildi!", Toast.LENGTH_SHORT).show();

                    Intent in2 = new Intent(DetailsActivity.this, ShowPostsActivity.class);
                    startActivity(in2);

                    // urun sahibine bildirim gonderme

//                    FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance();
//
//                    firebaseDb.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            GenericTypeIndicator<HashMap<String, User>> t = new GenericTypeIndicator<HashMap<String, User>>() {};
//                            HashMap<String, User> dataset = dataSnapshot.getValue(t);
//
//                            if (dataset != null) {
//                                for (String id : dataset.keySet()) {
//
//                                    User user = new User();
//                                    user.setUsername(dataset.get(id).getUsername());
//                                    user.setPassword(dataset.get(id).getPassword());
//                                    user.setLocation(dataset.get(id).getLocation());
//                                    user.setPhone(dataset.get(id).getPhone());
//                                    user.setToken(dataset.get(id).getToken());
//                                    userList.add(user);
//                                }
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                    for (int j = 0; j < userList.size(); j++) {
//                        if ((userList.get(j).getUsername().toString() +userList.get(j).getPassword().toString()).equals(post.getSellerkey().toString())) {
//
//                            token = userList.get(j).getToken().toString();
//
//                        }}

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("fcm_token", token);
                            params.put("user_id", upw);


                            return params;
                        }
                    };

                    MySingleton.getmInstance(DetailsActivity.this).addToRequestqueue(stringRequest);

                }catch (Exception ex){
                    Toast.makeText(DetailsActivity.this, "Hata olustu:"+ ex, Toast.LENGTH_SHORT).show();
                }


            }
        });

        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, MapsActivity.class);
                intent.putExtra("address", ownerLocationTw.getText().toString());
                startActivity(intent);
            }
        });
    }

//    public class GetItem extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//        String timeButtonTxt = timeButton.getText().toString();
//
//
//        @Override
//        protected void onPreExecute() {
////            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//           // pbbar.setVisibility(View.GONE);
//            // isSuccess should be false if there is no mistake. should go else.
//            Boolean x = isSuccess;
//            if(isSuccess) {
//                Intent intent = new Intent(DetailsActivity.this, ShowPostsActivity.class);
//                startActivity(intent);
//                Toast.makeText(DetailsActivity.this,r,Toast.LENGTH_SHORT).show();
//            }else{
//                Intent intent1 = new Intent(DetailsActivity.this, DetailsActivity.class);
//                startActivity(intent1);
//                Toast.makeText(DetailsActivity.this,r,Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String uname = preferences.getString("username", "");
//            String password = preferences.getString("password", "");
//
//
//            int uId = 0;
//            if(timeButtonTxt.equals("Seçiniz"))
//                z = "Alacağınız saati seçiniz!";
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
//                            String query1 = "UPDATE [SellingItem] SET BuyerId = '"+ uId +"', TakingTime = '" + timeButtonTxt + "' WHERE SellingId = '" + postId +"'";
//                            Statement stmt1 = con.createStatement();
//                            stmt1.executeUpdate(query1);
//                            isSuccess=true;
//                            z = "Siparişiniz Eklendi!";
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
