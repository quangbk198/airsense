package com.example.doan1.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.Controller.AdapterPositionFavorite;
import com.example.doan1.Controller.LocaFavViewModel;
import com.example.doan1.MainActivity;
import com.example.doan1.Object.Node;
import com.example.doan1.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteLocationFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private AdapterPositionFavorite adapter;
    private LocaFavViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(requireActivity()).get(LocaFavViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite_location, container, false);
        recyclerView = view.findViewById(R.id.RecyclePositionFav);
        //SetAdapterRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        model.getListLocaFav().observe(requireActivity(), new Observer<List<Node>>() {
            @Override
            public void onChanged(List<Node> nodes) {
                if(nodes != null) {
                    adapter = new AdapterPositionFavorite(getContext(), R.layout.item_list_postion_fav, MainActivity.listFavLocation);
                    recyclerView.setAdapter(adapter);
                }
                adapter.setListLocaFav(nodes);
            }
        });
    }

//        @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            SetAdapterRecyclerView();
//        }
//
//    }

    //Không cần dùng
    private void SetAdapterRecyclerView() {
        if(MainActivity.listFavLocation.size() != 0) {
            adapter = new AdapterPositionFavorite(this.getContext(), R.layout.item_list_postion_fav, MainActivity.listFavLocation);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setAdapter(adapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(linearLayoutManager);

        }
    }
}

























