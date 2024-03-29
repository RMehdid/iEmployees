package com.raynmore.iemployees;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class NewEmployeeAdapter extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.new_employee_popup, null);
        builder.setView(view)
                .setTitle("Add new employee")
                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editName = view.findViewById(R.id.nameTextField);
                        EditText editPhone = view.findViewById(R.id.phoneTextField);

                        // Get the text from the EditText views
                        String name = editName.getText().toString();
                        String phone = editPhone.getText().toString();

                        Employee newEmployee = new Employee(name, phone, R.drawable.profile_placeholder);

                        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
                        boolean success = dbHelper.addEmployee(newEmployee);
                        Toast.makeText(requireContext(), "Success: " + success, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
