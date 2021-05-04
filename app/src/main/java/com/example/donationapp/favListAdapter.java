package com.example.donationapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Association;
import com.example.donationapp.models.Favorite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.List;

public class favListAdapter extends RecyclerView.Adapter<favListAdapter.FavViewHolder> {
    private Context mContext;
    private List<Favorite> mFavorite;
    private favListAdapter.OnItemClickListener mListener;
    public favListAdapter(Context context, List<Favorite> favorites)  {
        mContext = context;
        mFavorite = favorites;
    }

    @NonNull
    @Override
    public favListAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.favorite_item, parent, false);
        return new favListAdapter.FavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull favListAdapter.FavViewHolder holder, int position) {
        Favorite favCurrent = mFavorite.get(position);
        holder.assoName.setText("Name: "+favCurrent.getNameAsso());


    }

    @Override
    public int getItemCount() {
        return mFavorite.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView assoName;

        public FavViewHolder(View itemView) {
            super(itemView);

            assoName= itemView.findViewById(R.id.favName);
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
    public void setOnItemClickListener(favListAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
