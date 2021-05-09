package com.example.doan1.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doan1.MainActivity;
import com.example.doan1.Model.AQI_US;
import com.example.doan1.Model.DataMqtt;
import com.example.doan1.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailsInformationFragment extends Fragment {
    TextView txtLocate, txtAQI, txtPM25, txtPM1, txtPM10, txtCO, txtTem, txtHumi, txtQuality;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);

        Initwidget();

        LoadData();

        drawChart();

        return view;
    }

    private void Initwidget() {
        txtLocate = view.findViewById(R.id.textviewLoca);
        txtAQI = view.findViewById(R.id.textviewAQIindex);
        txtPM25 = view.findViewById(R.id.textviewPM25);
        txtPM1 = view.findViewById(R.id.textviewPM1);
        txtPM10 = view.findViewById(R.id.textviewPM10);
        txtCO = view.findViewById(R.id.textviewCO);
        txtTem = view.findViewById(R.id.textviewTem);
        txtHumi = view.findViewById(R.id.textviewHumi);
        txtQuality = view.findViewById(R.id.textviewQuality);
    }

    private void LoadData() {
        int mIndex = MainActivity.currentNode;
        if(ListLocationFragment.checkClickItemLocaFav == true) {
            for(int i = 0; i < MainActivity.allNode.size(); i++) {
                if(MainActivity.listFavLocation.get(mIndex).getID().equals(MainActivity.allNode.get(i).getID())) {
                    txtLocate.setText(MainActivity.allNode.get(i).getAddress());
                    break;
                }
            }
            for(int i = 0; i < DataMqtt.airInfoList.size(); i++) {
                if (MainActivity.listFavLocation.get(mIndex).getID().equals(DataMqtt.airInfoList.get(i).getID())) {
                    txtAQI.setText(DataMqtt.airInfoList.get(i).getAQI() + "");
                    txtPM25.setText(DataMqtt.airInfoList.get(i).getPM2_5() + "");
                    txtPM1.setText(DataMqtt.airInfoList.get(i).getPM1() + "");
                    txtPM10.setText(DataMqtt.airInfoList.get(i).getPM10() + "");
                    txtTem.setText(DataMqtt.airInfoList.get(i).getTemprature() + "");
                    txtHumi.setText(DataMqtt.airInfoList.get(i).getHumidity() + "");
                    txtQuality.setText(AQI_US.getMessage(DataMqtt.airInfoList.get(i).getAQI()));
                    break;
                }
            }
        }

        else {
            txtLocate.setText(MainActivity.allNode.get(mIndex).getAddress());
            for(int i = 0; i < DataMqtt.airInfoList.size(); i++) {
                if(MainActivity.allNode.get(mIndex).getID().equals(DataMqtt.airInfoList.get(i).getID())) {
                    txtAQI.setText(DataMqtt.airInfoList.get(i).getAQI() + "");
                    txtPM25.setText(DataMqtt.airInfoList.get(i).getPM2_5() + "");
                    txtPM1.setText(DataMqtt.airInfoList.get(i).getPM1() + "");
                    txtPM10.setText(DataMqtt.airInfoList.get(i).getPM10() + "");
                    txtTem.setText(DataMqtt.airInfoList.get(i).getTemprature() + "");
                    txtHumi.setText(DataMqtt.airInfoList.get(i).getHumidity() + "");
                    txtQuality.setText(AQI_US.getMessage(DataMqtt.airInfoList.get(i).getAQI()));
                    break;
                }
            }
        }
    }

    private void drawChart() {
        LineChart lineChart = view.findViewById(R.id.lineChart);
        List<Entry> lineEntries = getDataset();
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Chỉ số AQI");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.YELLOW);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setCircleHoleRadius(2);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(lineDataSet);
        lineChart.getDescription().setText("AQI trong 24h qua");
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f);
        lineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
        lineChart.setData(lineData);
    }

    private List<Entry> getDataset() {
        List<Entry> list = new ArrayList<>();
        Random rd = new Random();
        for (int i = 0; i < 24; i++) {
            int random = 50 + rd.nextInt(100);
            list.add(new Entry(i, random));
        }
        return list;
    }
}













