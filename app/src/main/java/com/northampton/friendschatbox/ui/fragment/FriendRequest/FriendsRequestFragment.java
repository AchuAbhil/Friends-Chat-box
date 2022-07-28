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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.FragmentFriendsRequestBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.LandingActivity;
import com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter.UserListAdapter;
import com.northampton.friendschatbox.ui.helper.AdapterInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsRequestFragment extends BaseFragment implements AdapterInterface {

    String SearchData = "";
    Integer friendId = 0;
    List<UserDetails> userList = new ArrayList<>();
    List<FriendRequestData> friendRequestList = new ArrayList<>();
    private AdapterInterface adapterInterface;
    private FragmentFriendsRequestBinding binding;

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
        return R.layout.fragment_friends_request;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterInterface = this;
        findFriend(SearchData);
        searchFriendsName();
    }

    private void searchFriendsName() {
        binding.rlSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchData = query;
                findFriend(SearchData);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void recycle() {
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvUser.setAdapter(new UserListAdapter(userList, adapterInterface));
    }

    private void findFriend(String userName) {
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
    public void onItemClicked(UserDetails userDetails, String email) {
        friendRequestList.clear();
        if (getAppPreferences().getAllFriendRequest() != null) {
            friendRequestList = getAppPreferences().getAllFriendRequest();
        }
        Handler handler = new Handler();
        binding.rvUser.setEnabled(false);
        showProgressBar(true);
        FriendRequestData friendRequest = new FriendRequestData();
        handler.postDelayed(() -> {
            friendRequest.setFullName(userDetails.getFullName());
            friendRequest.setEmailAddress(userDetails.getEmailAddress());
            friendRequest.setRequestedAccepted(false);
            ((LandingActivity) requireActivity()).friendsRequestDBUpdateCheck(userDetails.getEmailAddress(), friendRequest);
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
