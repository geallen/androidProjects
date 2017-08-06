package com.example.gamze.firebasedemo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    List<User> userList = new ArrayList<>();
    TextView userTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        userTxt = (TextView) findViewById(R.id.userTxt);

        final ListView usersLW = (ListView) findViewById(R.id.user_list);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, User>> t = new GenericTypeIndicator<HashMap<String, User>>(){};
                HashMap<String, User> dataset = dataSnapshot.getValue(t);
                if (dataset != null) {
                    for (String id : dataset.keySet()) {

                        User user = new User();
                        user.setUsername(dataset.get(id).getUsername());
                        user.setPassword(dataset.get(id).getPassword());
                        userList.add(user);
                    }
                }

                UserAdapter adapter = new UserAdapter(userList, UsersActivity.this);
                usersLW.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
