package com.northampton.friendschatbox.ui.fragment.FriendRequest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.FragmentFriendsRequestBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter.FDRequestAdapterInterface;
import com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter.UserListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsRequestFragment extends BaseFragment implements FDRequestAdapterInterface {

    UserDetails userDetails = new UserDetails();
    List<FriendRequestData> friendRequestList = new ArrayList<>();
    private FragmentFriendsRequestBinding binding;
    private FDRequestAdapterInterface adapterInterface;

    public FriendsRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFriendsRequestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_search_friend;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterInterface = this;
        findFriendRequest();
    }

    public void recycle() {
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvUser.setAdapter(new UserListAdapter(friendRequestList, adapterInterface));
    }

    private void findFriendRequest() {
        userDetails = getAppPreferences().getUserInfo();
        friendRequestList.clear();
        friendRequestList.addAll(getAppPreferences().getAllFriendRequest());
        if (friendRequestList != null && !friendRequestList.isEmpty()) {
            binding.rvUser.setVisibility(View.VISIBLE);
            binding.tvNoDBMessage.setVisibility(View.GONE);
        } else {
            binding.rvUser.setVisibility(View.GONE);
            binding.tvNoDBMessage.setVisibility(View.VISIBLE);
        }
        recycle();
    }

    @Override
    public void onItemClicked(FriendRequestData userDetails, String email) {

    }

    @Override
    public void onDeleteCtaClicked(FriendRequestData userDetails, String email) {

    }

    @Override
    public void setEditableText(Integer id, String name) {

    }
}
