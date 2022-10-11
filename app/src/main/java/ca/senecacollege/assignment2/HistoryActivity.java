package ca.senecacollege.assignment2;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    static ArrayList<String> productNames = new ArrayList<>();
    static ArrayList<String> productQuantities = new ArrayList<>();
    static ArrayList<String> productPrices = new ArrayList<>();
    static ArrayList<String> productDates = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        MyAdapter myAdapter = new MyAdapter(this, productNames.toArray(new String[0]), productPrices.toArray(new String[0]), productQuantities.toArray(new String[0]));
        listView = (ListView) findViewById(R.id.historyListView);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Intent intent = new Intent(HistoryActivity.this, DetailPageActivity.class);
                intent.putExtra("product", productNames.get(index));
                intent.putExtra("price", productQuantities.get(index));
                intent.putExtra("date", productDates.get(index));
                startActivity(intent);
            }
        });
    }
    public static void updateHistory(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        productNames.add(sharedPreferences.getString("product", ""));
        productQuantities.add(sharedPreferences.getString("price", ""));
        productPrices.add(sharedPreferences.getString("quantity", ""));
        productDates.add(String.valueOf(new Date()));
    }
}
