package com.example.doan1.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.Controller.AdapterPosition;
import com.example.doan1.Controller.LocaFavViewModel;
import com.example.doan1.MainActivity;
import com.example.doan1.Object.Node;
import com.example.doan1.R;

import java.util.ArrayList;

public class AllLocationFragment extends Fragment {
    private View view;
    public static RecyclerView recyclerView;
    public static AdapterPosition adapter;
    public static SharedPreferences sharedCheckedLocaFav;

    public static LocaFavViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(requireActivity()).get(LocaFavViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_location, container, false);

        recyclerView = view.findViewById(R.id.recyclePosition);

        adapter = new AdapterPosition(this.getContext(), R.layout.item_list_postion, MainActivity.allNode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(linearLayoutManager);

        Log.d("sizeok", MainActivity.listFavLocation.size()+"");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model.setListLocaFav(MainActivity.listFavLocation);
    }
}







