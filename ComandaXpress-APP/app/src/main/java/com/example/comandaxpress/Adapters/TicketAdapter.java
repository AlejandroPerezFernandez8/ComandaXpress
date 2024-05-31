package com.example.comandaxpress.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.comandaxpress.API.Clases.Ticket;
import com.example.comandaxpress.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends ArrayAdapter<Ticket> {
    private List<Ticket> tickets;

    public TicketAdapter(Context context, List<Ticket> tickets) {
        super(context, R.layout.ticket_item, tickets);
        this.tickets = tickets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TicketAdapter", "Getting view for position: " + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket_item, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.icon);
        TextView ticketInfo = convertView.findViewById(R.id.ticket_info);
        TextView ticketDate = convertView.findViewById(R.id.ticket_date);

        Ticket ticket = getItem(position);

        icon.setImageResource(R.drawable.ticket);
        ticketInfo.setText("Ticket nº " + ticket.getTicketId());

        // Formatear la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ticketDate.setText(sdf.format(ticket.getFechaHora()));

        return convertView;
    }
}
