package com.northampton.friendschatbox.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.northampton.friendschatbox.R;
import com.northampton.friendschatbox.databinding.FragmentSearchFriendsBinding;
import com.northampton.friendschatbox.ui.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFriendsFragment extends BaseFragment {

    private FragmentSearchFriendsBinding binding;

    public SearchFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchFriendsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_friends_request;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
