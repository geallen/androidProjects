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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowMyPostsActivity extends NavigationDrawerActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String uname, password, upw;
    ListView mypostLw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     setContentView(R.layout.activity_show_my_posts);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_show_my_posts, null, false);
        drawer.addView(contentView, 0);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        uname = preferences.getString("username", "");
        password = preferences.getString("password", "");
        upw = uname + password;

        mypostLw = (ListView) findViewById(R.id.myposts_list_view);

        final List<Post> myPostList = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef_add_user = database.getReference("orders");

        Query queryRef = dbRef_add_user.orderByChild("sellerkey").equalTo(upw);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Post>> t = new GenericTypeIndicator<HashMap<String, Post>>() {};
                HashMap<String, Post> dataset = dataSnapshot.getValue(t);
                if (dataset != null) {
                    for (String id : dataset.keySet()) {
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
                        myPostList.add(p);
                    }
                }

                MyPostsAdapter myPostAdapter = new MyPostsAdapter(myPostList, ShowMyPostsActivity.this);
                mypostLw.setAdapter(myPostAdapter);

                mypostLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Post post = myPostList.get(i);
                    Intent intent = new Intent(ShowMyPostsActivity.this, UpdatePostActivity.class);
                    intent.putExtra("postDetailsId", post);
                    startActivity(intent);
                }
            });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public class ShowMyPost extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//        @Override
//        protected void onPreExecute() {}
//
//        @Override
//        protected void onPostExecute(String r) {
//            Toast.makeText(ShowMyPostsActivity.this,r,Toast.LENGTH_SHORT).show();
//            ListView postListView = ((ListView) findViewById(R.id.myposts_list_view));
//            PostAdapter adapter = new PostAdapter(personList, ShowMyPostsActivity.this);
//            postListView.setAdapter(adapter);
//
//            postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Post post = personList.get(i);
//                    Intent intent = new Intent(ShowMyPostsActivity.this, UpdatePostActivity.class);
//                    intent.putExtra("postDetailsId", post);
//                    startActivity(intent);
//                }
//            });
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String menu, owner, date, cost, ownerPhone, ownerLocation;
//            int menuId;
//
//            try {
//                Connection con = connectionClass.CONN();
//                if (con == null) {
//                    z = "Error in connection with SQL server";
//                } else {
//                    String query = "SELECT S.[MenuContent], S.[Cost],S.[AddingDate], U.[Username], U.[Phone], U.[Location],S.[SellingId] FROM [DBAndroid1].[dbo].[SellingItem] S LEFT JOIN [DBAndroid1].[dbo].[User] U ON U.UserId = S.SellerId WHERE U.Username = '" + unamee +"' AND U.Password= '"+ password+"'";
//                    Statement stmt = con.createStatement();
//                    ResultSet rs = stmt.executeQuery(query);
//                    while(rs.next()) {
//
//                        menu = rs.getString(1);
//                        cost = rs.getString(2);
//                        date = rs.getString(3);
//                        owner = rs.getString(4);
//                        ownerPhone = rs.getString(5);
//                        ownerLocation = rs.getString(6);
//                        menuId = rs.getInt(7);
//
//                        personList.add(new Post(menu, cost, date, owner, ownerPhone, ownerLocation, menuId));
//                        isSuccess = true;
//                        z = "Ilanlarım Gösteriliyor!";
//                    }
//                }
//            }
//            catch (Exception ex)
//            {
//                isSuccess = false;
//                z = "Hata meydana geldi: "+ ex;
//            }
//
//            return z;
//        }
//    }
}
