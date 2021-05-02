package com.example.donationapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Projet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllProjectsAdapter extends RecyclerView.Adapter<AllProjectsAdapter.ProjectViewHolder> {

    private Context mContext;
    private List<Projet> mProjects;
    private OnItemClickListenerD mListener;
    Button mDonate;


    public AllProjectsAdapter(Context mContext, List<Projet> mProjects) {
        this.mContext = mContext;
        this.mProjects = mProjects;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.project_item_donater, parent, false);
        return new ProjectViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Projet projCurrent = mProjects.get(position);
        holder.vtitreView.setText(projCurrent.getTitre());
        holder.vDL.setText("Date lancement: "+projCurrent.getDateLancement());
        holder.vDE.setText("Date d'echeance: "+projCurrent.getDateEcheance());
        holder.vBudget.setText("Budget: "+projCurrent.getBudget());
        holder.vDescrip.setText(projCurrent.getDescription());
        Picasso.with(mContext)
                .load(projCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView vtitreView;
        public TextView vDL;
        public TextView vDE;
        public TextView vBudget;
        public TextView vDescrip;
        public ImageView imageView;
        //constructor
        public ProjectViewHolder(View itemView){
            super(itemView);
            vtitreView = itemView.findViewById(R.id.titreViewCard);
            vDL = itemView.findViewById(R.id.dateLancementView);
            vDE = itemView.findViewById(R.id.dateEcheanceView);
            vBudget = itemView.findViewById(R.id.budgetView);
            vDescrip = itemView.findViewById(R.id.DescriptionView);
            imageView = itemView.findViewById(R.id.imgView);

            mDonate=itemView.findViewById(R.id.btnDonateList);
            mDonate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mListener.donationClick(position);
                }
            });
            itemView.setOnClickListener(this);
            //itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }

    public interface OnItemClickListenerD {
        void onItemClick(int position);
        void donationClick(int position);
        //void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListenerD listener) {
        mListener = listener;
    }
}
