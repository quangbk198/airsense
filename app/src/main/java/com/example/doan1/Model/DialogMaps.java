package com.example.doan1.Model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan1.MainActivity;
import com.example.doan1.R;

public class DialogMaps {
    public static void showDialog(final int index, final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_details_info_air);

        Button dialogButton = dialog.findViewById(R.id.buttonViewDetails);
        TextView tvLocation = dialog.findViewById(R.id.textviewLocationMap);
        TextView tvAQI = dialog.findViewById(R.id.textviewAQIindexMap);
        TextView tvQuality = dialog.findViewById(R.id.texviewStatusMap);
        TextView tvTem = dialog.findViewById(R.id.textviewTemMap);
        TextView tvHumi = dialog.findViewById(R.id.textviewHumiMap);
        ImageView img = dialog.findViewById(R.id.imageviewHealthMap);
        final CheckBox cbFav = dialog.findViewById(R.id.checkboxFavoriteMap);

        tvLocation.setText(MainActivity.allNode.get(index).getAddress());
        for(int i = 0; i < DataMqtt.airInfoList.size(); i++) {
            if(MainActivity.allNode.get(index).getID().equals(DataMqtt.airInfoList.get(i).getID())) {
                tvAQI.setText(DataMqtt.airInfoList.get(i).getAQI() + "");
                tvQuality.setText(AQI_US.getMessage(DataMqtt.airInfoList.get(i).getAQI()));
                tvTem.setText(DataMqtt.airInfoList.get(i).getTemprature() + "");
                tvHumi.setText(DataMqtt.airInfoList.get(i).getHumidity() + "");
                tvLocation.setBackgroundResource(AQI_US.listColorForChart((int)DataMqtt.airInfoList.get(i).getAQI()));
                img.setImageResource(AQI_US.getAqiIcon(DataMqtt.airInfoList.get(i).getAQI()));
                break;
            }
        }

        cbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFav.isChecked()) {
                    Toast.makeText(context, R.string.favorite, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, R.string.un_favorite, Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.currentNode = index;
                MainActivity.tabLayout.getTabAt(2).select();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
