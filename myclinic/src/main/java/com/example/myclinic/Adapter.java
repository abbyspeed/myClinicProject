package com.example.myclinic;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private User[] dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;

        public ViewHolder(TextView v) {
            super(v);
            this.textview = v;
        }
    }

    public Adapter(User[] dataset) {
        Log.w("Adapter", "Loggin0");
        this.dataset = dataset;
    }


    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.w("Adapter", "Loggin1");
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.dbox, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Log.w("Adapter", "Loggin2");
        holder.textview.setText(dataset[position].packet.get("name").toString());
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }
}
