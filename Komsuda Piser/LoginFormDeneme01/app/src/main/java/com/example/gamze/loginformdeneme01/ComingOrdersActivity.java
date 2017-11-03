package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComingOrdersActivity extends NavigationDrawerActivity {

    ListView ordersLw;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String upw, uname, password;
    Button approveBtn, disapproveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coming_orders);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_coming_orders, null, false);
        drawer.addView(contentView, 0);

        ordersLw = (ListView) findViewById(R.id.orders_list_view);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        uname = preferences.getString("username", "");
        password = preferences.getString("password", "");
        upw = uname + password;

//        approveBtn = (Button) findViewById(R.id.approveBtn);
//        disapproveBtn = (Button) findViewById(R.id.unapproveBtn);
//

        final List<Post> postList = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef_add_user = database.getReference("orders");

        dbRef_add_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Post>> t = new GenericTypeIndicator<HashMap<String, Post>>() {};
                HashMap<String, Post> dataset = dataSnapshot.getValue(t);
                if (dataset != null) {
                    for (String id : dataset.keySet()) {
                        if(dataset.get(id).getSellerkey().toString().equals(upw) && dataset.get(id).getStatus().toString().equals("2")) {
                            Post p = new Post();
                            p.setAddingdate(dataset.get(id).getAddingdate());
                            p.setMenucontent(dataset.get(id).getMenucontent());
                            p.setSellerkey(dataset.get(id).getSellerkey());
                            p.setOwnerlocation(dataset.get(id).getOwnerlocation());
                            p.setOwnerphone(dataset.get(id).getOwnerphone());
                            p.setCost(dataset.get(id).getCost());
                            p.setBuyerid(dataset.get(id).getBuyerid());
                            p.setTakingtime(dataset.get(id).getTakingtime());
                            p.setStatus(dataset.get(id).getStatus());
                            p.setOwnertoken(dataset.get(id).getOwnertoken());
                            postList.add(p);

                        }
                    }
                }


                OrdersAdapter myPostAdapter = new OrdersAdapter(postList, ComingOrdersActivity.this);
                ordersLw.setAdapter(myPostAdapter);


//                ordersLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Post p = postList.get(i);
////                        String bid= p.getBuyerid().toString();
////                        Toast.makeText(ComingOrdersActivity.this, "post detaylari:"+bid, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ComingOrdersActivity.this, UpdatePostActivity.class);
//                        intent.putExtra("postDetailsId", p);
//                        startActivity(intent);
//                    }
//                });
                ordersLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Post p = postList.get(i);
                        Intent intent = new Intent(ComingOrdersActivity.this, OrderDetailsActivity.class);
                        intent.putExtra("postDetailsId", p);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
