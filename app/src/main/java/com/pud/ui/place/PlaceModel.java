package com.pud.ui.place;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.base.BaseModel;
import com.pud.model.Place;

import io.reactivex.Observable;

public class PlaceModel extends BaseModel implements PlaceContract.Model {

    @Override
    public Observable<Place> getPlaceBackendless(String objectId) {
        return Observable.create(emitter ->
                Backendless.Data.of(Place.class).findById(objectId, new AsyncCallback<Place>() {
                    @Override
                    public void handleResponse(Place response) {
                        emitter.onNext(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        emitter.onError(setError(fault.getMessage()));
                    }
                }));
    }

}
