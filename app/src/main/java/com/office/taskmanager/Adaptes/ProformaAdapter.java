package com.office.taskmanager.Adaptes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.office.taskmanager.Pojo.Proforma;
import com.office.taskmanager.R;

import java.util.ArrayList;

/**
 * Created by vbsystem on 1/25/2017.
 */

public class ProformaAdapter extends RecyclerView.Adapter<ProformaAdapter.MyViewHolder> {
    Context context;
    ArrayList<Proforma> proformaArrayList;

    public ProformaAdapter(Context cont, int taskId, ArrayList<Proforma> proformas) {
        this.context = cont;
        this.proformaArrayList = proformas;
    }

    @Override
    public ProformaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proform_rowitems, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView BF;
        private TextView Balance;
        private TextView Branchid;
        private TextView FLR;
        private TextView OpenBal;
        private TextView RCD;
        private TextView RoleS;
        private TextView StatusS;
        private TextView Total;
        private TextView Userid;

        public MyViewHolder(View itemView) {
            super(itemView);
            BF = (TextView)itemView.findViewById(R.id.bf);
            Balance = (TextView)itemView.findViewById(R.id.balance);
            FLR = (TextView)itemView.findViewById(R.id.flr);
            OpenBal = (TextView)itemView.findViewById(R.id.open);
            RCD = (TextView)itemView.findViewById(R.id.rcd);
            Total = (TextView)itemView.findViewById(R.id.total);
        }
    }
    @Override
    public void onBindViewHolder(ProformaAdapter.MyViewHolder holder, int position) {
        final Proforma proforma = proformaArrayList.get(position);
        holder.BF.setText(proforma.getBF());
        holder.Balance.setText(proforma.getBalance());
        holder.FLR.setText(proforma.getFLR());
        holder.OpenBal.setText(proforma.getOpenBal());
        holder.RCD.setText(proforma.getRCD());
        holder.Total.setText(proforma.getTotal());

    }

    @Override
    public int getItemCount() {
        return proformaArrayList.size();
    }
}
