package com.mycompany.igrocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SharedListAdapter extends RecyclerView.Adapter<SharedListAdapter.MyViewHolder> {

    Context context;
    ArrayList<SharedListUser> sharedListUser;

    public SharedListAdapter(Context c, ArrayList<SharedListUser> p){
        context = c;
        sharedListUser = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shared_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sharedItemTitle.setText(sharedListUser.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return sharedListUser.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView sharedItemTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sharedItemTitle = (TextView) itemView.findViewById(R.id.sharedItemTitle);
        }
    }

}
