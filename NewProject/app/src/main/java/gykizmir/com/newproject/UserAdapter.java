package gykizmir.com.newproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gamze on 1/13/2019.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> userList;
    private LayoutInflater inflater;


    public UserAdapter(Context context, List list) {
        this.context = context;
        this.userList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        User user = userList.get(position);
        holder.isim.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView isim;
        public ImageView resim;
        private DatabaseReference dbRef;

        public ViewHolder(View view){
            super(view);
            isim = (TextView) view.findViewById(R.id.userName);
            resim = (ImageView) view.findViewById(R.id.userImg);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        final User selectedUser = userList.get(position);
                        Toast.makeText(context, "Secilen kullanici: "+ selectedUser.getName(), Toast.LENGTH_LONG).show();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("Degistir");
                        alertDialog.setMessage("Yeni degeri girin");
                        final EditText input = new EditText(context);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        alertDialog.setView(input);

                        alertDialog.setPositiveButton("Onayla",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

// value su o toast icinde gosterilen deger olanin key i ni bul. onun valuesunu girilen degeri setle

                                        dbRef = FirebaseDatabase.getInstance().getReference("users");

                                        dbRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                                    User u = dataSnapshot1.getValue(User.class);
                                                    if(u.getName().equals(selectedUser.getName())){
                                                             dbRef.child(dataSnapshot1.getKey()).child("name").setValue(input.getText().toString());

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                });

                        alertDialog.setNegativeButton("Iptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        alertDialog.show();

                        FirebaseDatabase.getInstance().getReference("users").child("gamze").child("name").setValue("Gamzecik");

                    }
                }
            });
        }

    }
}
