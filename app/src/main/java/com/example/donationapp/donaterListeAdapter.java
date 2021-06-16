package com.example.donationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Donater;
import com.example.donationapp.models.Donation;

import java.util.List;

public class donaterListeAdapter extends RecyclerView.Adapter<donaterListeAdapter.DonaterViewHolder>{
    private Context mContext;
    private List<Donater> mDonaters;
    private String id;
    public donaterListeAdapter(android.content.Context context, List<Donater> donaters) {
        mContext = context;
        mDonaters = donaters;
    }
    @NonNull
    @Override
    public DonaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.donater_item, parent, false);
        return new DonaterViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull DonaterViewHolder holder, int position) {
        Donater donCurrent = mDonaters.get(position);
        holder.name.setText(donCurrent.getFullName());
        holder.email.setText(donCurrent.getEmail());
        holder.phone.setText(donCurrent.getPhone());
        holder.adresse.setText(donCurrent.getAddress());


    }
    @Override
    public int getItemCount() {
        return mDonaters.size();
    }

    public class DonaterViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public TextView phone;
        public TextView adresse;

        public DonaterViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            adresse = itemView.findViewById(R.id.adr);

        }
    }
}
