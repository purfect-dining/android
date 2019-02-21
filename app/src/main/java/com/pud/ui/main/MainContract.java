package com.pud.ui.main;

import com.pud.base.BaseContract;
import com.pud.model.Place;

import java.util.List;

import io.reactivex.Observable;

public interface MainContract {

    interface View extends BaseContract.View {
        void onPlacesReceived(List<Place> placeList);

        void onError(String message);
    }

    interface Model extends BaseContract.Model {
        Observable<List<Place>> placesBackendless();
    }

    interface Presenter extends BaseContract.Presenter {
        void getPlaces();
    }

}
