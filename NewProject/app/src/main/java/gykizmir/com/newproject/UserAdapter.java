package gykizmir.com.newproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        public ViewHolder(View view){
            super(view);
            isim = (TextView) view.findViewById(R.id.userName);
            resim = (ImageView) view.findViewById(R.id.userImg);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        User selectedUser = userList.get(position);
                        Toast.makeText(context, "Secilen kullanici: "+ selectedUser.getName(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
