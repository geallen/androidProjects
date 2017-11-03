package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class ShowPostsActivity extends NavigationDrawerActivity {

    List<Post> personList = new ArrayList<>();
    ListView postLw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_posts);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_show_posts, null, false);
        drawer.addView(contentView, 0);

       /* personList.add(new Post("Helen","26.07.1994","7","89","ss","sds", 100));
        personList.add(new Post("Joe", "1.1.2019", "gsh", "sdh","dsds", "frf",214));
        personList.add(new Post("George", "2.2.2019","dds","we","fddf","edfc", 899));
        personList.add(new Post("Miranda", "3.3.2019","qdwq","sdds","fddf","edfc",543));
        personList.add(new Post("John", "4.4.2019","wew","df","fddf","edfc",546));
        personList.add(new Post("Steve", "6.6.2019","ddfd","er","fddf","edfc", 547));
*/

        postLw = (ListView) findViewById(R.id.post_list_view);

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

                PostAdapter myPostAdapter = new PostAdapter(postList, ShowPostsActivity.this);
                postLw.setAdapter(myPostAdapter);

                postLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Post post = postList.get(i);
                        Intent intent = new Intent(ShowPostsActivity.this, DetailsActivity.class);
                        intent.putExtra("postDetailsId", post);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        ShowPost showPost = new ShowPost();
//        showPost.execute("");
//        ListView postListView = ((ListView) findViewById(R.id.post_list_view));
//        PostAdapter adapter = new PostAdapter(personList, this);
//        postListView.setAdapter(adapter);
//
//        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Post post = personList.get(i);
//                Intent intent = new Intent(ShowPostsActivity.this, DetailsActivity.class);
//                intent.putExtra("postDetails", post);
//                startActivity(intent);
//            }
//        });

    }

//    public class ShowPost extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//        @Override
//        protected void onPreExecute() {}
//
//        @Override
//        protected void onPostExecute(String r) {
//            Toast.makeText(ShowPostsActivity.this,r,Toast.LENGTH_SHORT).show();
//            ListView postListView = ((ListView) findViewById(R.id.post_list_view));
//            PostAdapter adapter = new PostAdapter(personList, ShowPostsActivity.this);
//            postListView.setAdapter(adapter);
//
//            postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Post post = personList.get(i);
//                    Intent intent = new Intent(ShowPostsActivity.this, DetailsActivity.class);
//                    intent.putExtra("postDetails", post);
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
//                    String query = "SELECT S.[MenuContent], S.[Cost],S.[AddingDate], U.[Username], U.[Phone], U.[Location],S.[SellingId] FROM [DBAndroid1].[dbo].[SellingItem] S LEFT JOIN [DBAndroid1].[dbo].[User] U ON U.UserId = S.SellerId";
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
//                //        personList.add(new Post(menu, cost, date, owner, ownerPhone, ownerLocation, menuId));
//                        isSuccess = true;
//                        z = "Ilanlar Gosteriliyor!";
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
