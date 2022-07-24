package com.northampton.friendschatbox.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.utils.AppPreferences;


public abstract class BaseFragment extends Fragment {

    protected AppPreferences mAppPreferences;
    private AppCompatActivity activity;
    private UserDetails userDetails;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAppPreferences = AppPreferences.getInstance(getContext());
        userDetails = new UserDetails();
        userDetails = mAppPreferences.getUserInfo() != null ? mAppPreferences.getUserInfo() : userDetails;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public AppCompatActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
