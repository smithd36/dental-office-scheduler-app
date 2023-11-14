/**
 * Class represents the Appointment and the associated
 * attributes for a new appointment.
 *
 * @author Drey Smith
 * @date 10.20.2023
 */
package com.example.wiredorthodontics.model;

public class Appointment {
    private int id;
    private String patientName, address, identificationNumber, appointmentDescription, clinicSource;
    private String dob;
    private String appointmentTime;
    private String appointmentDate;

    /**
     * An empty constructor to initialize a new
     * appointment.
     */
    public Appointment() {}

    /**
     * Constructor to add a new appointment
     * None of the attributes should ever be null
     * @param patientName - name of patient
     * @param dob - dob of patient
     * @param address - address of patient
     * @param identificationNumber - id of patient (drivers, passport, state id no.)
     * @param appointmentDate - date of the new appointment
     * @param appointmentTime - time of appointment
     * @param appointmentDescription - reason for visit
     * @param clinicSource - how did you hear about us?
     */
    public Appointment(String patientName, String dob, String address, String identificationNumber, String appointmentDate, String appointmentTime, String appointmentDescription, String clinicSource) {
        this.patientName = patientName;
        this.dob = dob;
        this.address = address;
        this.identificationNumber = identificationNumber;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDescription = appointmentDescription;
        this.clinicSource = clinicSource;
    }

    /**
     * The row-id of the appointment if any
     * @return - intenger id of appointment
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a new ID
     * @param id - row index
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name of patient
     * @return string name
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Retrieve date of birth
     * @return the dob of patient
     */
    public String getDob() {
        return dob;
    }

    /**
     * Retrieves address of a patient
     * @return string address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the ID of a patient
     * @return the string id number
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * The time of the appointment
     * @return the formatted string time
     */
    public String getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Date of an appointment
     * @return the formatted scheduled date string
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Description of an appointment
     * @return string description (reason for visit)
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * Essentially the source of the referral.
     * Gets the answer to "How did you hear about us?"
     * @return string response to question as source of referral
     */
    public String getClinicSource() {
        return clinicSource;
    }
}