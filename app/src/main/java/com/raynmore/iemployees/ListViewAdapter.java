package com.raynmore.iemployees;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Employee> {

    private Context mContext;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Employee> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        Employee employee = getItem(position);
        ImageView img = v.findViewById(R.id.imageView);
        TextView txtName = v.findViewById(R.id.txtName);
        Button btnCall = v.findViewById(R.id.btnCall);

        assert employee != null;
        img.setImageResource(employee.getImageId());
        txtName.setText(employee.getName());
        Button btnMessage = v.findViewById(R.id.btnMessage);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (employee.getPhoneNumber() != null && !employee.getPhoneNumber().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + employee.getPhoneNumber()));
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Phone number not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Show dialog to modify employee
                NewEmployeeAdapter dialog = new NewEmployeeAdapter(employee);
                dialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "NewEmployeeAdapter");
                return true;
            }
        });

        return v;
    }
}
