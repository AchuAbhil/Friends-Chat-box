package com.northampton.friendschatbox.ui.fragment.Landing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.FragmentLandingPageBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.LandingActivity;
import com.northampton.friendschatbox.ui.fragment.Landing.adapter.UserFriendsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class LandingFragment extends BaseFragment {

    private static final String TAG = LandingFragment.class.getName();
    UserDetails userDetails = new UserDetails();
    List<FriendRequestData> friendsList = new ArrayList<>();
    private FragmentLandingPageBinding binding;
    private LandingActivity activity;

    public LandingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLandingPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_landing_page;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LandingActivity) requireActivity();
        activity.setToolbarTitle("My Friends");
        userDetails = getAppPreferences().getUserInfo();
        friendsList = userDetails.getFriends();
        findFriendList();
    }

    private void findFriendList() {
        friendsList.clear();
        userDetails = getAppPreferences().getUserInfo();
        friendsList.addAll(userDetails.getFriends());
        friendsList = activity.removeNullInList(friendsList);
        if (friendsList != null && !friendsList.isEmpty()) {
            binding.rvUser.setVisibility(View.VISIBLE);
            binding.tvNoDBMessage.setVisibility(View.GONE);
        } else {
            binding.rvUser.setVisibility(View.GONE);
            binding.tvNoDBMessage.setVisibility(View.VISIBLE);
        }
        recycle();
    }

    public void recycle() {
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvUser.setAdapter(new UserFriendsListAdapter(friendsList));
    }

    @Override
    public void onResume() {
        super.onResume();
        findFriendList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
