package com.example.donationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Donation;
import com.example.donationapp.models.Projet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class doncalledAdapter extends RecyclerView.Adapter<doncalledAdapter.DonViewHolder>{
    private Context mContext;
    private List<Donation> mDonations;
    private String id;
    private projectsListAdapter.OnItemClickListener mListener;
    public doncalledAdapter(Context context, List<Donation> donations) {
        mContext = context;
        mDonations = donations;
    }
    @NonNull
    @Override
    public DonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.doncalled_item, parent, false);
        return new DonViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull DonViewHolder holder, int position) {
        Donation donCurrent = mDonations.get(position);
        holder.idprojet.setText(donCurrent.getDonaterId());
        holder.amount.setText("+ "+donCurrent.getDonationAmount()+" dh");


    }
    @Override
    public int getItemCount() {
        return mDonations.size();
    }

    public class DonViewHolder extends RecyclerView.ViewHolder {
        public TextView idprojet;
        public TextView amount;

        public DonViewHolder(View itemView) {
            super(itemView);
            idprojet = itemView.findViewById(R.id.idproj);
            amount = itemView.findViewById(R.id.budget);

        }
    }
}
