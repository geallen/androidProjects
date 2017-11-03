package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gamze on 8/3/2017.
 */

public class MyPostAdapter extends BaseAdapter {

    private  List<Post> postList;
    private LayoutInflater inflater;
    private Context context;

    public MyPostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.postList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View resultView;
        resultView = inflater.inflate(R.layout.mypost_item, null);
        TextView menu = (TextView) resultView.findViewById(R.id.mymenu);
        TextView menucost = (TextView) resultView.findViewById(R.id.my_menu_cost);

        Post p = (Post) getItem(i);

        menu.setText(p.getMenucontent());
        menucost.setText(p.getCost());

        return resultView;
    }
}
