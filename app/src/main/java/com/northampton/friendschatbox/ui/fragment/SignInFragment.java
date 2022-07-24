package com.northampton.friendschatbox.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.FragmentSignInBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.MainActivity;
import com.northampton.friendschatbox.utils.AppPreferences;

import java.util.Objects;

public class SignInFragment extends BaseFragment {

    AppPreferences mAppPreferences;
    private FragmentSignInBinding binding;

    public SignInFragment() {
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
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppPreferences = AppPreferences.getInstance(getContext());
        validatePassword();
        validateUserName();
        binding.tvSignedUser.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
        });
        binding.btnLogin.setOnClickListener(v -> {
            if (((MainActivity) requireActivity()).loginCheck(
                    Objects.requireNonNull(binding.edtUsername.getEditText()).getText().toString(),
                    Objects.requireNonNull(binding.edtPassword.getEditText()).getText().toString()
            )) {
                ((MainActivity) requireActivity()).navigateToLanding();
            }
        });
    }

    private void validateUserName() {
        binding.edtUsername.setHelperText(getString(R.string.empty_string));
        binding.edtUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    binding.edtUsername.setHelperText(getString(R.string.empty_string));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    binding.edtUsername.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validatePassword() {
        binding.edtPassword.setHelperText(getString(R.string.empty_string));
        binding.edtPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    binding.edtPassword.setHelperText(getString(R.string.empty_string));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    binding.edtPassword.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
