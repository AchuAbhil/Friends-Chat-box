package com.northampton.friendschatbox.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.DataBaseHelper;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.ActivityMainBinding;
import com.northampton.friendschatbox.ui.BaseActivity;

import java.util.List;

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
        assert navHostFragment != null;
        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        navGraph = navInflater.inflate(R.navigation.app_navigation);
        navController = navHostFragment.getNavController();
        navGraph.setStartDestination(R.id.splashScreenFragment);
    }

    public Boolean loginCheck(String email, String password) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

        List<UserDetails> userDetailsList = dataBaseHelper.getAllUsers(this);
        Boolean isLoginCheck = false;

        if (userDetailsList != null) {
            if (userDetailsList.size() > 0) {
                for (UserDetails userDetails : userDetailsList) {
                    isLoginCheck = userDetails.emailAddress.equals(email) && userDetails.password.equals(password);
                }
                if (!isLoginCheck) {
                    Toast.makeText(this, "UserName: " + email + ",Is not registered kindly register.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "UserName: " + email + ",Is not registered kindly register.", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Empty data base please add a user.", Toast.LENGTH_LONG).show();
            return false;
        }
        return isLoginCheck;
    }

    public Boolean registerUser(UserDetails userDetails) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        return dataBaseHelper.addUser(userDetails);
    }

    public void navigateToLanding() {
        startActivity(new Intent(this, LandingActivity.class));
        finish();
    }
}
