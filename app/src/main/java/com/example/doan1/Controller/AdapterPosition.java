package com.example.doan1.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.Fragment.AllLocationFragment;
import com.example.doan1.Fragment.ListLocationFragment;
import com.example.doan1.MainActivity;
import com.example.doan1.Model.AQI_US;
import com.example.doan1.Model.DataMqtt;
import com.example.doan1.Object.Node;
import com.example.doan1.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterPosition extends RecyclerView.Adapter<AdapterPosition.NodeViewHolder> {
    private Context context;
    private int layout;
    private List<Node> listPosition;

    public AdapterPosition(Context context, int layout, List<Node> listPosition) {
        this.context = context;
        this.layout = layout;
        this.listPosition = listPosition;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Node> getListPosition() {
        return listPosition;
    }

    public void setListPosition(List<Node> listPosition) {
        this.listPosition = listPosition;
    }

    @NonNull
    @Override
    public NodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Nạp layout cho view biểu diễn phần tử
        View locationView = inflater.inflate(R.layout.item_list_postion, parent, false);
        NodeViewHolder viewHolder = new NodeViewHolder(locationView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NodeViewHolder holder, int position) {
        final Node node = listPosition.get(position);
        holder.tvPos.setText(node.getAddress());

        for(int i = 0; i < DataMqtt.airInfoList.size(); i++) {
            if(DataMqtt.airInfoList.get(i).getID().equals(node.getID())) {
                holder.tvPos.setBackgroundResource(AQI_US.listColorForChart((int)DataMqtt.airInfoList.get(i).getAQI()));
                holder.lnList.setBackgroundResource(AQI_US.listColorForChart((int)DataMqtt.airInfoList.get(i).getAQI()));
                holder.tvAQI.setText(DataMqtt.airInfoList.get(i).getAQI() + "");
                holder.imgHealth.setImageResource(AQI_US.getAqiIcon(DataMqtt.airInfoList.get(i).getAQI()));
                holder.tvStatus.setText(AQI_US.getMessage(DataMqtt.airInfoList.get(i).getAQI()));
                holder.tvTem.setText(DataMqtt.airInfoList.get(i).getTemprature() + "");
                holder.tvHumi.setText(DataMqtt.airInfoList.get(i).getHumidity() + "");
                holder.tvPM25.setText(DataMqtt.airInfoList.get(i).getPM2_5() + "");
            }
        }

        holder.cbfavList.setChecked(listPosition.get(position).getChecked());
        holder.cbfavList.setTag(position);

        Integer _pos = (Integer) holder.cbfavList.getTag();
        for(int i = 0; i < MainActivity.listFavLocation.size(); i++) {
            if(MainActivity.listFavLocation.get(i).getID().equals(node.getID())) {
                holder.cbfavList.setChecked(true);
                listPosition.get(_pos).setChecked(true);
            }
        }

        holder.cbfavList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.cbfavList.getTag();

                if (listPosition.get(pos).getChecked()) {
                    listPosition.get(pos).setChecked(false);
                    Toast.makeText(context, R.string.un_favorite, Toast.LENGTH_SHORT).show();

                    if (holder.cbfavList.isChecked() == false) {
                        //MainActivity.listFavLocation.remove(MainActivity.allNode.get(pos));
                        for(int i = 0; i < MainActivity.listFavLocation.size(); i++) {
                            if(MainActivity.listFavLocation.get(i).getAddress().equals(
                                    MainActivity.allNode.get(pos).getAddress())) {
                                MainActivity.listFavLocation.remove(MainActivity.listFavLocation.get(i));
                                break;
                            }
                        }
                        AllLocationFragment.model.setListLocaFav(MainActivity.listFavLocation);
                        saveListLocaFav();
                        Log.d("sizeok", MainActivity.listFavLocation.size()+" unlist " +
                                MainActivity.allNode.get(pos));



                    }

                } else {
                    listPosition.get(pos).setChecked(true);
                    Toast.makeText(context, R.string.favorite, Toast.LENGTH_SHORT).show();
                    MainActivity.listFavLocation.add(MainActivity.allNode.get(pos));
                    AllLocationFragment.model.setListLocaFav(MainActivity.listFavLocation);
                    saveListLocaFav();
                    Log.d("sizeok", MainActivity.listFavLocation.size()+"addlist");
                }
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick == false) {
                    ListLocationFragment.checkClickItemLocaFav = false;
                    MainActivity.currentNode = position;
                    MainActivity.tabLayout.getTabAt(2).select();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPosition.size();
    }

    private void saveListLocaFav() {        //Lưu danh sách địa điểm yêu thích
        SharedPreferences sharedLocaFav = getContext().getSharedPreferences("DataLocaFav", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedLocaFav.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.listFavLocation);
        editor.putString("listlocafav", json);
        editor.commit();
    }

    public class NodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemview;
        private ItemClickListener itemClickListener;

        public TextView tvPos, tvStatus, tvAQI, tvTem, tvHumi, tvPM25;
        public ImageView imgHealth;
        public LinearLayout lnList;
        public CheckBox cbfavList;

        public NodeViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemview = itemView;
            imgHealth = itemView.findViewById(R.id.imageviewHealthList);
            tvPos = itemView.findViewById(R.id.textviewLocationList);
            tvStatus = itemView.findViewById(R.id.textviewStatusList);
            tvAQI = itemView.findViewById(R.id.textviewAQIindexList);
            tvTem = itemView.findViewById(R.id.textviewTemList);
            tvHumi = itemView.findViewById(R.id.textviewHumiList);
            tvPM25 = itemView.findViewById(R.id.textviewPM25List);
            lnList = itemView.findViewById(R.id.lnList);
            cbfavList = itemView.findViewById(R.id.checkboxFavoriteList);

            itemView.setOnClickListener(this);


        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }

    }
}




















