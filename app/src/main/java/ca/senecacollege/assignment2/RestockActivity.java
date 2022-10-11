package ca.senecacollege.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RestockActivity extends AppCompatActivity {

    String quantity[] = new String[2];
    String name[] = new String[2];
    TextView textViewProduct;
    EditText editText;
    ListView listView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        quantity = MainActivity.getProductQuantities();
        myAdapter = new MyAdapter(this, MainActivity.getProductNames(), MainActivity.getProductPrices(), MainActivity.getProductQuantities());
        listView = (ListView) findViewById(R.id.restockListView);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                name = MainActivity.getProductNames();
                textViewProduct = (TextView) findViewById(R.id.productType);
                textViewProduct.setText(name[index]);
            }
        });
    }

    public void OkayButton(View view) {
        int index = MainActivity.listIndex(findViewById(R.id.productType));
        textViewProduct = (TextView) findViewById(R.id.productType);
        editText = (EditText) findViewById(R.id.editText);

        if (TextUtils.isDigitsOnly(editText.getText().toString()) && !editText.getText().toString().matches("")) {
            if (index != 3) {
                quantity[index] = editText.getText().toString();
                MainActivity.setProductQuantities(quantity);
                myAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(RestockActivity.this, "All fields are required!!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RestockActivity.this, "Input must only contain digits (no spaces)", Toast.LENGTH_SHORT).show();
        }
    }

    public void CancelButton(View view) {
        Intent myIntent = new Intent(RestockActivity.this, ManageActivity.class);
        startActivity(myIntent);
    }
}
