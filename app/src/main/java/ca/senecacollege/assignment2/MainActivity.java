package ca.senecacollege.assignment2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    NumberPicker numberPicker;
    TextView textViewProduct;
    TextView textViewTotal;
    TextView textViewQuantity;
    MyAdapter myAdapter;

    static String[] productNames = {"Pants", "Shoes", "Hats"};
    static String[] productPrices = {"20.44", "10.44", "5.9"};
    static String[] productQuantities = {"10", "100", "30"};

    public static String[] getProductNames() {
        return productNames;
    }

    public static String[] getProductPrices() {
        return productPrices;
    }

    public static String[] getProductQuantities() {
        return productQuantities;
    }

    public static void setProductQuantities(String[] productQuantities) {
        MainActivity.productQuantities = productQuantities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an adapter class
        myAdapter = new MyAdapter(this, productNames, productPrices, productQuantities);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_restock, null);
        ListView restockListView = (ListView) view.findViewById(R.id.restockListView);
        restockListView.setAdapter(myAdapter);

        // Create number picker 0 - 100
        numberPicker = (NumberPicker) findViewById(R.id.quantityNumberPicker);
        numberPicker.setMaxValue(100);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                int index = listIndex(findViewById(R.id.productType));

                textViewTotal = (TextView) findViewById(R.id.total);
                textViewProduct = (TextView) findViewById(R.id.productType);
                textViewQuantity = (TextView) findViewById(R.id.quantity);
                textViewQuantity.setText(Integer.toString(newValue));

                if (index < 3)
                    textViewTotal.setText(String.format("%.2f", newValue * Double.parseDouble(productPrices[index])));
            }
        });

        // Set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                textViewProduct = (TextView) findViewById(R.id.productType);
                textViewProduct.setText(productNames[index]);

                textViewTotal = (TextView) findViewById(R.id.total);
                numberPicker = (NumberPicker) findViewById(R.id.quantityNumberPicker);

                textViewTotal.setText(String.format("%.2f", numberPicker.getValue() * Double.valueOf(productPrices[index])));
            }
        });
    }

    public void buyButton(View view) {
        textViewTotal = (TextView) findViewById(R.id.total);

        if (textViewTotal.getText().toString().equals("Total") || textViewTotal.getText().toString().equals("0.00")) {
            Toast.makeText(MainActivity.this, "All fields are required!!!", Toast.LENGTH_SHORT).show();
        } else {
            if (inStock()) {

                new AlertDialog.Builder(MainActivity.this) // Pass a reference to your main activity here
                        .setTitle("Thank You for your purchase")
                        .setMessage("Your purchase is " + numberPicker.getValue() + " " + textViewProduct.getText().toString() + " for " + textViewTotal.getText().toString()).show();
                myAdapter.notifyDataSetChanged();

                textViewProduct = (TextView) findViewById(R.id.productType);

                SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("product", textViewProduct.getText().toString());
                editor.putString("price", textViewTotal.getText().toString());
                editor.putString("quantity", Integer.toString(numberPicker.getValue()));
                editor.apply();
                HistoryActivity.updateHistory(getApplicationContext());
            } else {
                Toast.makeText(MainActivity.this, "Not enough quantity in stock", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void manageButton(View v) {
        Intent myIntent = new Intent(MainActivity.this, ManageActivity.class);
        startActivity(myIntent);
    }


    public boolean inStock() {
        boolean inStock = true;
        int index = listIndex(findViewById(R.id.productType));
        numberPicker = (NumberPicker) findViewById(R.id.quantityNumberPicker);

        int purchasedQuantity = numberPicker.getValue();

        if (purchasedQuantity > Integer.valueOf(productQuantities[index])) {
            inStock = false;
        } else {
            productQuantities[index] = String.valueOf(Integer.parseInt(productQuantities[index]) - purchasedQuantity);
        }

        return inStock;
    }

    public static int listIndex(TextView tv) {
        int index = 3;

        switch (tv.getText().toString()) {
            case "Pants":
                index = 0;
                break;
            case "Shoes":
                index = 1;
                break;
            case "Hats":
                index = 2;
                break;
            default:
        }
        return index;
    }
}