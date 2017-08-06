package com.example.gamze.firebasedemo02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gamze on 8/6/2017.
 */

public class UserAdapter extends BaseAdapter {

    private List<User> userList;
    private LayoutInflater inflater;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.userList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View resultView;
        resultView = inflater.inflate(R.layout.each_user_item, null);
        TextView u_name = ((TextView) resultView.findViewById(R.id.u_name));
        TextView u_pw = ((TextView) resultView.findViewById(R.id.u_pw));

        User u = (User) getItem(i);
        u_name.setText(u.getUsername());
        u_pw.setText(u.getPassword());

        return resultView;
    }
}
