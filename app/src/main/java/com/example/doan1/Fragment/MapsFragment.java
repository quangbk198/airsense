package com.example.doan1.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.doan1.MainActivity;
import com.example.doan1.Model.CustomMarker;
import com.example.doan1.Model.DataMqtt;
import com.example.doan1.Model.DialogMaps;
import com.example.doan1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    public ArrayList<Marker> markerArrayList = null;
    private int DEFAULT_ZOOM = 13;
    private View view;
    Marker _marker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        markerArrayList = new ArrayList<>();

        for(int i = 0; i < MainActivity.allNode.size(); i++) {
            for(int j = 0; j < DataMqtt.airInfoList.size(); j++) {
                if(DataMqtt.airInfoList.get(j).getID().equals(MainActivity.allNode.get(i).getID())){
                    int bt = (int) DataMqtt.airInfoList.get(j).getAQI();
                    _marker = mMap.addMarker(new MarkerOptions().position(new LatLng(MainActivity.allNode.get(i).getLatitude(),
                            MainActivity.allNode.get(i).getLongitude()))
                            .title(MainActivity.allNode.get(i).getAddress())
                            .icon(BitmapDescriptorFactory.fromBitmap(CustomMarker.createMarker(this.getContext(), String.valueOf(bt), j)))
                            .zIndex(i));
                    markerArrayList.add(_marker);
                    break;
                }
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(21.020502, 105.830849), DEFAULT_ZOOM));
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mMap.setMyLocationEnabled(true);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int index = (int) marker.getZIndex();
                DialogMaps.showDialog(index, getContext());
                return false;
            }
        });

    }

//    @Override
//    public boolean onMarkerClick(final Marker marker) {
//        if (marker.equals(_marker)) {
//            int index = (int) marker.getZIndex();
//            DialogMaps.showDialog(index, this.getContext());
//            return false;
//        }
//        return false;
//    }
}
