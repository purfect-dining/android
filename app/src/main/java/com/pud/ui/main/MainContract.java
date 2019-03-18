package com.pud.ui.main;

import com.pud.base.BaseContract;
import com.pud.model.DiningTiming;
import com.pud.model.Place;

import java.util.List;

import io.reactivex.Observable;

public interface MainContract {

    interface View extends BaseContract.View {
        void onPlacesReceived(List<Place> placeList);

        void onOpenDiningTimingsReceived(List<DiningTiming> diningTimingList);

        void onError(String message);
    }

    interface Model extends BaseContract.Model {
        Observable<List<Place>> placesBackendless();

        Observable<List<DiningTiming>> getOpenDiningTimingsBackendless(String date, String time);
    }

    interface Presenter extends BaseContract.Presenter {
        void getPlaces();

        void getOpenDiningTimings();
    }

}
