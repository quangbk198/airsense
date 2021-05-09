package com.example.doan1.Controller;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doan1.MainActivity;
import com.example.doan1.Object.Node;

import java.util.List;

public class LocaFavViewModel extends ViewModel {
    private MutableLiveData<List<Node>> listLocaFav;

    public void setListLocaFav(List<Node> nodes) {
        if(listLocaFav == null) {
            listLocaFav = new MutableLiveData<>();
            listLocaFav.setValue(nodes);
        }
        else {
            listLocaFav.setValue(nodes);
        }
    }

    public MutableLiveData<List<Node>> getListLocaFav() {
        if(listLocaFav == null) {
            listLocaFav = new MutableLiveData<>();
            listLocaFav.setValue(MainActivity.listFavLocation);
        }
        return listLocaFav;
    }
}
