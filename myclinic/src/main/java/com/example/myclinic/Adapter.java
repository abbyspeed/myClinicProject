package com.example.myclinic;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private User[] dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout constraintLayout;
        public TextView doctorView, dateView, timeView;
        public Button deleteBtn, updateBtn;

        public ViewHolder(ConstraintLayout v) {
            super(v);
            this.constraintLayout = v;
            this.doctorView = v.findViewById(R.id.doctor);
            this.timeView = v.findViewById(R.id.time);
            this.dateView = v.findViewById(R.id.date);
            this.deleteBtn = v.findViewById(R.id.deleteBtn);
            this.updateBtn = v.findViewById(R.id.updateBtn);
        }

        public TextView getDoctorView() {
            return doctorView;
        }

        public TextView getTimeView() {
            return timeView;
        }

        public TextView getDateView() {
            return dateView;
        }

        public Button getDeleteBtn() {
            return deleteBtn;
        }

        public Button getUpdateBtn() {
            return updateBtn;
        }
    }

    public Adapter(User[] dataset) {
        Log.w("Adapter", "Loggin0");
        this.dataset = dataset;
    }


    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.w("Adapter", "Loggin1");
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.dbox, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Log.w("Adapter", "Loggin2");
//        holder.textview.setText(dataset[position].packet.get("name").toString());
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }
}
