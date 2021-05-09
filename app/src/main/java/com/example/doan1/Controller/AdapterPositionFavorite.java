package com.example.doan1.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.Fragment.AllLocationFragment;
import com.example.doan1.Fragment.ListLocationFragment;
import com.example.doan1.MainActivity;
import com.example.doan1.Model.AQI_US;
import com.example.doan1.Model.DataMqtt;
import com.example.doan1.Object.Node;
import com.example.doan1.R;

import java.util.List;

public class AdapterPositionFavorite extends RecyclerView.Adapter<AdapterPositionFavorite.NodeFavViewHolder>{
    private Context context;
    private int layout;
    private List<Node> listPosition;

    public AdapterPositionFavorite(Context context, int layout, List<Node> listPosition) {
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

    public void setListLocaFav(List<Node> nodes) {
        this.listPosition = nodes;
        //if(AllLocationFragment.listFavLocation.size() != 0)
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NodeFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Nạp layout cho view biểu diễn phần tử
        View locationView = inflater.inflate(R.layout.item_list_postion_fav, parent, false);
        AdapterPositionFavorite.NodeFavViewHolder viewHolder =
                                            new AdapterPositionFavorite.NodeFavViewHolder(locationView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NodeFavViewHolder holder, int position) {
        Node node = listPosition.get(position);

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

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick == false) {
                    ListLocationFragment.checkClickItemLocaFav = true;
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

    public class NodeFavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{
        private View itemview;
        private ItemClickListener itemClickListener;

        public TextView tvPos, tvStatus, tvAQI, tvTem, tvHumi, tvPM25;
        public ImageView imgHealth;
        public LinearLayout lnList;

        public NodeFavViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            imgHealth = itemView.findViewById(R.id.imageviewHealthListFav);
            tvPos = itemView.findViewById(R.id.textviewLocationListFav);
            tvStatus = itemView.findViewById(R.id.textviewStatusListFav);
            tvAQI = itemView.findViewById(R.id.textviewAQIindexListFav);
            tvTem = itemView.findViewById(R.id.textviewTemListFav);
            tvHumi = itemView.findViewById(R.id.textviewHumiListFav);
            tvPM25 = itemView.findViewById(R.id.textviewPM25ListFav);
            lnList = itemView.findViewById(R.id.lnListFav);

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


        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}
