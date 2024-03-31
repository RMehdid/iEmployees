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

    private Employee employee;

    NewEmployeeAdapter() { }

    NewEmployeeAdapter(Employee employee) {
        this.employee = employee;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.new_employee_popup, null);

        if (employee != null) {
            EditText editName = view.findViewById(R.id.nameTextField);
            editName.setText(employee.getName());

            EditText editPhone = view.findViewById(R.id.phoneTextField);
            editPhone.setText(employee.getPhoneNumber());

            EditText editEmail = view.findViewById(R.id.emailTextField);
            editEmail.setText(employee.getEmail());
        }

        builder.setView(view)
                .setTitle("Add new employee")
                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editName = view.findViewById(R.id.nameTextField);
                        EditText editPhone = view.findViewById(R.id.phoneTextField);
                        EditText editEmail = view.findViewById(R.id.emailTextField);

                        // Get the text from the EditText views
                        String name = editName.getText().toString();
                        String phone = editPhone.getText().toString();
                        String email = editEmail.getText().toString();

                        Employee newEmployee = new Employee(name, phone, email, R.drawable.profile_placeholder);

                        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

                        if (employee != null) {
                            newEmployee.setId(employee.getId());
                            dbHelper.updateEmployee(newEmployee);
                        } else {
                            dbHelper.addEmployee(newEmployee);
                        }
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
