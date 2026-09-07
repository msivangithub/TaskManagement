package com.office.taskmanager.Adaptes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.office.taskmanager.Pojo.Proforma1;
import com.office.taskmanager.R;

import java.util.ArrayList;

/**
 * Created by vbsystem on 1/25/2017.
 */

public class OutwardAdapter extends RecyclerView.Adapter<OutwardAdapter.MyViewHolder> {
    Context context;
    ArrayList<Proforma1> proformaArrayList;

    public OutwardAdapter(Context cont, int taskId, ArrayList<Proforma1> proformas) {
        this.context = cont;
        this.proformaArrayList = proformas;
    }

    @Override
    public OutwardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclear_outware_items, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView values;


        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.subject_textview);
            values = (TextView)itemView.findViewById(R.id.text_values);

        }
    }
    @Override
    public void onBindViewHolder(OutwardAdapter.MyViewHolder holder, int position) {
        final Proforma1 proforma = proformaArrayList.get(position);
        holder.text.setText(proforma.getProformaType());
        holder.values.setText(proforma.getProformaValue());


    }

    @Override
    public int getItemCount() {
        return proformaArrayList.size();
    }
}
