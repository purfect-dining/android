package com.pud.ui.main;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.base.BaseModel;
import com.pud.model.Place;

import java.util.List;

import io.reactivex.Observable;

public class MainModel extends BaseModel implements MainContract.Model {

    @Override
    public Observable<List<Place>> placesBackendless() {
        return Observable.create(emitter ->
                Backendless.Data.of(Place.class).find(new AsyncCallback<List<Place>>() {
                    @Override
                    public void handleResponse(List<Place> response) {
                        emitter.onNext(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        emitter.onError(setError(fault.getMessage()));
                    }
                }));
    }

}
