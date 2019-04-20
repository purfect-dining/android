package com.pud.ui.main;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.pud.base.BaseModel;
import com.pud.model.DiningTiming;
import com.pud.model.Place;

import java.util.List;
import java.util.Map;

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
//            queryBuilder.setWhereClause("from <= '" + date + " " + time + ":00 EST' and to > '" + date + " " + time + ":00 EST' AND to < '" + date + " 23:59:59 EST'");
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


    @Override
    public Observable<Double> getOpenRatingsBackendless(String date, String name) {
        return Observable.create(emitter -> {
            Log.e("KING", name);
            DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create().setProperties("Avg(rating) as calc");
            dataQueryBuilder.setWhereClause("ofDiningTiming.ofPlace.name = '" + name + "' and created > '" + date + " 00:00:00 EST'");
            Backendless.Data.of("Rating").find(dataQueryBuilder, new AsyncCallback<List<Map>>() {
                @Override
                public void handleResponse(List<Map> response) {
                    Log.e("KING", response.get(0).get("calc") + "");
                    emitter.onNext(Double.valueOf(String.valueOf(response.get(0).get("calc"))));
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    emitter.onError(setError(fault.getMessage()));
                }
            });
        });
    }

}
