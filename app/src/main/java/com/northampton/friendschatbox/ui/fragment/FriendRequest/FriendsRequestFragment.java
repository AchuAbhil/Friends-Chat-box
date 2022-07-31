package com.northampton.friendschatbox.ui.fragment.FriendRequest;


import static com.northampton.friendschatbox.ui.fragment.SplashScreenFragment.SPLASH_SCREEN_TIME_OUT;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.northampton.friendschatbox.ui.activity.LandingActivity;
import com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter.FDRequestAdapterInterface;
import com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter.UserListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsRequestFragment extends BaseFragment implements FDRequestAdapterInterface {

    private static final String TAG = FriendsRequestFragment.class.getName();
    UserDetails userDetails = new UserDetails();
    List<FriendRequestData> friendRequestList = new ArrayList<>();
    FriendRequestData currentFriendRequestData = new FriendRequestData();
    private FragmentFriendsRequestBinding binding;
    private FDRequestAdapterInterface adapterInterface;
    private LandingActivity activity;

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
        userDetails = getAppPreferences().getUserInfo();
        activity = (LandingActivity) requireActivity();
        currentFriendRequestData.setRequestedAccepted(true);
        currentFriendRequestData.setEmailAddress(userDetails.getEmailAddress());
        currentFriendRequestData.setFullName(userDetails.getFullName());
        currentFriendRequestData.setDateBecomeBuddy(getDateTime());
        findFriendRequest();
    }

    public void recycle() {
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvUser.setAdapter(new UserListAdapter(friendRequestList, adapterInterface));
    }

    private void findFriendRequest() {
        friendRequestList.clear();
        UserDetails userDetails = mAppPreferences.getUserInfo();
        friendRequestList.addAll(userDetails.getFriendsRequest());
        friendRequestList = activity.removeNullInList(friendRequestList);
        friendRequestList = activity.removeDuplicates(friendRequestList);
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
    public void onAddItemClicked(FriendRequestData clickedUserDetails, String email) {
        friendRequestList.clear();
        UserDetails userDetails = mAppPreferences.getUserInfo();
        if (userDetails.getFriendsRequest() != null) {
            friendRequestList = userDetails.getFriendsRequest();
        }
        Handler handler = new Handler();
        binding.rvUser.setEnabled(false);
        showProgressBar(true);
        FriendRequestData friendRequest = new FriendRequestData();
        Log.d(TAG, "getEmailAddress: " + userDetails.getEmailAddress());
        handler.postDelayed(() -> {
            friendRequest.setFullName(clickedUserDetails.getFullName());
            friendRequest.setEmailAddress(clickedUserDetails.getEmailAddress());
            friendRequest.setDateBecomeBuddy(getDateTime());
            friendRequest.setRequestedAccepted(true);
            activity.friendsListDBUpdateCheck(friendRequest, currentFriendRequestData, false);
            showProgressBar(false);
            binding.rvUser.setEnabled(true);
            findFriendRequest();
        }, SPLASH_SCREEN_TIME_OUT);
    }

    @Override
    public void onDeleteCtaClicked(FriendRequestData clickedUserDetails, String email) {
        friendRequestList.clear();
        UserDetails userDetails = mAppPreferences.getUserInfo();
        if (userDetails.getFriendsRequest() != null) {
            friendRequestList = userDetails.getFriendsRequest();
        }
        Handler handler = new Handler();
        binding.rvUser.setEnabled(false);
        showProgressBar(true);
        FriendRequestData friendRequest = new FriendRequestData();
        Log.d(TAG, "getEmailAddress: " + userDetails.getEmailAddress());
        handler.postDelayed(() -> {
            friendRequest.setFullName(clickedUserDetails.getFullName());
            friendRequest.setEmailAddress(clickedUserDetails.getEmailAddress());
            friendRequest.setRequestedAccepted(false);
            activity.friendsListDBUpdateCheck(friendRequest, currentFriendRequestData, true);
            showProgressBar(false);
            binding.rvUser.setEnabled(true);
            findFriendRequest();
        }, SPLASH_SCREEN_TIME_OUT);
    }

    @Override
    public void setEditableText(Integer id, String name) {

    }
}
