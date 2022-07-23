package com.northampton.friendschatbox.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.ActivityLandingBinding;
import com.northampton.friendschatbox.ui.BaseActivity;
import com.northampton.friendschatbox.utils.AppPreferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingActivity extends BaseActivity {

    private ActivityLandingBinding binding;
    private NavGraph navGraph;
    private NavController navController;
    private View headerView;
    private TextView navUsername;
    private TextView navEmail;
    private CircleImageView navProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpNavHost();
    }

    private void setUpNavHost() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_landing);
        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        navGraph = navInflater.inflate(R.navigation.landing_navigation);
        navController = navHostFragment.getNavController();
        navGraph.setStartDestination(R.id.landingFragment);
        setupDrawerLayout();
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        headerView = binding.navView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.navUsername);
        navEmail = headerView.findViewById(R.id.navEmail);
        navProfile = headerView.findViewById(R.id.navProfile);
        mAppPreferences = AppPreferences.getInstance(this);
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
        mAppPreferences = AppPreferences.getInstance(this);
/*        userDetails = mAppPreferences.getUserCashedInfo();
        navUsername.setText(userDetails.getUsername());
        navEmail.setText(userDetails.getEmail());*/
//        Log.i("GlideImage", userDetails.getProfilePic());
/*        Glide.with(this)
                .asDrawable()
                .error(R.drawable.image1)
                .into(navProfile);*/

    }
}
