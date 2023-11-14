/**
 * A fragment that allows a user to input their
 * information required for the appointment.
 * @author Drey Smith
 * @date 10.22.2023
 */
package com.example.wiredorthodontics.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wiredorthodontics.R;
import com.example.wiredorthodontics.controller.AppointmentController;

public class PersonalInfoFragment extends Fragment {

    private EditText edtPatientName,edtDOB, edtAddress, edtIDNumber, edtDescription, edtClinicSource;
    private Spinner spinnerIdChoices;
    private Button btnSubmit;
    private AppointmentController controller;

    /**
     * Required empty public constructor.
     */
    public PersonalInfoFragment() {
    }

    /**
     * Sets the AppointmentController for managing appointments.
     *
     * @param controller The AppointmentController to be set.
     */
    public void setController(AppointmentController controller) {
        // assign
        this.controller = controller;
    }

    /**
     * Inflates the layout for entering personal information and sets up user interface components.
     *
     * @param inflater           The LayoutInflater object to inflate views.
     * @param container          The parent view for the fragment UI.
     * @param savedInstanceState  The previous state of the fragment.
     * @return                   The inflated View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        edtPatientName = view.findViewById(R.id.edtPatientName);
        edtDOB = view.findViewById(R.id.edtDateOfBirth);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtIDNumber = view.findViewById(R.id.edtIDNumber);
        edtDescription = view.findViewById(R.id.edtAppointmentDescription);
        edtClinicSource = view.findViewById(R.id.edtClinicSource);
        spinnerIdChoices = view.findViewById(R.id.spinnerIdChoices);
        btnSubmit = view.findViewById(R.id.btnSubmitPersonalInfo);


        // set spinner options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.id_choices_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdChoices.setAdapter(adapter);

        // create a click listener to retrieve the ID number based on the selection
        spinnerIdChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected item and perform operations based on the selection
                String selectedItem = spinnerIdChoices.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Driving License":
                        edtIDNumber.setHint("Driver's License #");
                        break;
                    case "Passport":
                        edtIDNumber.setHint("Passport #");
                        break;
                    case "State ID":
                        edtIDNumber.setHint("State ID #");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSubmit.setOnClickListener(v -> addAppointment());

        return view;
    }

    /**
     * Processes the entered information to add an appointment using AppointmentController.
     */
    private void addAppointment() {
        String patientName = edtPatientName.getText().toString();
        String patientDOB = edtDOB.getText().toString();
        String patientAddress = edtAddress.getText().toString();
        String patientID = edtIDNumber.getText().toString();
        String appointmentDescription = edtDescription.getText().toString();
        String clinicSource = edtClinicSource.getText().toString();

        // get date and time from preferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppointmentData", Context.MODE_PRIVATE);
        String appointmentDate = sharedPreferences.getString("selectedDate", "DEFAULT_VALUE");
        String appointmentTime = sharedPreferences.getString("selectedTime", "DEFAULT_VALUE");

        boolean isAdded = controller.addAppointment(patientName, patientDOB, patientAddress, patientID, appointmentDate, appointmentTime, appointmentDescription, clinicSource);
        if (isAdded) {
            showAppointmentConfirmation();
        }
    }


    /**
     * Displays a confirmation dialog after successfully scheduling an appointment.
     */
    private void showAppointmentConfirmation() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Appointment Added")
                .setMessage("Your appointment has been scheduled!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Go back to the first tab
                        MainActivity activity = (MainActivity) requireActivity();
                        activity.switchToFirstTab();
                    }
                })
                .show();
    }
}