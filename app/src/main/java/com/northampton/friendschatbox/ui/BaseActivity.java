package com.northampton.friendschatbox.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.northampton.friendschatbox.R;


public abstract class BaseActivity extends AppCompatActivity {
    public FrameLayout mProgressBar;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
    }

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility) {
        if (visibility) {
            showProgressDialog();
        } else {
            dismissProgressDialog();
        }
    }

    public void showProgressDialog() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
