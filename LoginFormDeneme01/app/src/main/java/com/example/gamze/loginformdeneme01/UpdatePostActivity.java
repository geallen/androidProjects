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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdatePostActivity extends NavigationDrawerActivity {

    EditText menuContent, menuCost;
    Button changeButton, deleteButton, cancelButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Post post;
    String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_update_post, null, false);
        drawer.addView(contentView, 0);

        menuContent = (EditText) findViewById(R.id.my_menu_content);
        menuCost = (EditText)findViewById(R.id.my_menu_cost);

        changeButton = (Button) findViewById(R.id.btn_changePost);
        cancelButton = (Button) findViewById(R.id.cancelPostBtn);
        deleteButton = (Button) findViewById(R.id.deletePostBtn);
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Intent i = getIntent();
        post= (Post) i.getSerializableExtra("postDetailsId");

        postId = post.getAddingdate();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();



        menuContent.setText(post.getMenucontent());
        menuCost.setText(post.getCost());

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRef_add_user = firebaseDatabase.getReference("orders");
                String primaryKey = postId;

                try {
                    dbRef_add_user.child(primaryKey).child("menucontent").setValue(menuContent.getText().toString());
                    dbRef_add_user.child(primaryKey).child("cost").setValue(menuCost.getText().toString());
                    Toast.makeText(UpdatePostActivity.this, "İlanınız Güncellendi!", Toast.LENGTH_SHORT).show();

                    Intent in2 = new Intent(UpdatePostActivity.this, ShowMyPostsActivity.class);
                    startActivity(in2);
                }catch (Exception ex){
                    Toast.makeText(UpdatePostActivity.this, "Hata olustu:"+ ex, Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(UpdatePostActivity.this, ShowMyPostsActivity.class);
                startActivity(in1);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference dbRef_add_user = firebaseDatabase.getReference("orders");
                String primaryKey = postId;

                try {
                    dbRef_add_user.child(primaryKey).child("status").setValue("1");
                    Toast.makeText(UpdatePostActivity.this, "Ilanınız Silindi!", Toast.LENGTH_SHORT).show();

                    Intent in2 = new Intent(UpdatePostActivity.this, ShowPostsActivity.class);
                    startActivity(in2);
                }catch (Exception ex){
                    Toast.makeText(UpdatePostActivity.this, "Silme sırasında hata :"+ ex, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


//    public class UpdatePost extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//        String menucontentTxt = menuContent.getText().toString();
//        String menuCostTxt = menuCost.getText().toString();
//
//        @Override
//        protected void onPreExecute() {
////            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//            // pbbar.setVisibility(View.GONE);
//            // isSuccess should be false if there is no mistake. should go else.
//            Boolean x = isSuccess;
//            if(isSuccess) {
//                Intent intent = new Intent(UpdatePostActivity.this, ShowMyPostsActivity.class);
//                startActivity(intent);
//                Toast.makeText(UpdatePostActivity.this,r,Toast.LENGTH_SHORT).show();
//            }else{
//                Intent intent1 = new Intent(UpdatePostActivity.this, UpdatePostActivity.class);
//                startActivity(intent1);
//                Toast.makeText(UpdatePostActivity.this,r,Toast.LENGTH_SHORT).show();
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
//
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
//                            String query1 = "UPDATE [SellingItem] SET MenuContent = '"+ menucontentTxt +"', Cost = '" + menuCostTxt + "' WHERE SellerId = '" + uId +"' AND SellingId = '"+ postId+"'";
//                            Statement stmt1 = con.createStatement();
//                            stmt1.executeUpdate(query1);
//                            isSuccess=true;
//                            z = "Siparişiniz Güncellendi!";
//                        }
//                    }
//                }
//                catch (Exception ex)
//                {
//                    isSuccess = false;
//                    z = "Hata meydana geldi: "+ ex;
//                }
//
//            return z;
//        }
//    }
//
//    public class DeletePost extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//
//        @Override
//        protected void onPreExecute() {
////            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//            // pbbar.setVisibility(View.GONE);
//            // isSuccess should be false if there is no mistake. should go else.
//            Boolean x = isSuccess;
//            if(isSuccess) {
//                Intent intent = new Intent(UpdatePostActivity.this, ShowMyPostsActivity.class);
//                startActivity(intent);
//                Toast.makeText(UpdatePostActivity.this,r,Toast.LENGTH_SHORT).show();
//            }else{
//                Intent intent1 = new Intent(UpdatePostActivity.this, UpdatePostActivity.class);
//                startActivity(intent1);
//                Toast.makeText(UpdatePostActivity.this,r,Toast.LENGTH_SHORT).show();
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
//
//            try {
//                Connection con = connectionClass.CONN();
//                if (con == null) {
//                    z = "Error in connection with SQL server";
//                } else {
//                    String query = "select UserId from [User] where Username = '"+ uname +"' AND  Password = '"+ password +"'";
//                    Statement stmt = con.createStatement();
//                    ResultSet rs = stmt.executeQuery(query);
//                    if(rs.next()) {
//                        uId = rs.getInt(1);
//                        String query1 = "UPDATE [SellingItem] SET Status = '2' WHERE SellerId = '" + uId +"' AND SellingId = '"+ postId+"'";
//                        Statement stmt1 = con.createStatement();
//                        stmt1.executeUpdate(query1);
//                        isSuccess=true;
//                        z = "Siparişiniz Silindi!";
//                    }
//                }
//            }
//            catch (Exception ex)
//            {
//                isSuccess = false;
//                z = "Silme sirasinda hata meydana geldi: "+ ex;
//            }
//
//            return z;
//        }
//    }
}
