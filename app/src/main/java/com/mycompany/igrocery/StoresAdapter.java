package com.mycompany.igrocery;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {
    List<Stores> storesList = new ArrayList<>();
    Context context;

    public StoresAdapter(List<Stores> storesList, Context context) {
        this.storesList = storesList;
        this.context = context;
    }

    //method to change the adapter data
    public void ChangeData(List<Stores> newStoreList) {
        this.storesList = newStoreList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_storeitem, parent, false);
        final StoresViewHolder myHolder = new StoresViewHolder(itemView);

        myHolder.storeName = itemView.findViewById(R.id.storeNameTitle);
        myHolder.storePic = itemView.findViewById(R.id.imgViewStore);
        myHolder.storeDetails = itemView.findViewById(R.id.showMapBtn);
        myHolder.storeItemView = itemView;

        myHolder.storeDetails = itemView.findViewById(R.id.showMapBtn);
        myHolder.storeDetails.setBackgroundColor(Color.parseColor("#458b74"));
        //set the btn to show the fragment

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoresViewHolder holder, int position) {
        holder.storeName.setText(storesList.get(position).getStoreName());
        holder.storePic.setImageResource(storesList.get(position).getStorePic());
    }

    @Override
    public int getItemCount() {
        return storesList.size();
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        ImageView storePic;
        Button storeDetails;
        View storeItemView;

        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
