package com.raynmore.iemployees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;

public class MainActivity extends AppCompatActivity {
    private SearchView recherche;
    private ImageView list ;

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private List<Employee> employeeList = new ArrayList<>();
    private int currentViewMode = 0;

    static final int VIEW_MODE_LIST = 0;
    static final int VIEW_MODE_GRID = 1;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the custom toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        /**list= findViewById(R.id.iconListView);**/

        if(intent.hasExtra("test")){
            Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
            /**list.performClick();**/

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

        stubList = findViewById(R.id.stub_list);
        stubGrid = findViewById(R.id.stub_grid);
        recherche= findViewById(R.id.recherche);

        stubList.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.mylistview);
        gridView = findViewById(R.id.mygridview);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        employeeList = dbHelper.getEmployees();

        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LIST);

        recherche.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Employee> rechercheemp = new ArrayList<>();
                String minuscultext = newText.toLowerCase();
                for (Employee emp : employeeList){
                    if(emp.getName().toLowerCase().contains(minuscultext)){
                        rechercheemp.add(emp);
                    }

                }
                setAdapters(rechercheemp);

                return false;
            }
        });
    }

    public void newEmployeeBtnOnClick(View view) {
        NewEmployeeAdapter dialog = new NewEmployeeAdapter();
        dialog.show(getSupportFragmentManager(), "NewEmployeeAdapter");
    }

    private void saveSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences ("ViewMode", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt ("currentViewMode", currentViewMode);
        editor.apply();
    }

    public void setListView(View view) {
        currentViewMode = VIEW_MODE_LIST;
        setListViewVisibility();
        saveSharedPreferences();
    }

    public void setGridView(View view) {
        currentViewMode = VIEW_MODE_GRID;
        setGridViewVisibility();
        saveSharedPreferences();
    }

    private void setListViewVisibility() {
        stubList.setVisibility(View.VISIBLE);
        stubGrid.setVisibility(View.GONE);
        setAdapters(employeeList);
    }

    private void setGridViewVisibility() {
        stubList.setVisibility(View.GONE);
        stubGrid.setVisibility(View.VISIBLE);

        setAdapters(employeeList);
    }


    private void setAdapters(List<Employee> employee) {
        if (VIEW_MODE_LIST == currentViewMode) {
            ListViewAdapter listViewAdapter = new ListViewAdapter(this, R.layout.list_item, employee);
            listView.setAdapter(listViewAdapter);
        } else {
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, employee);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with image picking
                // Your code to initiate image picking here
            } else {
                Toast.makeText(this, "You denied request to gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }
}