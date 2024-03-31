package com.raynmore.iemployees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private List<Employee> employeeList = new ArrayList<>();
    private int currentViewMode = 0;

    static final int VIEW_MODE_LIST = 0;
    static final int VIEW_MODE_GRID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the custom toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stubList = findViewById(R.id.stub_list);
        stubGrid = findViewById(R.id.stub_grid);

        stubList.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.mylistview);
        gridView = findViewById(R.id.mygridview);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        employeeList = dbHelper.getEmployees();

        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LIST);
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

        setAdapters();
    }

    private void setGridViewVisibility() {
        stubList.setVisibility(View.GONE);
        stubGrid.setVisibility(View.VISIBLE);

        setAdapters();
    }

    private void setAdapters() {
        if (VIEW_MODE_LIST == currentViewMode) {
            ListViewAdapter listViewAdapter = new ListViewAdapter(this, R.layout.list_item, employeeList);
            listView.setAdapter(listViewAdapter);
        } else {
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, employeeList);
            gridView.setAdapter(gridViewAdapter);
        }
    }
}