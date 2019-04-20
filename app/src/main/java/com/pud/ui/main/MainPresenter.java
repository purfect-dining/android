package com.pud.ui.main;

import android.util.Log;

import com.pud.model.DiningTiming;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainModel mModel;
    private CompositeDisposable mCompositeDisposable;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        mModel = new MainModel();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onCreate() {
        mModel.onCreate();
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
        mModel.onDestroy();
    }

    @Override
    public void getPlaces() {
        Disposable disposable = mModel.placesBackendless().subscribe(places -> mView.onPlacesReceived(places),
                throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getOpenDiningTimings() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);

        Disposable disposable = mModel.getOpenDiningTimingsBackendless(dateFormat.format(cal.getTime()), timeFormat.format(cal.getTime())).subscribe(
                diningTimingList -> getCurrentRatings(dateFormat.format(cal.getTime()), diningTimingList),
                throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

    private void getCurrentRatings(String date, List<DiningTiming> diningTimingList) {
        List<Double> ratingList = new ArrayList<>();

        Disposable disposable = getNames(diningTimingList)
                .flatMapIterable(ids -> ids)
                .flatMap((Function<String, ObservableSource<Double>>) name -> mModel.getOpenRatingsBackendless(date, name))
                .subscribe(rating -> {
                            ratingList.add(rating);
                            if (ratingList.size() == diningTimingList.size())
                                mView.onOpenDiningTimingsReceived(diningTimingList, ratingList);
                        },
                        throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

    private Observable<List<String>> getNames(List<DiningTiming> diningTimingList) {
        List<String> names = new ArrayList<>();
        for (DiningTiming timing : diningTimingList) {
            names.add(timing.getOfPlace().getName());
        }
        Log.e("KING", names.toString());

        return Observable.just(names);
    }
}
