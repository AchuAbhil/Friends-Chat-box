package com.northampton.friendschatbox.ui.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.FragmentProfileBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.LandingActivity;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;
    private String currentEmailAddress = "";
    private UserDetails userDetails;

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
        init();
        getTextChangeListener();
        registerBtnClick();
    }

    private void init() {
        userDetails = new UserDetails();
        userDetails = getUserDetails();
        currentEmailAddress = userDetails.getEmailAddress();
        updateUIDetails(userDetails);
        userDetails.setDateUpdated(getDateTime());
    }

    private void updateUIDetails(UserDetails userDetail) {
        Objects.requireNonNull(binding.edtFullName.getEditText()).setText(userDetail.getFullName());
        Objects.requireNonNull(binding.edtEmailID.getEditText()).setText(userDetail.getEmailAddress());
        Objects.requireNonNull(binding.edtPassword.getEditText()).setText(userDetail.getPassword());
        Objects.requireNonNull(binding.edtHobbies.getEditText()).setText(userDetail.getHobbies());
        Objects.requireNonNull(binding.edtRegisterDate.getEditText()).setText(userDetail.getDateRegistered());
        Objects.requireNonNull(binding.edtLastUpdated.getEditText()).setText(userDetail.getDateUpdated());
    }

    private void registerBtnClick() {
        binding.btnSignUp.setOnClickListener(v -> {
            updateUserData();
        });
    }

    private void updateUserData() {
        if (binding.edtPassword.getEditText() != null &&
                binding.edtEmailID.getEditText() != null &&
                binding.edtFullName.getEditText() != null &&
                binding.edtHobbies.getEditText() != null
        ) {
            if(((LandingActivity) requireActivity()).updateDBUserDetails(currentEmailAddress, userDetails)){
                Toast.makeText(requireActivity(), "Update Successful.", Toast.LENGTH_LONG).show();
                Objects.requireNonNull(binding.edtLastUpdated.getEditText()).setText(userDetails.getDateUpdated());
                getAppPreferences().setUserInfo(userDetails);
            }else {
                Toast.makeText(requireActivity(), "Update failed please see the DB values inputted.", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(requireActivity(), "Update failed please see the text values inputted.", Toast.LENGTH_LONG).show();
        }
    }

    private void getTextChangeListener() {
        validateFullName();
        validatePassword();
        validateEmailId();
        validateHobbies();
    }

    private void validateFullName() {
        binding.edtFullName.setHelperText(getString(R.string.empty_string));
        Objects.requireNonNull(binding.edtFullName.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    binding.edtFullName.setHelperText(getString(R.string.empty_string));
                    userDetails.setFullName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    binding.edtFullName.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validatePassword() {
        binding.edtPassword.setHelperText(getString(R.string.empty_string));
        Objects.requireNonNull(binding.edtPassword.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    binding.edtPassword.setHelperText(getString(R.string.empty_string));
                    userDetails.setPassword(s.toString());
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

    private void validateEmailId() {
        binding.edtEmailID.setHelperText(getString(R.string.empty_string));
        Objects.requireNonNull(binding.edtEmailID.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    binding.edtEmailID.setHelperText(getString(R.string.empty_string));
                    userDetails.setEmailAddress(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    binding.edtEmailID.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validateHobbies() {
        binding.edtHobbies.setHelperText(getString(R.string.empty_string));
        Objects.requireNonNull(binding.edtHobbies.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    binding.edtHobbies.setHelperText(getString(R.string.empty_string));
                    userDetails.setHobbies(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    binding.edtHobbies.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }
}
