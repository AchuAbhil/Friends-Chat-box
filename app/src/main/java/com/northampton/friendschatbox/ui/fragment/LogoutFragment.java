package com.northampton.friendschatbox.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.FragmentLogoutBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.MainActivity;
import com.northampton.friendschatbox.utils.AppPreferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends BaseFragment {

    private FragmentLogoutBinding binding;

    public LogoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_logout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAppPreferences().clear();
        startActivity(new Intent(getBaseActivity(), MainActivity.class));
        getBaseActivity().finish();
    }
}
