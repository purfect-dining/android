package com.pud.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.pud.R;
import com.pud.listener.RecyclerItemClickListener;
import com.pud.model.Comment;
import com.pud.ui.comment.CommentAdapter;
import com.pud.ui.comment.ModCommentActivity;
import com.pud.ui.main.MainActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements UserContract.View, RecyclerItemClickListener.OnItemClickListener {

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_name)
    TextView nameTextView;

    @BindView(R.id.user_email)
    TextView emailTextView;

    @BindView(R.id.user_password)
    TextView passwordTextView;

    @BindView(R.id.user_comment_list)
    RecyclerView mCommentList;

    private UserPresenter mPresenter;
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        mPresenter = new UserPresenter(this);
        mPresenter.onCreate();
        mPresenter.getUserComments();

        nameTextView.setText(Backendless.UserService.CurrentUser().getProperty("name").toString());
        emailTextView.setText(Backendless.UserService.CurrentUser().getEmail());
        passwordTextView.setText(Backendless.UserService.CurrentUser().getPassword());

        mCommentList.setLayoutManager(new LinearLayoutManager(this));
        mCommentList.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
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
                mPresenter.updateUser(nameTextView.getText().toString().trim(),
                        emailTextView.getText().toString(),
                        passwordTextView.getText().toString());
                break;

            case R.id.user_menu_logout:
                mPresenter.logoutUser();
        }
        return true;
    }

    @Override
    public void onUserUpdated() {

    }

    @Override
    public void onUserLoggedOut() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onCommentsLoaded(List<Comment> commentList) {
        Toast.makeText(this, "GOT", Toast.LENGTH_SHORT).show();
        mAdapter = new CommentAdapter(this, commentList);
        mCommentList.setAdapter(mAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Comment comment = mAdapter.getList().get(position);
        mAdapter.getList().remove(position);

        Intent intent = new Intent(this, ModCommentActivity.class);
        intent.putExtra("comment_id", comment.getObjectId());
        intent.putExtra("comment_edit", true);
        intent.putExtra("comment_text", comment.getText());
        startActivityForResult(intent, 433);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.getUserComments();
    }
}
