package com.mycompany.igrocery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.MyViewHolder> {

    Context context;
    ArrayList<GroceryList> GroceryList;

    public GroceryListAdapter(Context c, ArrayList<GroceryList> p) {
        context = c;
        GroceryList = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.grocery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemTitle.setText(GroceryList.get(position).getItemTitle());
        holder.itemDescription.setText(GroceryList.get(position).getItemDescription());
        holder.itemQuantity.setText(GroceryList.get(position).getItemQuantity());

        String getItemTitle = GroceryList.get(position).getItemTitle();
        String getItemDescription = GroceryList.get(position).getItemDescription();
        String getItemQuantity = GroceryList.get(position).getItemQuantity();
        String getItemKey = GroceryList.get(position).getItemKey();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditListItem.class);
                Intent userIntent = ((Activity)v.getContext()).getIntent();
                String listOwner = userIntent.getStringExtra("listOwner");
                intent.putExtra("listOwner", listOwner);

                //Intent intent = new Intent(context, EditListItem.class);
                intent.putExtra("itemTitle", getItemTitle);
                intent.putExtra("itemDescription", getItemDescription);
                intent.putExtra("itemQuantity", getItemQuantity);
                intent.putExtra("itemKey", getItemKey);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return GroceryList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle, itemDescription, itemQuantity, itemKey;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.itemTitle);
            itemDescription = (TextView)itemView.findViewById(R.id.itemDescription);
            itemQuantity = (TextView)itemView.findViewById(R.id.itemQuantity);
        }
    }
}
