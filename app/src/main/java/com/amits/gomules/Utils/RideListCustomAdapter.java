package com.amits.gomules.Utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amits.gomules.Entity.RideListEntity;
import com.amits.gomules.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class RideListCustomAdapter extends RecyclerView.Adapter<RideListCustomAdapter.RideListViewHolder> {

    private ArrayList<RideListEntity> dataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RideListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tvTitle;
        TextView tvDescription;
        TextView tvPostedBy;
        TextView tvSource;
        TextView tvDestination;
        TextView tvDate;
        TextView tvContact;
        TextView tvEmail;
        TextView tvCost;
        TextView tvNoSeat;
        View currentView;
        String email;
        String mobNumber;

        public RideListViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription= itemView.findViewById(R.id.tvDesc);
            tvPostedBy= itemView.findViewById(R.id.tvPostedBy);
            tvSource= itemView.findViewById(R.id.tvSource);
            tvDestination= itemView.findViewById(R.id.tvDestination);
            tvDate= itemView.findViewById(R.id.tvDate);
            /*tvContact=itemView.findViewById(R.id.tvContact);
            tvEmail=itemView.findViewById(R.id.tvEmail);*/
            tvCost= itemView.findViewById(R.id.tvCost);
            tvNoSeat= itemView.findViewById(R.id.tvSeat);
            currentView=itemView;
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Call = menu.add(Menu.NONE, 1, 1, "Call");
            MenuItem Email = menu.add(Menu.NONE, 2, 2, "Email");
            Call.setOnMenuItemClickListener(onEditMenu);
            Email.setOnMenuItemClickListener(onEditMenu);
        }
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobNumber ));
                        currentView.getContext().startActivity(intent);
                        break;

                    case 2:

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto",email, null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Need more info on the ride");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Want to know more about the ride");
                        currentView.getContext().startActivity(Intent.createChooser(emailIntent, "Send email..."));

                        break;
                }
                return true;
            }
        };
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RideListCustomAdapter(ArrayList<RideListEntity> data) {
        this.dataSet = data;
    }

    @Override
    public RideListViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ride_list_card_view, parent, false);
        RideListViewHolder myViewHolder = new RideListViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RideListViewHolder holder, final int listPosition) {

        TextView tvTitle = holder.tvTitle;
        TextView tvDescription = holder.tvDescription;
        TextView tvPostedBy = holder.tvPostedBy;
        TextView tvSource = holder.tvSource;
        TextView tvDestination = holder.tvDestination;
        TextView tvDate = holder.tvDate;
        TextView tvContact = holder.tvContact;
        TextView tvEmail = holder.tvEmail;
        TextView tvCost = holder.tvCost;
        TextView tvNoSeat = holder.tvNoSeat;

        holder.email=dataSet.get(listPosition).getEmail();
        holder.mobNumber=dataSet.get(listPosition).getContactMobile();

        LocalDate datetime = LocalDate.parse(dataSet.get(listPosition).getAvailableDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String newDate= datetime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        tvTitle.setText("Title: "+dataSet.get(listPosition).getTitle());
        tvDescription.setText("Desc: "+dataSet.get(listPosition).getDescription());
        tvPostedBy.setText("By: "+dataSet.get(listPosition).getFirstName()+" "+dataSet.get(listPosition).getLastName());
        tvSource.setText("From: "+dataSet.get(listPosition).getSource());
        tvDestination.setText("To: "+dataSet.get(listPosition).getDestination());
        tvDate.setText("Date: "+newDate);
        /*tvContact.setText("Mobile: "+dataSet.get(listPosition).getContactMobile());*/
        /*tvEmail.setText("Email: "+dataSet.get(listPosition).getEmail());*/
        tvCost.setText("Cost: "+dataSet.get(listPosition).getCost());
        tvNoSeat.setText("Seats: "+dataSet.get(listPosition).getNoOfSeat());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
