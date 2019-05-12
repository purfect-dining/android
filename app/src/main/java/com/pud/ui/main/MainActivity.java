package com.pud.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.pud.R;
import com.pud.listener.RecyclerItemClickListener;
import com.pud.model.DiningTiming;
import com.pud.model.Place;
import com.pud.ui.auth.AuthActivity;
import com.pud.ui.place.PlaceActivity;
import com.pud.ui.user.UserActivity;
import com.webianks.easy_feedback.EasyFeedback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View, RecyclerItemClickListener.ClickListener {

    @BindView(R.id.main_open_list)
    RecyclerView mOpenList;

    @BindView(R.id.main_place_list)
    RecyclerView mList;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    private MainPresenter mPresenter;
    private DiningTimingAdapter mAdapter;
    private List<DiningTiming> timings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences dataSave = getSharedPreferences("app", 0);
        if (dataSave.getBoolean("firstRun", true)) {
            SharedPreferences.Editor editor = dataSave.edit();
            editor.putBoolean("firstRun", false);
            editor.apply();

            Intent intent = new Intent(this, IntroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        mPresenter = new MainPresenter(this);
        mPresenter.onCreate();
        mPresenter.getOpenDiningTimings();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onPlacesReceived(List<Place> placeList) {
        List<Place> wowlist = new ArrayList<>();
        for (DiningTiming timing : timings) {
            wowlist.add(timing.getOfPlace());
        }
        placeList.removeAll(wowlist);
        PlaceAdapter ff = new PlaceAdapter(this, placeList);
        mList.setLayoutManager(new LinearLayoutManager(this));
//        mList.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        mList.setAdapter(ff);
    }

    @Override
    public void onOpenDiningTimingsReceived(List<DiningTiming> diningTimingList, List<Double> ratingList) {
        Log.e("KING", diningTimingList.size() + "  -  " + ratingList.size());
        List<Temp> temps = new ArrayList<>();
        for (int i = 0; i < diningTimingList.size(); i++) {
            temps.add(new Temp(diningTimingList.get(i), ratingList.get(i)));
        }

        for (int i = 0; i < temps.size(); i++) {
            for (int j = 1; j < (temps.size() - i); j++) {
                if (temps.get(j).rating < temps.get(j - 1).rating) {
                    Temp temp = temps.get(j);
                    temps.set(j, temps.get(j - 1));
                    temps.set(j - 1, temp);
                }
            }
        }

//        for (int i = 0; i < ratingList.size(); i++) {
//            for (int j = 0; j < ratingList.size() - 1; j++) {
//                if (ratingList.get(j) < ratingList.get(j + 1)) {
//                    double temp = ratingList.get(j);
//                    ratingList.set(j, ratingList.get(j + 1));
//                    ratingList.set(j + 1, temp);
//
//                    DiningTiming tempd = diningTimingList.get(j);
//                    diningTimingList.set(j, diningTimingList.get(j + 1));
//                    diningTimingList.set(j + 1, tempd);
//                }
//            }
//        }

        diningTimingList.clear();
        ratingList.clear();

        for (int i = 0; i < temps.size(); i++) {
            diningTimingList.add(temps.get(i).diningTiming);
            ratingList.add(temps.get(i).rating);
        }

        mPresenter.getPlaces(); // get closed
        timings = diningTimingList;
        Log.e("KING", diningTimingList.size() + "  -  " + ratingList.size());
        mAdapter = new DiningTimingAdapter(this, diningTimingList, ratingList);
        mOpenList.setLayoutManager(new LinearLayoutManager(this));
        mOpenList.addOnItemTouchListener(new RecyclerItemClickListener(this, mOpenList, this));
        mOpenList.setAdapter(mAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if (Backendless.UserService.CurrentUser() == null) {
            getMenuInflater().inflate(R.menu.main_toolbar, menu);
        } else {
            getMenuInflater().inflate(R.menu.main_user_toolbar, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.main_menu_login:
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.main_menu_profile:
                Intent intent1 = new Intent(this, UserActivity.class);
                startActivity(intent1);
                break;
            case R.id.main_menu_feedback:
                new EasyFeedback.Builder(this)
                        .withEmail("pulse.aashir@gmail.com")
                        .withSystemInfo()
                        .build()
                        .start();
                Toast.makeText(this, "FEEDBACK SENT", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("objectId", mAdapter.getList().get(position).getOfPlace().getObjectId());
        intent.putExtra("name", mAdapter.getList().get(position).getOfPlace().getName());
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    class Temp {
        public DiningTiming diningTiming;
        public double rating;

        public Temp(DiningTiming d, double r) {
            this.diningTiming = d;
            this.rating = r;
        }
    }
}
