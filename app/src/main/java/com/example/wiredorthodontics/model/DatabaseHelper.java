/**
 * Manages the SQLite database for storing appointment information.
 * @author Drey Smith
 * @date 10.20.2023
 */
package com.example.wiredorthodontics.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Name of the database.
     */
    private static final String DATABASE_NAME = "AppData.db";

    /**
     * Database version.
     */
    private static final int DATABASE_VERSION = 1;

    // Table and field names
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_IDENTIFICATION_NUMBER = "identification_number";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DESCRIPTION = "description";

    /**
     * Constructs a DatabaseHelper object.
     *
     * @param context The application context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createAppointmentsTable(db);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the appointments table if it exists and create it again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    /**
     * Creates the appointments table in the database.
     *
     * @param db The database.
     */
    public void createAppointmentsTable(SQLiteDatabase db) {
        String createAppointmentsTable = "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DATE_OF_BIRTH + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_IDENTIFICATION_NUMBER + " TEXT," +
                COLUMN_SOURCE + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT" +
                ")";
        db.execSQL(createAppointmentsTable);
    }
}