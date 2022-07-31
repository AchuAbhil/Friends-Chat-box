package com.northampton.friendschatbox.ui.fragment.SearchFriend;


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
import com.northampton.friendschatbox.databinding.FragmentSearchFriendBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.LandingActivity;
import com.northampton.friendschatbox.ui.fragment.SearchFriend.adapter.UserListAdapter;
import com.northampton.friendschatbox.ui.fragment.SearchFriend.adapter.AdapterInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFriendFragment extends BaseFragment implements AdapterInterface {

    public static final String TAG = SearchFriendFragment.class.getName();
    List<UserDetails> userList = new ArrayList<>();
    List<FriendRequestData> friendList = new ArrayList<>();
    UserDetails userDetails = new UserDetails();
    FriendRequestData currentFriendRequestData = new FriendRequestData();
    private AdapterInterface adapterInterface;
    private FragmentSearchFriendBinding binding;

    public SearchFriendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchFriendBinding.inflate(inflater, container, false);
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
        currentFriendRequestData.setRequestedAccepted(false);
        currentFriendRequestData.setEmailAddress(userDetails.getEmailAddress());
        currentFriendRequestData.setFullName(userDetails.getFullName());
        findFriend();
    }

    public void recycle() {
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvUser.setAdapter(new UserListAdapter(userList, adapterInterface));
    }

    private void findFriend() {
        userList.clear();
        userList.addAll(((LandingActivity) requireActivity()).getUsersList());
        if (userList != null && !userList.isEmpty()) {
            binding.rvUser.setVisibility(View.VISIBLE);
            binding.tvNoDBMessage.setVisibility(View.GONE);
        } else {
            binding.rvUser.setVisibility(View.GONE);
            binding.tvNoDBMessage.setVisibility(View.VISIBLE);
        }
        recycle();
    }

    @Override
    public void onItemClicked(UserDetails clickedUserDetails, String email) {
        friendList.clear();
        if (getAppPreferences().getAllFriendRequest() != null) {
            friendList = getAppPreferences().getAllFriends();
            friendList = ((LandingActivity) requireActivity()).removeNullInList(friendList);
            friendList = ((LandingActivity) requireActivity()).removeDuplicates(friendList);
        }
        Handler handler = new Handler();
        binding.rvUser.setEnabled(false);
        showProgressBar(true);
        FriendRequestData friendRequest = new FriendRequestData();
        Log.d(TAG, "getEmailAddress: "+userDetails.getEmailAddress());
        handler.postDelayed(() -> {
            friendRequest.setFullName(clickedUserDetails.getFullName());
            friendRequest.setEmailAddress(clickedUserDetails.getEmailAddress());
            friendRequest.setRequestedAccepted(false);
            ((LandingActivity) requireActivity()).friendsRequestDBUpdateCheck(friendRequest, currentFriendRequestData);
            showProgressBar(false);
            binding.rvUser.setEnabled(true);
        }, SPLASH_SCREEN_TIME_OUT);
    }

    @Override
    public void onDeleteCtaClicked(Integer id) {

    }

    @Override
    public void setEditableText(Integer id, String name) {

    }
}
