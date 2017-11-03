package com.example.gamze.loginformdeneme01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class ShowMyNotificationsActivity extends AppCompatActivity {

    TextView notificationContent;
    Button approveBtn, cancelBtn;
    String txtNotif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_notifications);

        notificationContent = (TextView) findViewById(R.id.notif_content);
        approveBtn = (Button) findViewById(R.id.approveBtn);
        cancelBtn = (Button) findViewById(R.id.cancelNotifBtn);

        txtNotif = "";

        notificationContent.setText(txtNotif);

    }
}
