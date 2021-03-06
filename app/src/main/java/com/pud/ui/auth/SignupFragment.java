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

public class SignupFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.auth_signup_email)
    EditText mEmailEditText;

    @BindView(R.id.auth_signup_password)
    EditText mPasswordEditText;

    @BindView(R.id.auth_signup_name)
    EditText mNameEditText;

    @BindView(R.id.auth_signup_button)
    Button mSignupButton;

    private SignupListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SignupListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_signup, container, false);
        ButterKnife.bind(this, view);

        mSignupButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        boolean error = false;

        mEmailEditText.setError(null);
        mPasswordEditText.setError(null);
        mNameEditText.setError(null);

        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String name = mNameEditText.getText().toString().trim();

        if (email.length() == 0 || !email.endsWith("@purdue.edu")) {
            mEmailEditText.setError(getString(R.string.auth_error_email));
            error = true;
        }

        if (password.length() < 5) {
            mPasswordEditText.setError(getString(R.string.auth_error_short_password));
            error = true;
        }

        if (name.length() == 0) {
            mNameEditText.setError(getString(R.string.auth_error_field_required));
            error = true;
        }

        if (error) {
            Toast.makeText(getContext(), "Validate Error", Toast.LENGTH_SHORT).show();
        } else {
            mListener.onSignup(email, password, name);
        }
    }

    public interface SignupListener {
        void onSignup(String email, String password, String name);
    }

}
