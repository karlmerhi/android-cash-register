package ca.senecacollege.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailPageActivity extends AppCompatActivity {

    TextView productName;
    TextView productPrice;
    TextView productDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        productName  = (TextView) findViewById(R.id.productName);
        productPrice = (TextView) findViewById(R.id.productPrice);
        productDate = (TextView) findViewById(R.id.productDate);

        Intent intent = getIntent();

        productName.setText("Product: " + intent.getStringExtra("product"));
        productPrice.setText("Price: " + intent.getStringExtra("price"));
        productDate.setText("Purchase Date " + intent.getStringExtra("date"));
    }
}
