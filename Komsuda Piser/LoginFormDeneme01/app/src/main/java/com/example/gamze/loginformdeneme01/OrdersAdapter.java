package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Gamze on 9/4/2017.
 */

public class OrdersAdapter extends BaseAdapter {

    private List<Post> ordersList;
    private LayoutInflater inflater;
    private Context context;

    public OrdersAdapter(List<Post> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.ordersList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.ordersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View resultView;
        resultView = inflater.inflate(R.layout.order_item, null);
        TextView menuTxt = (TextView) resultView.findViewById(R.id.menu);
        TextView aliciNameTxt = (TextView) resultView.findViewById(R.id.aliciNameTxt);
        TextView alisSaatTxt = ((TextView) resultView.findViewById(R.id.alisSaatTxt));

        Post p = (Post) getItem(i);

        menuTxt.setText(p.getMenucontent());


        aliciNameTxt.setText(p.getBuyerid());
        alisSaatTxt.setText(p.getTakingtime());

        return resultView;
    }
}
