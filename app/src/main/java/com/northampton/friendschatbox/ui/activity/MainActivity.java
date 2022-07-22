package com.northampton.friendschatbox.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.ActivityMainBinding;
import com.northampton.friendschatbox.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private NavGraph navGraph;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpNavHost();
    }

    private void setUpNavHost() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        navGraph = navInflater.inflate(R.navigation.app_navigation);
        navController = navHostFragment.getNavController();
        navGraph.setStartDestination(R.id.splashScreenFragment);

  /*      if (mAppPreferences.getToken() != null) {
            if(mAppPreferences.getToken().equals("")) {
                navGraph.setStartDestination(R.id.splashScreenFragment);
            }
        } else {
            navigateToLanding();
        }*/
    }

    public void navigateToLanding() {
        startActivity(new Intent(this, LandingActivity.class));
        finish();
    }
}
