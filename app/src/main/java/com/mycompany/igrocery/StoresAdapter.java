package com.mycompany.igrocery;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StoresAdapter extends BaseAdapter {
    List<Stores> StoreList;

    public StoresAdapter(List<Stores> storeList) {
        this.StoreList = storeList;
    }

    @Override
    public int getCount() {
        return StoreList.size();
    }

    @Override
    public Stores getItem(int position) {
        return StoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return StoreList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_spinneritem, parent, false);
        }
        TextView txtViewStoreName = convertView.findViewById(R.id.txtViewStoreName);
        ImageView imgViewStorePic = convertView.findViewById(R.id.imgStorePic);
        TextView txtViewStoreAddress = convertView.findViewById(R.id.txtViewStoreAddress);

        imgViewStorePic.setImageResource(StoreList.get(position).getStorePicDrawable());
        txtViewStoreName.setText(StoreList.get(position).getStoreName());
        txtViewStoreAddress.setText(StoreList.get(position).getStoreAddress());

        return convertView;
    }
}
