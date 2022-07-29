package com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.databinding.ViewListUserViewBinding;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.holder> {

    List<FriendRequestData> items;
    public FDRequestAdapterInterface buttonListener;

    public UserListAdapter(List<FriendRequestData> items, FDRequestAdapterInterface buttonListener) {
        this.items = items;
        this.buttonListener = buttonListener;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewListUserViewBinding itemBinding = ViewListUserViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new holder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder,final int position) {
        final FriendRequestData contact = items.get(position);
        holder.itemBinding.tvTitle.setText(contact.getFullName());

        holder.itemBinding.imgChecked.setOnClickListener(v -> {
            holder.itemBinding.imgChecked.setEnabled(false);
            buttonListener.onAddItemClicked(contact, contact.getEmailAddress());
            holder.itemBinding.imgChecked.setEnabled(true);
        });

        holder.itemBinding.imgDelete.setOnClickListener(v -> {
            holder.itemBinding.imgDelete.setEnabled(false);
            buttonListener.onDeleteCtaClicked(contact, contact.getEmailAddress());
            holder.itemBinding.imgDelete.setEnabled(true);
        });

    }


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {
        private ViewListUserViewBinding itemBinding;

        public holder(ViewListUserViewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            itemBinding.imgDelete.setVisibility(View.VISIBLE);
        }
    }
}
