package com.northampton.friendschatbox.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.FragmentLogoutBinding;
import com.northampton.friendschatbox.databinding.FragmentProfileBinding;
import com.northampton.friendschatbox.ui.BaseFragment;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;
    UserDetails userDetails;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {

        userDetails = new UserDetails();
        userDetails = getUserDetails();

        Objects.requireNonNull(binding.edtFullName.getEditText()).setText(userDetails.getFullName());
        Objects.requireNonNull(binding.edtEmailID.getEditText()).setText(userDetails.getEmailAddress());
        Objects.requireNonNull(binding.edtPassword.getEditText()).setText(userDetails.getPassword());
        Objects.requireNonNull(binding.edtHobbies.getEditText()).setText(userDetails.getHobbies());
    }
}
