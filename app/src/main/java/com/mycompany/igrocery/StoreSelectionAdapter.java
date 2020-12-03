package com.mycompany.igrocery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mycompany.igrocery.stores.GroceryStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StoreSelectionAdapter extends RecyclerView.Adapter<StoreSelectionAdapter.MyViewHolder> {

    String data1[];
    int images[];
    String latitude;
    String longitude;
    Context context;
    GroceryStore superstore = new GroceryStore("Real Canadian Superstore", "4700 Kingsway, Burnaby", "49.22615", "-122.999113");
    GroceryStore saveOnFoods = new GroceryStore("Save On Foods", "4469 Kingsway, Burnaby", "49.231050", "-123.004530");
    GroceryStore walmart = new GroceryStore("Walmart", "4545 Central Blvd, Burnaby", "49.224490", "-123.000000");
    List<GroceryStore> list = new ArrayList<GroceryStore>(Arrays.asList(superstore, saveOnFoods, walmart));
    private AlertDialog dialog;


    public StoreSelectionAdapter(Context ct, String s1[], int img[]) {
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


//        holder.tv.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                if(position == 0){
//                    latitude = superstore.getLatitude();
//                    longitude = superstore.getLongitude();
//                }
//                if(position == 1){
//                    latitude = saveOnFoods.getLatitude();
//                    longitude = saveOnFoods.getLongitude();
//
//                }
//                if(position == 2){
//                    latitude = walmart.getLatitude();
//                    longitude = walmart.getLongitude();
//
//                }
//
//                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                context.startActivity(mapIntent);
//            }
//
//        });

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (position == 0) {
                    showDialog(position);
                }
                if (position == 1) {
                    showDialog(position);
                }
                if (position == 2) {
                    showDialog(position);
                }
                //intent.putExtra("store", data1[position]);
                //Toast.makeText(context, data1[position], Toast.LENGTH_LONG).show();

            }

        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvStore);
            iv = itemView.findViewById(R.id.ivStore);
        }
    }

    public void showDialog(int position) {

        //AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GPS Service");
        builder.setIcon(R.drawable.logo);
        builder.setMessage("Choose one of the following options:");
        builder.setPositiveButton("Google Map", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                addExtra(position);
            }
        });
        builder.setNegativeButton("iGrocery Map", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                initialGoogleMap(position);
            }
        });

        dialog = builder.create();

        dialog.show();
    }

    //iGrocery Map
    public void addExtra(int position) {
        Intent intent = new Intent(context, RouteStoreActivity.class);
        intent.putExtra("storeName", list.get(position).getName());
        intent.putExtra("storeAddress", list.get(position).getAddress());
        intent.putExtra("storeLatitude", list.get(position).getLatitude());
        intent.putExtra("storeLongitude", list.get(position).getLongitude());
        context.startActivity(intent);
    }
    //Initial Google map
    public void initialGoogleMap(int position) {
        latitude = list.get(position).getLatitude();
        longitude = list.get(position).getLongitude();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

}

