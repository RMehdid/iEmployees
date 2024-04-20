package com.raynmore.iemployees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.List;

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


    /**private void performSearch(String query) {
        // Création d'une instance de DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Utilisation de la méthode searchEmployees(query) pour rechercher des employés
        ArrayList<Employee> searchResults = dbHelper.searchEmployees(query);

        // Mettre à jour votre liste ou grille avec les résultats de la recherche
        updateEmployeeList(searchResults);
    }

    private void updateEmployeeList(ArrayList<Employee> employees) {
        // Mettez à jour votre liste ou grille avec les employés fournis
        // Par exemple, si vous utilisez un adaptateur, vous pouvez appeler notifyDataSetChanged() sur cet adaptateur
        if (currentViewMode == VIEW_MODE_LIST) {
            ListViewAdapter listViewAdapter = new ListViewAdapter(this, R.layout.list_item, employees);
            listView.setAdapter(listViewAdapter);
        } else {
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, employees);
            gridView.setAdapter(gridViewAdapter);
        }
    }**/

}