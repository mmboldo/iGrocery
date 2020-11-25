package com.mycompany.igrocery;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycompany.igrocery.stores.GroceryStore;
import com.mycompany.igrocery.stores.SaveOnFoods;
import com.mycompany.igrocery.stores.Superstore;
import com.mycompany.igrocery.stores.Walmart;

public class StoreSelectionAdapter extends RecyclerView.Adapter<StoreSelectionAdapter.MyViewHolder> {

    String data1[];
    int images[];
    Context context;
    GroceryStore superstore = new GroceryStore("Real Canadian Superstore", "4700 Kingsway, Burnaby","49.22615","-122.999113");
    GroceryStore saveOnFoods = new GroceryStore("Save On Foods","4469 Kingsway, Burnaby","49.231050","-123.004530");
    GroceryStore walmart = new GroceryStore("Walmart","4545 Central Blvd, Burnaby","49.224490","-123.000000");

    public StoreSelectionAdapter(Context ct, String s1[], int img[]){
        context = ct;
        data1 = s1;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_storeselection, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(data1[position]);
        holder.iv.setImageResource(images[position]);

        holder.tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RouteStoreActivity.class);
                if(position == 0){
                    intent.putExtra("storeName", superstore.getName());
                    intent.putExtra("storeAddress", superstore.getAddress());
                    intent.putExtra("storeLatitude", superstore.getLatitude());
                    intent.putExtra("storeLongitude", superstore.getLongitude());
                }
                if(position == 1){
                    intent.putExtra("storeName", saveOnFoods.getName());
                    intent.putExtra("storeAddress", saveOnFoods.getAddress());
                    intent.putExtra("storeLatitude", saveOnFoods.getLatitude());
                    intent.putExtra("storeLongitude", saveOnFoods.getLongitude());
                }
                if(position == 2){
                    intent.putExtra("storeName", walmart.getName());
                    intent.putExtra("storeAddress", walmart.getAddress());
                    intent.putExtra("storeLatitude", walmart.getLatitude());
                    intent.putExtra("storeLongitude", walmart.getLongitude());;
                }
                //intent.putExtra("store", data1[position]);
                //Toast.makeText(context, data1[position], Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }

        });

        holder.iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RouteStoreActivity.class);
                if(position == 0){
                    intent.putExtra("storeName", superstore.getName());
                    intent.putExtra("storeAddress", superstore.getAddress());
                    intent.putExtra("storeLatitude", superstore.getLatitude());
                    intent.putExtra("storeLongitude", superstore.getLongitude());
                }
                if(position == 1){
                    intent.putExtra("storeName", saveOnFoods.getName());
                    intent.putExtra("storeAddress", saveOnFoods.getAddress());
                    intent.putExtra("storeLatitude", saveOnFoods.getLatitude());
                    intent.putExtra("storeLongitude", saveOnFoods.getLongitude());
                }
                if(position == 2){
                    intent.putExtra("storeName", walmart.getName());
                    intent.putExtra("storeAddress", walmart.getAddress());
                    intent.putExtra("storeLatitude", walmart.getLatitude());
                    intent.putExtra("storeLongitude", walmart.getLongitude());;
                }
                //intent.putExtra("store", data1[position]);
                //Toast.makeText(context, data1[position], Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvStore);
            iv = itemView.findViewById(R.id.ivStore);
        }
    }
}

