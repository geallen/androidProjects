package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gamze on 8/10/2017.
 */

public class MyPostsAdapter extends BaseAdapter {
    private List<Post> mypostList;
    private LayoutInflater inflater;
    private Context context;

    public MyPostsAdapter(List<Post> mypostList, Context context) {
        this.mypostList = mypostList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.mypostList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.mypostList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View resultView;
        resultView  = inflater.inflate(R.layout.mypost_item, null);
        TextView menuTw = (TextView) resultView.findViewById(R.id.mymenu);
        TextView costTw = (TextView) resultView.findViewById(R.id.mymenucost);

        Post p = (Post) getItem(i);

        menuTw.setText(p.getMenucontent());

        costTw.setText(p.getCost());
        return resultView;
    }
}
