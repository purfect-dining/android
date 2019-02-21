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

public class RecoverFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.auth_recover_email)
    EditText mEmailEditText;

    @BindView(R.id.auth_recover_button)
    Button mRecoverButton;

    private RecoverListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (RecoverListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_recover, container, false);
        ButterKnife.bind(this, view);

        mRecoverButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        boolean error = false;

        mEmailEditText.setError(null);

        String email = mEmailEditText.getText().toString().trim();

        if (email.length() == 0 || !email.endsWith("@purdue.edu")) {
            mEmailEditText.setError(getString(R.string.auth_error_email));
            error = true;
        }

        if (error) {
            Toast.makeText(getContext(), "Validate Error", Toast.LENGTH_SHORT).show();
        } else {
            mListener.onRecover(email);
        }
    }

    public interface RecoverListener {
        void onRecover(String email);
    }

}
