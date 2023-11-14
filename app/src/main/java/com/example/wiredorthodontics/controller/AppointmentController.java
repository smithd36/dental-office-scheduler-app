/**
 * Controller class to handle interactions with the data access object
 * Used within the main activity and the associated tab fragments for
 * each step of the appointment scheduling process.
 * -
 * The list of holidays that is used to
 * by the calendar was acquired via this .gov list of holidays:
 * https://www.employmentlawhandbook.com/employment-and-labor-laws/states/new-mexico/leave-laws/holidays/
 * -
 * @author Drey Smith
 * @date 10.28.2023
 */
package com.example.wiredorthodontics.controller;

import com.example.wiredorthodontics.R;
import com.example.wiredorthodontics.view.MainActivity;
import com.example.wiredorthodontics.model.Appointment;
import com.example.wiredorthodontics.model.AppointmentDAO;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController {
    // class level vars
    private final MainActivity view;
    private final AppointmentDAO model;
    private final List<String> holidays;

    /**
     * Constructor method to accept the view as context and the model as the data access object
     * Also initializes the 2023 holiday-dates list.
     * @param view
     * @param model
     */
    public AppointmentController(MainActivity view, AppointmentDAO model) {
        this.view = view;
        this.model = model;

        // initialize the list of holidays
        holidays = new ArrayList<>();
        holidays.add("2023-01-01"); // new years day
        holidays.add("2023-01-02"); // new years day (in lieu)
        holidays.add("2023-01-16"); // mlk jr day
        holidays.add("2023-04-09"); // easter sunday
        holidays.add("2023-05-14"); // mother's day
        holidays.add("2023-06-18"); // father's day
        holidays.add("2023-05-29"); // memorial day
        holidays.add("2023-06-19"); // Juneteenth
        holidays.add("2023-07-04"); // 4th of july
        holidays.add("2023-07-04"); // labor day
        holidays.add("2023-08-09"); // indigenous people's day
        holidays.add("2023-11-10"); // veterans day
        holidays.add("2023-11-23"); // thanksgiving
        holidays.add("2023-11-24"); // day after thanksgiving
        holidays.add("2023-12-25"); // christmas
    }

    /**
     * Method to collect all available times from the DAO/Model
     * Collects a list of all times and a list of scheduled times,
     * then subtracts the scheduled times from the available times to
     * only show back to the user the dates that are available.
     * @param selectedDate - selected date by the user
     * @return a 1d list of non-booked, selectable times
     */
    public List<String> getAvailableTimes(String selectedDate) {
        // starts as a list of all times for a date
        List<String> availableTimes = model.getAllTimesForDate(selectedDate);

        // retrieve a list of all booked times for the selected date
        List<String> bookedTimes = model.getBookedTimesForDate(selectedDate);

        // remove the booked times from available times
        availableTimes.removeAll(bookedTimes);

        return availableTimes;
    }

    /**
     * Method to receive the input parameters from the view, add them to the appointments
     * object, and add to the database on successful validation of each input.
     * @param patientName - name of new appointment scheduler
     * @param patientDOB - date of birth
     * @param patientAddress - their address
     * @param patientID - ID (3 types) chosen by patient
     * @param appointmentDescription - reason for visit
     * @param clinicSource - how did you hear about us?
     * @param selectedDate - appointment date
     * @param selectedTime - appointmentTime
     * @return true if successfully added
     */
    public boolean addAppointment(String patientName, String patientDOB, String patientAddress, String patientID,
                               String appointmentDescription, String clinicSource, String selectedDate, String selectedTime) {
        // initialize an appointment object
        Appointment appointment = new Appointment(patientName, patientDOB, patientAddress, patientID, appointmentDescription, clinicSource, selectedDate, selectedTime);

        if (!isEmpty(appointment)) {
            // if appointment insertion is successful, display the confirmation message
            if (model.insertAppointment(appointment) != -1) {
                String message = "Name: " + appointment.getPatientName() + "\n\n"
                        + "Date of Birth: " + appointment.getDob() + "\n\n"
                        + "Address: " + appointment.getAddress() + "\n\n"
                        + "ID: " + appointment.getIdentificationNumber() + "\n\n"
                        + "Reason for Visit: " + appointment.getAppointmentDescription() + "\n\n"
                        + "Source: " + appointment.getClinicSource() + "\n\n"
                        + "Appointment Date: " + appointment.getAppointmentDate() + "\n\n"
                        + "Appointment Time: " + appointment.getAppointmentTime();

                // Show an alert with appointment details
                view.showAlert(message);
                return true;
            }
        } else {
            view.showAlert(view.getString(R.string.null_error_alert));
        } return false;
    }

    /**
     * Parent method to the isHoliday method used to efficiently deter a user from
     * selecting a date that is not valid.
     * @param selectedDate - date that is selected
     * @return - true if valid false if holiday
     */
    public boolean isValid(String selectedDate) {
        if (selectedDate == null || selectedDate == "" || selectedDate.isEmpty()){
            view.showAlert(view.getString(R.string.unknown_error));
            return false;
        } else {
            return !isHoliday(selectedDate);
        }
    }

    /**
     * Compares the selected date to the list of holiday dates
     * @param selectedDate - date selected
     * @return - true if not in holiday
     */
    public boolean isHoliday(String selectedDate) {
        // compare to holidays
        return holidays.contains(selectedDate);
    }

    /**
     * Parent method of isNullOrEmpty used to efficiently validate
     * the user's input as empty or valid.
     * @param appointment - new appointment
     * @return true if non empty/null
     */
    public boolean isEmpty(Appointment appointment) {
        return isNullOrEmpty(appointment.getPatientName()) ||
                isNullOrEmpty(appointment.getDob()) ||
                isNullOrEmpty(appointment.getAddress()) ||
                isNullOrEmpty(appointment.getIdentificationNumber()) ||
                isNullOrEmpty(appointment.getAppointmentDescription()) ||
                isNullOrEmpty(appointment.getClinicSource()) ||
                isNullOrEmpty(appointment.getAppointmentDate()) ||
                isNullOrEmpty(appointment.getAppointmentTime());
    }

    /**
     * Method takes in a value passed from isEmpty then returns checks if empty
     * @param value - string passed from isEmpty
     * @return - true if non-empty for each value
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}