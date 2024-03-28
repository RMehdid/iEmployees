package com.raynmore.iemployees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Employee> {
    public GridViewAdapter(@NonNull Context context, int resource, @NonNull List<Employee> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        Employee employee = getItem(position);
        ImageView img = v.findViewById(R.id.imageView);
        TextView txtName = v.findViewById(R.id.txtName);

        img.setImageResource(employee.getImageId());
        txtName.setText(employee.getName());

        return v;
    }
}
