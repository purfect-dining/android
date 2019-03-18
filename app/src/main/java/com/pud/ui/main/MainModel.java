package com.pud.ui.main;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.pud.base.BaseModel;
import com.pud.model.DiningTiming;
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

    @Override
    public Observable<List<DiningTiming>> getOpenDiningTimingsBackendless(String date, String time) {
        return Observable.create(emitter -> {
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause("from <= '" + date + " " + time + ":00 EST' and to > '" + date + " " + time + ":00 EST' AND to < '" + date + " 23:59:59 EST'");
            queryBuilder.setSortBy("ofPlace.name");
            Backendless.Data.of(DiningTiming.class).find(queryBuilder, new AsyncCallback<List<DiningTiming>>() {
                @Override
                public void handleResponse(List<DiningTiming> response) {
                    emitter.onNext(response);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    emitter.onError(setError(fault.getMessage()));
                }
            });

        });
    }

}
