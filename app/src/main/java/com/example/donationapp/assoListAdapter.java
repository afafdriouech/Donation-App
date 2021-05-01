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

import com.example.donationapp.models.Association;
import com.example.donationapp.models.Projet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class assoListAdapter extends RecyclerView.Adapter<assoListAdapter.AssoViewHolder> {

    private Context mContext;
    private List<Association> mAssociation;
    private OnItemClickListener mListener;
    public assoListAdapter(Context context, List<Association> associations)  {
        mContext = context;
        mAssociation = associations;
    }

    @NonNull
    @Override
    public AssoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.association_item, parent, false);
        return new AssoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssoViewHolder holder, int position) {
        Association projCurrent = mAssociation.get(position);
        holder.assoName.setText(projCurrent.getAssoName());
        holder.adresse.setText("Adresse: "+projCurrent.getAdresse());
        holder.email.setText("Email: "+projCurrent.getEmail());
        holder.phone.setText("Phone: "+projCurrent.getPhone());
        holder.dateCreation.setText("Date lancement: "+projCurrent.getDateCreation());

    }

    @Override
    public int getItemCount() {
        return mAssociation.size();
    }

    public class AssoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView adresse,assoName,dateCreation, email, phone;

        public AssoViewHolder(View itemView) {
            super(itemView);
            adresse = itemView.findViewById(R.id.assoadresse);
            assoName= itemView.findViewById(R.id.assoName);
            dateCreation= itemView.findViewById(R.id.assodateCreation);
            email= itemView.findViewById(R.id.assoemail);
            phone= itemView.findViewById(R.id.assophone);

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
                            //notifyDataSetChanged();
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

/*package com.example.donationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Association;
import com.example.donationapp.models.Projet;

import java.util.ArrayList;
import java.util.List;

public class assoListAdapter extends RecyclerView.Adapter<assoListAdapter.AssociationViewHolder>  {

    Context context;
    ArrayList<Association> listAsso;

    public assoListAdapter(Context context, ArrayList<Association> listAsso) {
        this.context = context;
        this.listAsso = listAsso;
    }

    @NonNull
    @Override
    public AssociationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_associations,parent, false);
        return new AssociationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssociationViewHolder holder, int position) {
        Association assoCurrent = listAsso.get(position);
        holder.adresse.setText(assoCurrent.getAdresse());
        holder.email.setText(assoCurrent.getEmail());
        holder.dateCreation.setText(assoCurrent.getDateCreation());
        holder.phone.setText(assoCurrent.getPhone());


    }

    /*@Override
    public int getItemCount() {*/
        /*return listAsso.size();
    }

    public static class AssociationViewHolder extends RecyclerView.ViewHolder{

        TextView adresse,assoName,dateCreation, email, phone;

        public AssociationViewHolder(@NonNull View itemView) {
            super(itemView);
            adresse = itemView.findViewById(R.id.assoadresse);
             assoName= itemView.findViewById(R.id.assoName);
             dateCreation= itemView.findViewById(R.id.assodateCreation);
             email= itemView.findViewById(R.id.assoemail);
             phone= itemView.findViewById(R.id.assophone);

        }
    }
}*/
