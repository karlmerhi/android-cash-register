package ca.senecacollege.assignment2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class MyAdapter extends ArrayAdapter<String> {
    Context context;
    String names[];
    String prices[];
    String quantities[];

    MyAdapter(Context c, String names[], String prices[], String quantities[]) {
        super(c, R.layout.mylist, R.id.productName, names);
        this.context = c;
        this.names = names;
        this.prices = prices;
        this.quantities = quantities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.mylist, parent, false);

        TextView name = row.findViewById(R.id.productName);
        TextView price = row.findViewById(R.id.productPrice);
        TextView quantity = row.findViewById(R.id.productQuantity);

        name.setText(names[position]);
        price.setText(prices[position]);
        quantity.setText(quantities[position]);

        return row;
    }
}