package com.northampton.friendschatbox.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.FragmentSplashScreenBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.utils.AppPreferences;


public class SplashScreenFragment extends BaseFragment {

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
        //Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signInFragment);

/*        if (userDetails != null && userDetails.getFirstName() != null) {
            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signInFragment);
        } else {
            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signInFragment);
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
