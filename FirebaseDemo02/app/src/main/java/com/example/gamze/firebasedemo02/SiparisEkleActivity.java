package com.example.gamze.firebasedemo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SiparisEkleActivity extends AppCompatActivity {

    EditText siparisIcerigi, siparisFiyati;
    Button siparisiEkle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ekle);

        siparisIcerigi = (EditText)findViewById(R.id.siparisIcerik);
        siparisFiyati = (EditText) findViewById(R.id.siparisFiyati);

        siparisiEkle = (Button) findViewById(R.id.siparisiEkle);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        siparisiEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference siparisEkleRef = database.getReference("siparis");
                String primaryKey = siparisIcerigi.getText().toString()+ siparisFiyati.getText().toString();

                siparisEkleRef.child(primaryKey).child("siparisIcerigi").setValue(siparisIcerigi.getText().toString());
                siparisEkleRef.child(primaryKey).child("siparisFiyati").setValue(siparisFiyati.getText().toString());
              //  siparisEkleRef.child(primaryKey).child("siparisSahibi").setValue("Gamze");

                siparisIcerigi.setText("");
                siparisFiyati.setText("");
                Toast.makeText(SiparisEkleActivity.this, "Siparis Eklendi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
