package com.office.taskmanager.Adaptes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.office.taskmanager.Pojo.Inward;
import com.office.taskmanager.R;
import com.office.taskmanager.services.TotalSetListener;

import java.util.ArrayList;

/**
 * Created by JUNED on 6/10/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements TotalSetListener {

    String[] SubjectValues;
    Context context;
    ArrayList<Inward> inwardArrayList;
    TotalSetListener setListener;

    public RecyclerViewAdapter(Context context1, ArrayList<Inward> inwardArray, TotalSetListener Listener) {
        inwardArrayList = inwardArray;
        context = context1;
        setListener = Listener;
    }

    @Override
    public void setTotalData() {
    }
    @Override
    public void setRowData(int Index) {
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView, mTotal;
        private EditText bf, rcd, open, balance;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.subject_textview);
            mTotal = (TextView) v.findViewById(R.id.text_Total);
            bf = (EditText) v.findViewById(R.id.edit_BF);
            rcd = (EditText) v.findViewById(R.id.edit_RCD);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
        View view1 = LayoutInflater.from(context).inflate(R.layout.recyclerview_items, parent, false);
        ViewHolder viewHolder1;
        viewHolder1 = new ViewHolder(view1);
        viewHolder1.setIsRecyclable(false);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            boolean IsFirstBind = true;
            if (holder.textView.getTag() != null) {
                if (!holder.textView.getTag().toString().equalsIgnoreCase("")) {
                    IsFirstBind = false;
                }
            }
            if (IsFirstBind) {
                final Inward inward = inwardArrayList.get(position);
                holder.textView.setText(inward.getName());
                holder.textView.setTag(inward.getId_());

                holder.bf.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        try {
                            int Id = Integer.parseInt(holder.textView.getTag().toString());
                            Inward inward = inwardArrayList.get(Id);
                            String strTxt = editable.toString();
                            inward.setBF(strTxt);
                            setListener.setRowData(Id);
                            holder.mTotal.setText(inward.getTotalBalance());
                            setListener.setTotalData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                });
                holder.rcd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        try {
                            int Id = Integer.parseInt(holder.textView.getTag().toString());
                            String strTxt = editable.toString();
                            Inward inward = inwardArrayList.get(Id);
                            inward.setRCD(strTxt);
                            setListener.setRowData(Id);
                            holder.mTotal.setText(inward.getTotalBalance());
                            setListener.setTotalData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return inwardArrayList == null ? 0 : inwardArrayList.size();
    }
}