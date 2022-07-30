package com.northampton.friendschatbox.ui.fragment.Landing.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.databinding.ViewFriendsUserListViewBinding;

import java.util.List;

public class UserFriendsListAdapter extends RecyclerView.Adapter<UserFriendsListAdapter.holder> {

    List<FriendRequestData> items;

    public UserFriendsListAdapter(List<FriendRequestData> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewFriendsUserListViewBinding itemBinding = ViewFriendsUserListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new holder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, final int position) {
        final FriendRequestData contact = items.get(position);
        holder.itemBinding.tvFDName.setText(contact.getFullName());
        holder.itemBinding.tvBuddyTime.setText(contact.getDateBecomeBuddy());
    }


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class holder extends RecyclerView.ViewHolder {
        private final ViewFriendsUserListViewBinding itemBinding;

        public holder(ViewFriendsUserListViewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
