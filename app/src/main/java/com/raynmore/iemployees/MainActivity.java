package com.raynmore.iemployees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the custom toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onListViewClick(View view) {
        // Handle list view icon click
        Toast.makeText(this, "List View Clicked", Toast.LENGTH_SHORT).show();
    }

    public void onGridViewClick(View view) {
        // Handle grid view icon click
        Toast.makeText(this, "Grid View Clicked", Toast.LENGTH_SHORT).show();
    }
}