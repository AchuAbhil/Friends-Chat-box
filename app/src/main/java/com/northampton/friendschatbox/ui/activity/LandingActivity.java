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
import com.google.gson.reflect.TypeToken;
import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.DataBaseUsersListHelper;
import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.ActivityLandingBinding;
import com.northampton.friendschatbox.ui.BaseActivity;
import com.northampton.friendschatbox.utils.AppPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingActivity extends BaseActivity {

    public static final String TAG = LandingActivity.class.getName();
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

    public Boolean updateProfileDBUserDetails(String originalName, UserDetails userDetails) {
        return dataBaseUsersListHelper.updateUser(originalName, userDetails);
    }

    public HashMap<Boolean, String> updateDBFriendRequestList(String emailAddress, String friendRequestToString) {
        return dataBaseUsersListHelper.updateUserFriendRequestList(emailAddress, friendRequestToString);
    }

    public HashMap<Boolean, String> updateDBFriendList(String emailAddress, String friendRequestToString) {
        return dataBaseUsersListHelper.updateUserFriendList(emailAddress, friendRequestToString);
    }

    public void friendsListDBUpdateCheck(FriendRequestData clickedFriendData, FriendRequestData currentFriendData, Boolean isDeleteFriendsItem) {
        List<FriendRequestData> currentFriendsList = new ArrayList<>();
        List<FriendRequestData> clickedFriendsList = getFriendList(clickedFriendData.getEmailAddress());
        List<FriendRequestData> currentFriendRequestList = new ArrayList<>();
        List<FriendRequestData> clickedFriendRequestList = getFriendRequestList(clickedFriendData.getEmailAddress());
        if (mAppPreferences.getAllFriends() != null) {
            currentFriendsList = mAppPreferences.getAllFriends();
        }
        if (mAppPreferences.getAllFriendRequest() != null) {
            currentFriendRequestList = mAppPreferences.getAllFriendRequest();
        }
        // if the logged in user has all ready been friends or not check is doing below
        if (!isDeleteFriendsItem) {
            if (currentFriendsList.size() > 0) {
                //check if current friends list contain clicked email
                if (!checkIfEmailPreExist(clickedFriendData.getEmailAddress(), currentFriendsList, "friendsList")) {
                    Log.d(TAG, "friendsListDBUpdateCheck CurrentUserEmailAddress: " + currentFriendData.getEmailAddress());
                    UpdateFDListDB(currentFriendData, clickedFriendData, currentFriendsList, clickedFriendsList, currentFriendRequestList, clickedFriendRequestList);
                } else {
                    deleteFDRQListDB(currentFriendData, clickedFriendData, currentFriendRequestList, clickedFriendRequestList);
                }
            } else {
                UpdateFDListDB(currentFriendData, clickedFriendData, currentFriendsList, clickedFriendsList, currentFriendRequestList, clickedFriendRequestList);
            }
        } else {
            deleteFDRQListDB(currentFriendData, clickedFriendData, currentFriendRequestList, clickedFriendRequestList);
        }
    }

    private void initFriendsListForDB() {

    }

    private void UpdateFDListDB(
            FriendRequestData currentFriendData,
            FriendRequestData clickedFriendData,
            List<FriendRequestData> currentFriendsList,
            List<FriendRequestData> clickedFriendsList,
            List<FriendRequestData> currentFriendRequestList,
            List<FriendRequestData> clickedFriendRequestList
    ) {
        currentFriendsList.add(clickedFriendData);
        List<FriendRequestData> currentFriendLists = removeDuplicates(currentFriendsList);
        Log.d(TAG, "UpdateFDListDB currentEmailAddress: " + currentFriendData.getEmailAddress() + " getFriendsListToString: " + getFriendsListToString(currentFriendLists));
        if (updateDBFriendList(currentFriendData.getEmailAddress(), getFriendsListToString(currentFriendLists)).containsKey(true)) {
            mAppPreferences.setAllFriends(currentFriendLists);
            //check if clicked friends list contain current email
            if (!checkIfEmailPreExist(currentFriendData.getEmailAddress(), clickedFriendsList)) {
                clickedFriendsList.add(currentFriendData);
                List<FriendRequestData> clickedFriendList = removeDuplicates(clickedFriendsList);
                Log.d(TAG, "UpdateFDListDB ClickedEmailAddress: " + clickedFriendData.getEmailAddress() + " getFriendsListToString: " + getFriendsListToString(clickedFriendList));
                updateDBFriendList(clickedFriendData.getEmailAddress(), getFriendsListToString(clickedFriendList));
            }
            deleteFDRQListDB(currentFriendData, clickedFriendData, currentFriendRequestList, clickedFriendRequestList);
            Toast.makeText(this, clickedFriendData.getFullName() + " is added to your friends list", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Update friend DB error ", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteFDRQListDB(FriendRequestData currentFriendRequest, FriendRequestData clickedFriendRequest, List<FriendRequestData> currentFriendRequestList, List<FriendRequestData> clickedFriendRequestList) {
        if (currentFriendRequestList.size() > 0) {
            currentFriendRequestList = removeItem(currentFriendRequestList, clickedFriendRequest);
        }
        List<FriendRequestData> currentFriendRequestLists = removeDuplicates(currentFriendRequestList);
        Log.d(TAG, "deleteFDRQListDB currentEmailAddress: " + currentFriendRequest.getEmailAddress() + " getFriendsListToString: " + getFriendsListToString(currentFriendRequestLists));
        if (updateDBFriendRequestList(currentFriendRequest.getEmailAddress(), getFriendsListToString(currentFriendRequestLists)).containsKey(true)) {
            mAppPreferences.setAllFriendRequest(currentFriendRequestList);
            //check if clicked friends request list contain current email
            if (checkIfEmailPreExist(currentFriendRequest.getEmailAddress(), clickedFriendRequestList)) {
                if (clickedFriendRequestList.size() > 0) {
                    clickedFriendRequestList = removeItem(clickedFriendRequestList, currentFriendRequest);
                    List<FriendRequestData> clickedFriendRequestsList = removeDuplicates(clickedFriendRequestList);
                    Log.d(TAG, "deleteFDRQListDB ClickedEmailAddress: " + clickedFriendRequest.getEmailAddress() + " getFriendsListToString: " + getFriendsListToString(clickedFriendRequestsList));
                    updateDBFriendRequestList(clickedFriendRequest.getEmailAddress(), getFriendsListToString(clickedFriendRequestsList));
                }
            }
        } else {
            Toast.makeText(this, "Update friend DB error ", Toast.LENGTH_LONG).show();
        }
    }

    private List<FriendRequestData> removeItem(List<FriendRequestData> currentFriendRequestList, FriendRequestData clickedFriendRequest) {
        List<FriendRequestData> friendRequestData = new ArrayList<>();
        FriendRequestData userDetails = new FriendRequestData();
        if (currentFriendRequestList != null) {
            if (currentFriendRequestList.size() > 0) {
                for (FriendRequestData user : currentFriendRequestList) {
                    if (user.getEmailAddress() != null) {
                        if (!user.getEmailAddress().equals(clickedFriendRequest.getEmailAddress())) {
                            userDetails = user;
                        }
                    }
                }
            }
        }
        friendRequestData.add(userDetails);
        return friendRequestData;
    }


    public List<UserDetails> getUsersList() {
        List<UserDetails> userDetailsListMain = dataBaseUsersListHelper.getAllUsers(this);
        List<UserDetails> userDetailsList = new ArrayList<>();
        if (userDetailsListMain != null) {
            if (userDetailsListMain.size() > 0) {
                for (UserDetails user : userDetailsListMain) {
                    if (!user.getFullName().equals(this.userDetails.getFullName()) && user.getFullName() != null) {
                        userDetailsList.add(user);
                    }
                }
            }
        }
        return userDetailsList;
    }

    public List<FriendRequestData> removeNullInList(List<FriendRequestData> currentList) {
        List<FriendRequestData> friendRequestListMain = currentList;
        List<FriendRequestData> friendRequestList = new ArrayList<>();
        if (friendRequestListMain != null) {
            if (friendRequestListMain.size() > 0) {
                for (FriendRequestData user : friendRequestListMain) {
                    if (user.getFullName() != null) {
                        friendRequestList.add(user);
                    }
                }
            }
        }
        return friendRequestList;
    }

    public List<FriendRequestData> getFriendRequestList(String emailAddress) {
        List<UserDetails> userDetailsListMain = dataBaseUsersListHelper.getAllUsers(this);
        List<FriendRequestData> friendRequestData;
        UserDetails userDetails = new UserDetails();
        if (userDetailsListMain != null) {
            if (userDetailsListMain.size() > 0) {
                for (UserDetails user : userDetailsListMain) {
                    if (user.getEmailAddress().equals(emailAddress) && user.getFullName() != null) {
                        userDetails = user;
                    }
                }
            }
        }
        friendRequestData = convertToList(userDetails.getFriendsRequestList());
        Log.d(TAG, "fdRqEmailAddress: " + userDetails.getFriendsRequestList());
        return friendRequestData;
    }

    public List<FriendRequestData> getFriendList(String emailAddress) {
        List<UserDetails> userDetailsListMain = dataBaseUsersListHelper.getAllUsers(this);
        List<FriendRequestData> friendRequestData;
        UserDetails userDetails = new UserDetails();
        if (userDetailsListMain != null) {
            if (userDetailsListMain.size() > 0) {
                for (UserDetails user : userDetailsListMain) {
                    if (user.getEmailAddress().equals(emailAddress)) {
                        userDetails = user;
                    }
                }
            }
        }
        friendRequestData = convertToList(userDetails.getFriendsList());
        Log.d(TAG, "fdListEmailAddress: " + userDetails.getFriendsList());
        return friendRequestData;
    }

    public void friendsRequestDBUpdateCheck(FriendRequestData clickedFriendRequest, FriendRequestData currentFriendRequest) {
        List<FriendRequestData> friendsList = new ArrayList<>();
        List<FriendRequestData> currentFriendRequestList = new ArrayList<>();
        List<FriendRequestData> clickedFriendRequestList = getFriendRequestList(clickedFriendRequest.getEmailAddress());
        if (mAppPreferences.getAllFriends() != null) {
            friendsList = mAppPreferences.getAllFriends();
        }
        if (mAppPreferences.getAllFriendRequest() != null) {
            currentFriendRequestList = mAppPreferences.getAllFriendRequest();
        }
        // if the logged in user has all ready been friends or not check is doing below
        if (friendsList.size() > 0) {
            //check if current friends request list contain clicked email
            if (!checkIfEmailPreExist(clickedFriendRequest.getEmailAddress(), friendsList, "friendsList")) {
                Log.d(TAG, "CurrentUserEmailAddress: " + currentFriendRequest.getEmailAddress());
                UpdateFDRQDB(currentFriendRequest, clickedFriendRequest, currentFriendRequestList, clickedFriendRequestList);
            }
        } else {
            if (currentFriendRequestList.size() > 0) {
                //check if current friends request list contain clicked email
                if (!checkIfEmailPreExist(clickedFriendRequest.getEmailAddress(), currentFriendRequestList, "friendRequestList")) {
                    Log.d(TAG, "CurrentUserEmailAddress: " + userDetails.getFriendsRequestList());
                    UpdateFDRQDB(currentFriendRequest, clickedFriendRequest, currentFriendRequestList, clickedFriendRequestList);
                }
            } else {
                UpdateFDRQDB(currentFriendRequest, clickedFriendRequest, currentFriendRequestList, clickedFriendRequestList);
            }
        }
    }

    private void UpdateFDRQDB(FriendRequestData currentFriendRequest, FriendRequestData clickedFriendRequest, List<FriendRequestData> currentFriendRequestList, List<FriendRequestData> clickedFriendRequestList) {
        currentFriendRequestList.add(clickedFriendRequest);
        List<FriendRequestData> currentFriendRequestLists = removeDuplicates(currentFriendRequestList);
        Log.d(TAG, "currentEmailAddress: " + currentFriendRequest.getEmailAddress() + " getFriendsListToString: " + getFriendsListToString(currentFriendRequestLists));
        if (updateDBFriendRequestList(currentFriendRequest.getEmailAddress(), getFriendsListToString(currentFriendRequestLists)).containsKey(true)) {
            mAppPreferences.setAllFriendRequest(currentFriendRequestList);
            //check if clicked friends request list contain current email
            if (!checkIfEmailPreExist(currentFriendRequest.getEmailAddress(), clickedFriendRequestList)) {
                clickedFriendRequestList.add(currentFriendRequest);
                List<FriendRequestData> clickedFriendRequestsList = removeDuplicates(clickedFriendRequestList);
                Log.d(TAG, "ClickedEmailAddress: " + clickedFriendRequest.getEmailAddress() + " getFriendsListToString: " + getFriendsListToString(clickedFriendRequestsList));
                updateDBFriendRequestList(clickedFriendRequest.getEmailAddress(), getFriendsListToString(clickedFriendRequestsList));
            }
            Toast.makeText(this, clickedFriendRequest.getFullName() + " is added to your friends request list", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Update friend DB error ", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean checkIfEmailPreExist(String emailAddress, List<FriendRequestData> friendsList, String list) {
        String toastMsg = "";
        if (list.equals("friendsList")) {
            toastMsg = " is all ready added to the user list.";
        } else {
            toastMsg = " is all ready added to the friend request list.";
        }
        for (FriendRequestData friends : friendsList) {
            if(friends.getEmailAddress()!=null) {
                if (friends.getEmailAddress().equals(emailAddress)) {
                    Toast.makeText(this, friends.getFullName() + toastMsg, Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean checkIfEmailPreExist(String emailAddress, List<FriendRequestData> friendsList) {
        if (friendsList.size() > 0) {
            for (FriendRequestData friends : friendsList) {
                if (friends.getEmailAddress().equals(emailAddress)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<FriendRequestData> removeDuplicates(List<FriendRequestData> clickedFriendRequestList) {
        List<FriendRequestData> listWithDuplicates = clickedFriendRequestList;
        List<FriendRequestData> listWithoutDuplicates = new ArrayList<>(new HashSet<>(listWithDuplicates));
        return listWithoutDuplicates;
    }

    private String getFriendsListToString(List<FriendRequestData> friendRequestList) {
        Gson gson = new Gson();
        return gson.toJson(friendRequestList);
    }

    public List<FriendRequestData> convertToList(String json) {
        List<FriendRequestData> temp;
        Gson gson = new Gson();
        temp = new ArrayList<>();
        if (json != null) {
            if (!json.isEmpty()) {
                temp = new ArrayList<>();
            } else {
                Type type = new TypeToken<List<FriendRequestData>>() {
                }.getType();
                temp = gson.fromJson(json, type);
            }
        }
        return temp;
    }
}
