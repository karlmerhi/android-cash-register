package ca.senecacollege.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
    }

    public void historyButton(View v) {
        Intent myIntent = new Intent(ManageActivity.this, HistoryActivity.class);
        startActivity(myIntent);
    }

    public void restockButton(View v) {
        Intent myIntent = new Intent(ManageActivity.this, RestockActivity.class);
        startActivity(myIntent);
    }
}
