package com.example.gamze.loginformdeneme01;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDetailsActivity extends NavigationDrawerActivity {

    String postId, uname, password, upw, token;
    Post post;
    Button approveBtn, disapproveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_order_details);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_order_details, null, false);
        drawer.addView(contentView, 0);
        Intent i = getIntent();
        post= (Post) i.getSerializableExtra("postDetailsId");
        postId = post.getAddingdate();

        TextView contentTw = ((TextView) findViewById(R.id.menucontent));
        TextView dateTw = ((TextView) findViewById(R.id.date));
        TextView ownerNameTw = ((TextView) findViewById(R.id.ownerName));
        final TextView ownerPhoneTw = ((TextView) findViewById(R.id.ownerPhone));
        ImageView phoneIcon = ((ImageView) findViewById(R.id.phoneIcon));
        TextView takingTime = (TextView) findViewById(R.id.takingTimeTxt);


        approveBtn = (Button) findViewById(R.id.completeBtn);
        disapproveBtn = (Button) findViewById(R.id.cancelBtn);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String taking_time = post.getAddingdate().toString();

        taking_time = taking_time.substring(0, taking_time.length() - 2);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        try {
            date = format.parse(taking_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        String formattedDate = format2.format(date);


        contentTw.setText(post.getMenucontent());
        dateTw.setText(formattedDate);
        ownerNameTw.setText(post.getSellerkey());
        ownerPhoneTw.setText(post.getOwnerphone());
        takingTime.setText(post.getTakingtime());

        phoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ownerPhoneTw.equals("")) {
                    Toast.makeText(OrderDetailsActivity.this, "Telefon numarası eklenmemiş", Toast.LENGTH_SHORT).show();
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ownerPhoneTw.getText().toString()));

                    if (ActivityCompat.checkSelfPermission(OrderDetailsActivity.this,
                            android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                }
            }
        });

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference dbRef = firebaseDatabase.getReference("orders");
                String primaryKey = postId;
                try {
                    dbRef.child(primaryKey).child("status").setValue("3");

                    Toast.makeText(OrderDetailsActivity.this, "Siparişi Onayladınız!", Toast.LENGTH_SHORT).show();

                    Intent in2 = new Intent(OrderDetailsActivity.this, ShowPostsActivity.class);
                    startActivity(in2);
                }catch (Exception ex){
                Toast.makeText(OrderDetailsActivity.this, "Hata olustu:"+ ex, Toast.LENGTH_SHORT).show();
            }
            }
        });

        disapproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OrderDetailsActivity.this, "Siparişi Onaylamadınız!", Toast.LENGTH_SHORT).show();
                Intent in2 = new Intent(OrderDetailsActivity.this, ShowPostsActivity.class);
                startActivity(in2);
            }
        });
    }
}
