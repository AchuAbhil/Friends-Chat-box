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

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.data.models.UserDetails;
import com.northampton.friendschatbox.databinding.FragmentSignUpBinding;
import com.northampton.friendschatbox.ui.BaseFragment;
import com.northampton.friendschatbox.ui.activity.MainActivity;

import java.util.Objects;

public class SignUpFragment extends BaseFragment {

    private FragmentSignUpBinding binding;
    private UserDetails userDetails;

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
        return binding.getRoot();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDetails = new UserDetails();
        getTextChangeListener();
        userDetails.setDateRegistered(getDateTime());
        userDetails.setDateUpdated(getDateTime());
        userDetails.setFriendsList("");
        userDetails.setFriendsRequestList("");
        registerBtnClick();
    }

    private void registerBtnClick() {
        binding.btnSignUp.setOnClickListener(v -> {
            getRegisterData();
        });
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

    private void getRegisterData() {
        if (binding.edtPassword.getEditText() != null &&
                binding.edtEmailID.getEditText() != null &&
                binding.edtFullName.getEditText() != null &&
                binding.edtHobbies.getEditText() != null
        ) {
            if (((MainActivity) requireActivity()).registerUser(userDetails)) {
                getAppPreferences().setUserInfo(userDetails);
                ((MainActivity) requireActivity()).navigateToLanding();
            } else {
                Toast.makeText(requireActivity(), "Register failed please see the values inputted.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
