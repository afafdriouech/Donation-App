package com.example.donationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Donation;
import com.example.donationapp.models.Mydonation;

import java.util.List;

public class MyDonationsAdapter extends RecyclerView.Adapter<MyDonationsAdapter.DonationViewHolder> {

    private Context mContext;
    private List<Mydonation> mDonations;

    public MyDonationsAdapter(Context mContext, List<Mydonation> mDonations) {
        this.mContext = mContext;
        this.mDonations = mDonations;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.mydonation_item, parent, false);
        return new MyDonationsAdapter.DonationViewHolder(v);     }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Mydonation current = mDonations.get(position);
        holder.mAssociation.setText("My donation for: "+current.getAssociation());
        holder.mProject.setText("Project: "+current.getProject());
        holder.mAmount.setText("Amount: "+current.getAmount()+" "+ current.getCurrency());
        //holder.mCurrency.setText("Budget: "+projCurrent.getBudget());
    }

    @Override
    public int getItemCount() {
        return mDonations.size();
    }

    public class DonationViewHolder extends RecyclerView.ViewHolder {
        public TextView mAssociation;
        public TextView mProject;
        public TextView mAmount;
        //public TextView mCurrency;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            mAssociation = itemView.findViewById(R.id.donationAssoViewCard);
            mProject = itemView.findViewById(R.id.donationProjViewCard);
            mAmount = itemView.findViewById(R.id.donationAmountViewCard);
            //mCurrency = itemView.findViewById(R.id.donationCurrencyCardView);
        }
    }
}
