package com.pud.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pud.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.auth_login_email)
    EditText mEmailEditText;

    @BindView(R.id.auth_login_password)
    EditText mPasswordEditText;

    @BindView(R.id.auth_login_button)
    Button mLoginButton;

    @BindView(R.id.auth_login_signup_button)
    Button mSignupButton;

    @BindView(R.id.auth_login_recover_button)
    Button mRecoverButton;

    private LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_login, container, false);
        ButterKnife.bind(this, view);

        mLoginButton.setOnClickListener(this);
        mSignupButton.setOnClickListener(this);
        mRecoverButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auth_login_button:
                boolean error = false;

                mEmailEditText.setError(null);
                mPasswordEditText.setError(null);

                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                if (email.length() == 0 || !email.endsWith("@purdue.edu")) {
                    mEmailEditText.setError(getString(R.string.auth_error_email));
                    error = true;
                }

                if (password.length() < 5) {
                    mPasswordEditText.setError(getString(R.string.auth_error_short_password));
                    error = true;
                }

                if (error) {
                    Toast.makeText(getContext(), "Validate Error", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.onLogin(email, password);
                }
                break;

            case R.id.auth_login_signup_button:
                mListener.changeScreen(AuthActivity.AuthType.SIGNUP);
                break;

            case R.id.auth_login_recover_button:
                mListener.changeScreen(AuthActivity.AuthType.RECOVER);
                break;
        }
    }

    public interface LoginListener {
        void onLogin(String email, String password);

        void changeScreen(AuthActivity.AuthType to);
    }

}
