package com.pud.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.R;
import com.pud.model.Place;
import com.pud.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements UserContract.View {

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_name)
    TextView nameTextView;

    @BindView(R.id.user_email)
    TextView emailTextView;

    @BindView(R.id.user_password)
    TextView passwordTextView;

    private UserPresenter mPresenter;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        mPresenter = new UserPresenter(this);
        mPresenter.onCreate();

        nameTextView.setText(Backendless.UserService.CurrentUser().getProperty("name").toString());
        emailTextView.setText(Backendless.UserService.CurrentUser().getEmail());
        passwordTextView.setText(Backendless.UserService.CurrentUser().getPassword());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.user_menu_edit:
                BackendlessUser user = Backendless.UserService.CurrentUser();
                user.setProperty("name", nameTextView.getText().toString());
                user.setEmail(emailTextView.getText().toString());
                user.setPassword(passwordTextView.getText().toString());

                if (passwordTextView.getText().toString().length() > 0) {
                    Backendless.UserService.logout();
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
                Backendless.Data.save(user, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(UserActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(UserActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.user_menu_logout:
                Backendless.UserService.logout();
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
        }
        return true;
    }

}
