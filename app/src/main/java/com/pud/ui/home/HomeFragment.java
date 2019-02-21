package com.pud.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pud.R;
import com.pud.model.Place;
import com.pud.ui.main.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements MainActivity.DataListener<List<Place>> {

    @BindView(R.id.home_place_list)
    RecyclerView mList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).setHomeDataListener(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(view);

        return view;
    }

    @Override
    public void onDataReceived(List<Place> data) {
        PlaceAdapter itemArrayAdapter = new PlaceAdapter(getContext(), data);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(itemArrayAdapter);
    }
}
