package com.pud.ui.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pud.R;
import com.pud.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity implements AuthContract.View, LoginFragment.LoginListener,
        SignupFragment.SignupListener, RecoverFragment.RecoverListener {

    @BindView(R.id.main_content)
    FrameLayout mContentView;

    @BindView(R.id.main_progress)
    View mProgressView;

    private AuthPresenter mPresenter;
    private List<Fragment> mFragments;

    private AuthType mCurrentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ButterKnife.bind(this);

        mPresenter = new AuthPresenter(this);
        mPresenter.onCreate();

        mFragments = new ArrayList<>();
        mFragments.add(new LoginFragment());
        mFragments.add(new SignupFragment());
        mFragments.add(new RecoverFragment());

        replaceFragment(AuthType.LOGIN);
    }

    private void showProgress(boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        mContentView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onSuccess(AuthType type, String message) {
        showProgress(false);
        Toast.makeText(this, "Success: " + message, Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent1);
    }

    @Override
    public void onError(String message) {
        showProgress(false);
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogin(String email, String password) {
        mPresenter.login(email, password);
    }

    @Override
    public void onRecover(String email) {
        mPresenter.recover(email);
    }

    @Override
    public void onSignup(String email, String password, String name) {
        mPresenter.signup(email, password, name);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentType != AuthType.LOGIN) {
            replaceFragment(AuthType.LOGIN);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void changeScreen(AuthType to) {
        replaceFragment(to);
    }

    public void replaceFragment(AuthType type) {
        mCurrentType = type;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, mFragments.get(type.get()));
        fragmentTransaction.commit();
    }

    public enum AuthType {
        LOGIN(0), SIGNUP(1), RECOVER(2);

        private int numVal;

        AuthType(int numVal) {
            this.numVal = numVal;
        }

        public int get() {
            return numVal;
        }
    }

}

