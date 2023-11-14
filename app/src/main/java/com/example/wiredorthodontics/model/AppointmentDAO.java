/**
 * The database model/ data access object (dao) for the appointment scheduling
 * requirements. The model has methods to
 * @author Drey Smith
 * @date 10.28.2023
 */
package com.example.wiredorthodontics.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppointmentDAO {

    // Table and field names
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_IDENTIFICATION_NUMBER = "identification_number";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DESCRIPTION = "description";
    private final DatabaseHelper dbHelper;

    public AppointmentDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Method to receive a valid appointment from the controller and
     * insert it to a new row in the database.
     * @param appointment the validated new appointment
     * @return id of new row if success else -1
     */
    public long insertAppointment(Appointment appointment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, appointment.getPatientName());
        values.put(COLUMN_DATE_OF_BIRTH, appointment.getDob());
        values.put(COLUMN_ADDRESS, appointment.getAddress());
        values.put(COLUMN_IDENTIFICATION_NUMBER, appointment.getIdentificationNumber());
        values.put(COLUMN_DESCRIPTION, appointment.getAppointmentDescription());
        values.put(COLUMN_SOURCE, appointment.getClinicSource());
        values.put(COLUMN_DATE, appointment.getAppointmentDate());
        values.put(COLUMN_TIME, appointment.getAppointmentTime());

        // insert a the new row and close database
        long id = db.insert("appointments", null, values);
        db.close();
        return id; // the auto-incremented id of the new row
    }

    /**
     * Method to receive the date valid date selected by the user and
     * retrieve all times for that date
     * @param selectedDate the date selected by the user
     * @return a list of all times for the selected date
     */
    public List<String> getAllTimesForDate(String selectedDate) {
        int startHour = 8; // 8 am
        int endHour = 17; // 5 pm

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT time FROM appointments WHERE date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{selectedDate});

        List<String> allAppointments = new ArrayList<>();
        List<String> availableTimes = new ArrayList<>(); // init the list for available times

        if (cursor.moveToFirst()) {
            do {
                allAppointments.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        for (int hour = startHour; hour <= endHour; hour++) {
            if (hour != 12 && hour != 17) { // exclude 12:00pm (lunchtime) and 5pm (closing time)
                for (int minute = 0; minute < 60; minute += 60) { /* For every hour in the time range */
                    String timeSlot = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                    if (!allAppointments.contains(timeSlot)) {
                        if (hour >= 12) {
                            int displayHour = (hour > 12) ? hour - 12 : hour;
                            availableTimes.add(String.format(Locale.getDefault(), "%d:%02d PM", displayHour, minute));
                        } else {
                            availableTimes.add(String.format(Locale.getDefault(), "%02d:%02d AM", hour, minute));
                        }
                    }
                }
            }
        }
        return availableTimes;
    }

    /**
     * A list of times that already exist in the database for
     * a specific date that is received as input
     * @param selectedDate the date selected by user
     * @return a list of all booked times for that date (if any)
     */
    public List<String> getBookedTimesForDate(String selectedDate) {
        List<String> bookedTimes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // retrieve the booked times for the selected date
        String query = "SELECT " + COLUMN_TIME + " FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{selectedDate});

        if (cursor.moveToFirst()) {
            do {
                bookedTimes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookedTimes;
    }
}