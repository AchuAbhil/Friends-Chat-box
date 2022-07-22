package com.northampton.friendschatbox.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.FragmentSignUpBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.MainActivity;
import com.northampton.friendschatbox.utils.AppPreferences;

public class SignUpFragment extends BaseFragment {

    AppPreferences mAppPreferences;
    private FragmentSignUpBinding binding;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppPreferences = AppPreferences.getInstance(getContext());
        binding.btnSignUp.setOnClickListener(v -> {
            ((MainActivity) getActivity()).navigateToLanding();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
