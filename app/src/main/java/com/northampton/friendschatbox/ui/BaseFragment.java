package com.northampton.friendschatbox.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.northampton.friendschatbox.data.DatabaseFactory;
import com.northampton.friendschatbox.utils.AppPreferences;


public abstract class BaseFragment extends Fragment {

    protected AppPreferences mAppPreferences;
    private AppCompatActivity activity;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DatabaseFactory.setupObject(getContext());

        mAppPreferences = AppPreferences.getInstance(getContext());
        //wiseLiUser = mAppPreferences.getUserCashedInfo();
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

    public void showProgressBar(boolean visibility) {
/*        if (activity instanceof LoginSignUpActivity) {
            ((LoginSignUpActivity) getBaseActivity()).showProgressBar(visibility);
        } else if (activity instanceof LandingActivity) {
            ((LandingActivity) getBaseActivity()).showProgressBar(visibility);
        }*/
    }

/*
    public WiseLiUser getWiseLiUser() {
        return wiseLiUser;
    }
*/

    public AppCompatActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
