package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Gamze on 7/23/2017.
 */

public class PostAdapter extends BaseAdapter {

    private List<Post> postList;
    private LayoutInflater inflater;
    private Context context;

    public PostAdapter(List<Post> postList, Context context) {
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

        resultView = inflater.inflate(R.layout.post_item, null);
        TextView menuTw = ((TextView) resultView.findViewById(R.id.menu));
        TextView costTw = ((TextView) resultView.findViewById(R.id.cost));
      //  TextView dateTw = ((TextView) resultView.findViewById(R.id.date));
        TextView ownerTw = ((TextView) resultView.findViewById(R.id.owner));

        Post p = (Post) getItem(i);

        menuTw.setText(p.getMenucontent());
        costTw.setText(p.getCost());
//        //dateTw.setText(p.getDate());
        ownerTw.setText(p.getSellerkey());

        return resultView;
    }
}
