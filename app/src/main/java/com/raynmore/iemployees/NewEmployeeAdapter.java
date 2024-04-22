package com.raynmore.iemployees;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NewEmployeeAdapter extends DialogFragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Employee employee;
    private String image;

    NewEmployeeAdapter() {
    }

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

        Button pickImageButton = view.findViewById(R.id.image_picker_button);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        builder.setView(view)
                .setTitle(getString(R.string.add_new_employee))
                // Add action buttons
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editName = view.findViewById(R.id.nameTextField);
                        EditText editPhone = view.findViewById(R.id.phoneTextField);
                        EditText editEmail = view.findViewById(R.id.emailTextField);

                        // Get the text from the EditText views
                        String name = editName.getText().toString();
                        String phone = editPhone.getText().toString();
                        String email = editEmail.getText().toString();

                        Employee newEmployee = new Employee(name, phone, email, image);

                        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

                        if (employee != null) {
                            newEmployee.setId(employee.getId());
                            dbHelper.updateEmployee(newEmployee);
                        } else {
                            dbHelper.addEmployee(newEmployee);

                        }
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("test", "changes applied");
                        startActivity(intent);
                    }
                })
                .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        if (employee != null) {
            builder.setNegativeButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
                    dbHelper.deleteEmployee(employee.getId());
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("test", "employer supprimer");
                    startActivity(intent);
                }
            });
        }

        return builder.create();
    }

    private void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } else {
            Toast.makeText(requireContext(), "No app available to handle image picking", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            assert selectedImageUri != null;

            Log.d("ImageUri", "" + selectedImageUri);
            image = selectedImageUri.toString();
//            employee.setImageId("" + selectedImageUri);
        }
    }
}