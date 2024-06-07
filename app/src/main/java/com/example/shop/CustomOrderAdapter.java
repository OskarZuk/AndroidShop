package com.example.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomOrderAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> orders;

    public CustomOrderAdapter(Context context, ArrayList<String> orders) {
        super(context, R.layout.list_item, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView productNameTextView = rowView.findViewById(R.id.textProductName);
        TextView priceTextView = rowView.findViewById(R.id.textPrice);
        TextView emailTextView = rowView.findViewById(R.id.textEmail);

        String order = orders.get(position);
        String[] orderDetails = order.split("\n");
        productNameTextView.setText(orderDetails[0]);
        priceTextView.setText(orderDetails[1]);
        emailTextView.setText(orderDetails[2]);

        return rowView;
    }

}
