package com.example.donationapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Comment;
import com.example.donationapp.models.Favorite;

import java.util.List;

public class commentListAdapter extends RecyclerView.Adapter<commentListAdapter.ComViewHolder> {
    private Context mContext;
    private List<Comment> mComment;
    private commentListAdapter.OnItemClickListener mListener;
    public commentListAdapter(Context context, List<Comment> comments)  {
        mContext = context;
        mComment = comments;
    }

    @NonNull
    @Override
    public ComViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        return new ComViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull commentListAdapter.ComViewHolder holder, int position) {
        Comment ComCurrent = mComment.get(position);
        holder.nameAsso.setText("Name:"+ComCurrent.getNameAsso());
        holder.idDonator.setText("id"+ComCurrent.getIdDonater());

    }
    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public class ComViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView nameAsso, idDonator ,review;

        public ComViewHolder(View itemView) {
            super(itemView);

            nameAsso = itemView.findViewById(R.id.favName);
            idDonator = itemView.findViewById(R.id.idon);
            review = itemView.findViewById(R.id.review);
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

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            return false;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);

    }
    public void setOnItemClickListener(commentListAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
