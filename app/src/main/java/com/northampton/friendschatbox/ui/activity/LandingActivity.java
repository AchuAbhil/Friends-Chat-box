package com.northampton.friendschatbox.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.gson.Gson;
import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.DataBaseUsersListHelper;
import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.ActivityLandingBinding;
import com.northampton.friendschatbox.ui.BaseActivity;
import com.northampton.friendschatbox.utils.AppPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingActivity extends BaseActivity {

    private ActivityLandingBinding binding;
    private NavGraph navGraph;
    private NavController navController;
    private View headerView;
    private TextView navUsername;
    private TextView navEmail;
    private CircleImageView navProfile;
    private AppPreferences mAppPreferences;
    private UserDetails userDetails;
    private DataBaseUsersListHelper dataBaseUsersListHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpNavHost();
    }

    private void setUpNavHost() {
        mAppPreferences = AppPreferences.getInstance(this);
        userDetails = mAppPreferences.getUserInfo();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_landing);
        assert navHostFragment != null;
        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        dataBaseUsersListHelper = new DataBaseUsersListHelper(LandingActivity.this);
        navGraph = navInflater.inflate(R.navigation.landing_navigation);
        navController = navHostFragment.getNavController();
        navGraph.setStartDestination(R.id.landingFragment);
        setupDrawerLayout();
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        headerView = binding.navView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.navUsername);
        navEmail = headerView.findViewById(R.id.navEmail);
        navProfile = headerView.findViewById(R.id.navProfile);
        updateNavHeader();
    }

    private void setupDrawerLayout() {
        // Set up ActionBar
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.drawerLayout);
    }

    public void setToolbarTitle(String title) {
        binding.toolbar.setTitle(title);
    }

    public void updateNavHeader() {
        navUsername.setText(userDetails.getFullName());
        navEmail.setText(userDetails.getEmailAddress());
    }

    public Boolean updateDBUserDetails(String originalName, UserDetails userDetails) {
        return dataBaseUsersListHelper.updateUser(originalName, userDetails);
    }

    public HashMap<Boolean, String> updateDBFriendRequestList(String emailAddress, String friendRequestToString) {
        return dataBaseUsersListHelper.updateUserFriendRequestList(emailAddress, friendRequestToString);
    }

    public List<UserDetails> getUsersList() {
        List<UserDetails> userDetailsListMain = dataBaseUsersListHelper.getAllUsers(this);
        List<UserDetails> userDetailsList = new ArrayList<>();
        if (userDetailsListMain != null) {
            if (userDetailsListMain.size() > 0) {
                for (UserDetails user : userDetailsListMain) {
                    if (!user.getFullName().equals(this.userDetails.getFullName())) {
                        userDetailsList.add(user);
                    }
                }
            }
        }
        return userDetailsList;
    }

    public void friendsRequestDBUpdateCheck(String emailAddress, FriendRequestData friendRequest, String CurrentUserEmailAddress) {
        List<FriendRequestData> friendsList = new ArrayList<>();
        List<FriendRequestData> friendRequestList = new ArrayList<>();
        if (mAppPreferences.getAllFriends() != null) {
            friendsList = mAppPreferences.getAllFriends();
        }
        if (mAppPreferences.getAllFriendRequest() != null) {
            friendRequestList = mAppPreferences.getAllFriendRequest();
        }
        // if the logged in user has all ready been friends or not check is doing below
        if (friendsList.size() > 0) {
            if(!checkIfEmailPreExist(CurrentUserEmailAddress, friendsList, "friendsList")) {
                UpdateDB(emailAddress, friendRequest, friendRequestList);
            }
        } else {
            if (friendRequestList.size() > 0) {
                if(!checkIfEmailPreExist(CurrentUserEmailAddress, friendRequestList, "friendRequestList")) {
                    UpdateDB(emailAddress, friendRequest, friendRequestList);
                }
            }else {
                UpdateDB(CurrentUserEmailAddress, friendRequest, friendRequestList);
            }
        }
    }

    private void UpdateDB(String emailAddress, FriendRequestData friendRequest, List<FriendRequestData> friendRequestList) {
        friendRequestList.add(friendRequest);
        if (updateDBFriendRequestList(emailAddress, getFriendsListToString(friendRequestList)).containsKey(true)) {
            mAppPreferences.setAllFriendRequest(friendRequestList);
            Toast.makeText(this, friendRequest.getFullName()+" is added to your friends list", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Update friend DB error ", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean checkIfEmailPreExist(String emailAddress, List<FriendRequestData> friendsList, String list) {
        String toastMsg = "";
        if(list.equals("friendsList")){
            toastMsg = " is all ready added to the user list.";
        }else {
            toastMsg = " is all ready added to the friend request list.";
        }
        for (FriendRequestData friends : friendsList) {
            if (friends.getEmailAddress().equals(emailAddress)) {
                Toast.makeText(this, friends.getFullName() + toastMsg, Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    private String getFriendsListToString(List<FriendRequestData> friendRequestList) {
        Gson gson = new Gson();
        return gson.toJson(friendRequestList);
    }
}
