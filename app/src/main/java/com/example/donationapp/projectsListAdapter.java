package com.example.donationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Projet;

import java.util.List;

public class projectsListAdapter extends RecyclerView.Adapter<projectsListAdapter.ProjectViewHolder> {

    private Context mContext;
    private List<Projet> mProjects;
    public projectsListAdapter(Context context, List<Projet> projects) {
        mContext = context;
        mProjects = projects;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.project_item, parent, false);
        return new ProjectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Projet projCurrent = mProjects.get(position);
        holder.vtitreView.setText(projCurrent.getTitre());
        holder.vDL.setText(projCurrent.getDateLancement());
        holder.vDE.setText(projCurrent.getDateEcheance());
        holder.vBudget.setText(projCurrent.getBudget());
        holder.vDescrip.setText(projCurrent.getDescription());
                //.setText(uploadCurrent.getName());
        /*Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        public TextView vtitreView;
        public TextView vDL;
        public TextView vDE;
        public TextView vBudget;
        public TextView vDescrip;
        public ImageView imageView;
        public ProjectViewHolder(View itemView) {
            super(itemView);
            vtitreView = itemView.findViewById(R.id.titreViewCard);
            vDL = itemView.findViewById(R.id.dateLancementView);
            vDE = itemView.findViewById(R.id.dateEcheanceView);
            vBudget = itemView.findViewById(R.id.budgetView);
            vDescrip = itemView.findViewById(R.id.DescriptionView);
        }
    }
}
