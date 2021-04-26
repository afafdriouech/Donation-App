package com.example.donationapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Projet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class projectsListAdapter extends RecyclerView.Adapter<projectsListAdapter.ProjectViewHolder> {

    private Context mContext;
    private List<Projet> mProjects;
    private OnItemClickListener mListener;
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
        holder.vDL.setText("Date lancement: "+projCurrent.getDateLancement());
        holder.vDE.setText("Date d'echeance: "+projCurrent.getDateEcheance());
        holder.vBudget.setText("Budget: "+projCurrent.getBudget());
        holder.vDescrip.setText(projCurrent.getDescription());
                //.setText(uploadCurrent.getName());
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

    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener{
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
            imageView = itemView.findViewById(R.id.imgView);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem update = menu.add(Menu.NONE, 1, 1, "Update");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
            update.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onUpdateClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onUpdateClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
