package com.mycompany.igrocery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreSelectionAdapter extends RecyclerView.Adapter<StoreSelectionAdapter.MyViewHolder> {

    String data1[];
    int images[];
    Context context;

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
                Intent intent = new Intent(context, Login.class);
                intent.putExtra("store", data1[position]);
                Toast.makeText(context, data1[position], Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }

        });

        holder.iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Login.class);
                intent.putExtra("store", data1[position]);
                Toast.makeText(context, data1[position], Toast.LENGTH_LONG).show();
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

