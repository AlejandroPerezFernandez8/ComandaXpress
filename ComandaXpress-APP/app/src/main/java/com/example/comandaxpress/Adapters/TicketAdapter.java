package com.example.comandaxpress.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandaxpress.API.Clases.Ticket;
import com.example.comandaxpress.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TicketRecyclerAdapter extends RecyclerView.Adapter<TicketRecyclerAdapter.TicketViewHolder> {
    private List<Ticket> tickets;
    private Context context;

    public TicketRecyclerAdapter(Context context, List<Ticket> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        holder.icon.setImageResource(R.drawable.ticket);
        holder.ticketInfo.setText("Ticket nº " + ticket.getTicketId());

        // Formatear la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        holder.ticketDate.setText(sdf.format(ticket.getFechaHora()));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView ticketInfo;
        TextView ticketDate;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            ticketInfo = itemView.findViewById(R.id.ticket_info);
            ticketDate = itemView.findViewById(R.id.ticket_date);
        }
    }
}
