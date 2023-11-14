/**
 * A fragment displaying a calendar for scheduling appointments.
 * @author Drey Smith
 * @date 10.22.2023
 */
package com.example.wiredorthodontics.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wiredorthodontics.R;
import com.example.wiredorthodontics.controller.AppointmentController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private AppointmentController controller;
    private RecyclerView recyclerAppointments;
    private AppointmentsAdapter adapter;
    private String selectedDate = "";

    /**
     * Public empty constructor
     */
    public CalendarFragment() {
    }

    /**
     * Sets the AppointmentController for managing appointments.
     *
     * @param controller The AppointmentController to be set.
     */
    public void setController(AppointmentController controller) {
        this.controller = controller;
    }

    /**
     * Inflates the layout and initializes the calendar view with available appointments.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState  The previous state of the fragment.
     * @return                   The inflated View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppointmentData", Context.MODE_PRIVATE);

        recyclerAppointments = view.findViewById(R.id.recyclerViewAvailableAppointments);
        TextView txtAvailableTimes = view.findViewById(R.id.txtAvailableTimes);

        adapter = new AppointmentsAdapter(new ArrayList<>());
        adapter.setOnItemClickListener(selectedTime -> {
            // Store the selected date in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selectedTime", selectedTime);
            editor.apply();

            // go to next tab
            MainActivity activity = (MainActivity) requireActivity();
            activity.switchToNextTab();
        });

        recyclerAppointments.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        recyclerAppointments.setAdapter(adapter);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        // today is minimum date
        calendarView.setMinDate(System.currentTimeMillis());
        Calendar maxDateCalendar = Calendar.getInstance();
        maxDateCalendar.add(Calendar.DATE, 30);
        long maxDate = maxDateCalendar.getTimeInMillis();
        calendarView.setMaxDate(maxDate);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year, month, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectedDate = sdf.format(selectedCalendar.getTime());

            // perform holiday validation
            if (controller.isValid(selectedDate)) {

                // add the selected date to the shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedDate", selectedDate);
                editor.apply();

                txtAvailableTimes.setText(getString(R.string.available_times) + " " + selectedDate);

                // retrieve a list of available appointments and update
                List<String> fetchedAppointments = controller.getAvailableTimes(selectedDate);
                updateAvailableAppointments(fetchedAppointments);
            } else {
                showHolidayAlert(selectedDate); // if the date is a holiday notify the user
            }
        });

        return view;
    }

    /**
     * Updates the list of available appointments.
     *
     * @param newAppointments A List of new appointments to be displayed.
     */
    public void updateAvailableAppointments(List<String> newAppointments) {
        if (adapter != null) {
            adapter.updateAppointments(newAppointments);
        }
    }

    /**
     * Displays an alert for holidays preventing appointment selection on a specific date.
     *
     * @param selectedDate The date that is a holiday and cannot be selected for an appointment.
     */
    public void showHolidayAlert(String selectedDate) {
        String message = "The selected date, " + selectedDate + ", is a holiday.\nPlease select a different date";

        new AlertDialog.Builder(requireContext())
                .setTitle("Holiday Alert")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}