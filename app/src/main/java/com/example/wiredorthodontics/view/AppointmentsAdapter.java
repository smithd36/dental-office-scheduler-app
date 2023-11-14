/**
 * Adapter class to manage the cards that represent
 * available time slots.
 * @author Drey Smith
 * @date 10.20.2023
 */
package com.example.wiredorthodontics.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wiredorthodontics.R;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
    private List<String> appointments;
    private OnItemClickListener onItemClickListener;

    /**
     * Constructs an AppointmentsAdapter.
     *
     * @param appointments The list of appointment times.
     */
    public AppointmentsAdapter(List<String> appointments) {
        this.appointments = appointments;
    }

    /**
     * Interface for item click events.
     */
    public interface OnItemClickListener {
        void onItemClick(String selectedTime);
    }

    /**
     * Sets the click listener for the item.
     *
     * @param listener The item click listener.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout and create the ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String appointmentTime = appointments.get(position);
        holder.bind(appointmentTime);

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(appointmentTime);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    /**
     * Updates the list of appointments in the adapter.
     *
     * @param newAppointments The updated list of appointments.
     */
    public void updateAppointments(List<String> newAppointments) {
        this.appointments.clear();
        this.appointments.addAll(newAppointments);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder for holding the appointment time view.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView appointmentTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            appointmentTimeTextView = itemView.findViewById(R.id.txtAppointmentTime);
        }

        /**
         * Binds the appointment time to the view.
         *
         * @param appointmentTime The appointment time to display.
         */
        public void bind(String appointmentTime) {
            appointmentTimeTextView.setText(appointmentTime);
        }
    }
}