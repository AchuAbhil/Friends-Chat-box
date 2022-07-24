package com.northampton.friendschatbox.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.FragmentSplashScreenBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.LandingActivity;
import com.northampton.friendschatbox.utils.AppPreferences;


public class SplashScreenFragment extends BaseFragment {

    public static final Long SPLASH_SCREEN_TIME_OUT = 1000L;
    AppPreferences mAppPreferences;
    private FragmentSplashScreenBinding binding;

    public SplashScreenFragment() {
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
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_splash_screen;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppPreferences = AppPreferences.getInstance(getContext());
        checkIfUserLoggedIn(view);
    }

    private void checkIfUserLoggedIn(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (getUserDetails() != null && getUserDetails().getFullName() != null) {
                Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signInFragment);
            } else {
                startActivity(new Intent(requireActivity(), LandingActivity.class));
                requireActivity().finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
