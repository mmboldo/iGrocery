package com.mycompany.igrocery;

import android.content.Context;
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
    }



    @Override
    public int getItemCount() {
        return GroceryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle, itemDescription, itemQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.itemTitle);
            itemDescription = (TextView)itemView.findViewById(R.id.itemDescription);
            itemQuantity = (TextView)itemView.findViewById(R.id.itemQuantity);
        }
    }
}
