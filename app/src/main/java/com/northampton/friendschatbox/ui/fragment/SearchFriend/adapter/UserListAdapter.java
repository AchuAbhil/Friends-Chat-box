package com.northampton.friendschatbox.ui.fragment.SearchFriend.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.ViewListUserViewBinding;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.holder> {

    List<UserDetails> items;
    public AdapterInterface buttonListener;

    public UserListAdapter(List<UserDetails> items, AdapterInterface buttonListener) {
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
        final UserDetails contact = items.get(position);
        holder.itemBinding.tvTitle.setText(contact.getFullName());


        holder.itemBinding.imgChecked.setOnClickListener(v -> {
            holder.itemBinding.imgChecked.setEnabled(false);
            buttonListener.onItemClicked(contact, contact.getEmailAddress());
            holder.itemBinding.imgChecked.setEnabled(true);
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
        }
    }
}
