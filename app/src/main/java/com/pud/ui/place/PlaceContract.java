package com.pud.ui.place;

import com.pud.base.BaseContract;
import com.pud.model.Place;

import io.reactivex.Observable;

public interface PlaceContract {

    interface View extends BaseContract.View {
        void onPlaceLoaded(Place place);

        void onError(String message);
    }

    interface Model extends BaseContract.Model {
        Observable<Place> getPlaceBackendless(String objectId);
    }

    interface Presenter extends BaseContract.Presenter {
        void getPlace(String objectId);
    }

}
