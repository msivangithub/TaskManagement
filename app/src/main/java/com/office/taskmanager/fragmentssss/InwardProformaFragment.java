package com.office.taskmanager.fragmentssss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.office.taskmanager.Adaptes.ProformaAdapter;
import com.office.taskmanager.Pojo.Inward;
import com.office.taskmanager.Pojo.MyData;
import com.office.taskmanager.Pojo.Proforma;
import com.office.taskmanager.Pojo.TaskBranches;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.AsynHttpPost143;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.services.TotalSetListener;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.ProjectVariables;
import com.office.taskmanager.util.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by GhanaShyam on 7/9/2016.
 */

public class InwardProformaFragment extends Fragment implements TotalSetListener, RestfulListener {
    View view;
    EditText mEditTextC1;
    private TextView mTotalBF, mTotalRCD, mTotalOpen, mTotalBalance;
    Context context;
    //    RecyclerView recyclerView;
    private LinearLayout inwordProformaLayout, totalHEader;
    RelativeLayout relativeLayout;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ArrayList<Inward> inwardArrayList;
    TotalSetListener totalSetListener;
    private String xmlf;
    RestfulListener listener;
    JSONObject jsonObject;
    JSONArray jsonArray;
    float totalfbqty, totalrcdqty, totalopen, totalbalance;
    private HashMap<String, View> namedView;
    private String uid, Role, branch, branchID;
    private TextView subject_textview;
    private EditText bf, rcd, open, balance;
    private TextView total;
    private View child;
    private Spinner selectBranches;
    private ArrayList<TaskBranches> branches;
    private String companyID = "";
    private String cityname = "";
    int branchpos = 0;
    private RecyclerView recyclerView;
    private ArrayList<Proforma> proformaArrayList;
    ProformaAdapter proformaAdapter;
    private ScrollingView scrollingView;
    MenuItem CheckUnCheck, upload, allChk;

    public static InwardProformaFragment newInstance() {
        Bundle args = new Bundle();
        InwardProformaFragment fragment = new InwardProformaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*[{"RoId":"4","RoleName":"Admin"},
     {"RoId":"5","RoleName":"Manger"},
     {"RoId":"3","RoleName":"User"},
     {"RoId":"6","RoleName":"Purchase Incharge"}]*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inwardproforma, container, false);
        setHasOptionsMenu(true);
        context = getActivity();
        listener = this;
        totalSetListener = InwardProformaFragment.this;
        inwardArrayList = new ArrayList<Inward>();
        proformaArrayList = new ArrayList<Proforma>();
        branches = new ArrayList<>();
        jsonObject = new JSONObject();
        uid = SharedPreferenceUtil.getInstance().getString(getActivity(), "Uid", "Uid");
        Role = SharedPreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user");
        branch = SharedPreferenceUtil.getInstance().getString(getActivity(), "BranchName", "BranchName");
        branchID = SharedPreferenceUtil.getInstance().getString(getActivity(), ProjectVariables.COMPNAME, "BranchName");
        intialVariables();
        getBranches();
        return view;
    }

    private void intialVariables() {
        mTotalBF = (TextView) view.findViewById(R.id.total_BF);
        mTotalRCD = (TextView) view.findViewById(R.id.total_RCD);
        mTotalOpen = (TextView) view.findViewById(R.id.total_Open);
        mTotalBalance = (TextView) view.findViewById(R.id.total_Balance);
        inwordProformaLayout = (LinearLayout) view.findViewById(R.id.inwordProformaLayout);
        totalHEader = (LinearLayout) view.findViewById(R.id.totalHEader);
        // final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        selectBranches = (Spinner) view.findViewById(R.id.id_Branch);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        alternatDisplay();
        recyclerViewListener();

    }

    private void getBranches() {
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
            try {
                AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 197, ProjectVariables.BRANCHES, listener, null, "");
                post.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        selectBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                branches = AppUtil.getBranchesInfo();
                branchpos = position;
                companyID = branches.get(position).getBranchId();
                cityname = branches.get(position).getBranchName();
                getInwordDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getInwordDetails() {
        jsonObject = new JSONObject();
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
            try {
                jsonObject.accumulate("BranchId", companyID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AsynHttpPost143 post143 = new AsynHttpPost143(getActivity(), 1, 196, ProjectVariables.GetInwardProforma, listener, jsonObject, "", true);
                post143.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void recyclerViewListener() {
        inwardArrayList = new ArrayList<Inward>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            Inward Inv = new Inward();
            Inv.setId_(MyData.id_[i]);
            Inv.setName(MyData.nameArray[i]);
            inwardArrayList.add(Inv);
        }
        namedView = new HashMap<>();
        inwordProformaLayout.removeAllViews();
        for (final Inward inword : inwardArrayList) {
            child = getActivity().getLayoutInflater().inflate(R.layout.recyclerview_items, null);
            childVariables();
            final TextView total = (TextView) child.findViewById(R.id.text_Total);
            bf.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int id = inword.getId_();
                    Inward inward = inwardArrayList.get(id);
                    String strTxt = editable.toString();
                    inward.setBF(strTxt);
                    calculateRow(inward);
                    total.setText(inward.getTotalBalance());
                    calculateTotals();
                }
            });
            rcd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int id = inword.getId_();
                    Inward inward = inwardArrayList.get(id);
                    String strTxt = editable.toString();
                    inward.setRCD(strTxt);
                    calculateRow(inward);
                    total.setText(inward.getTotalBalance());
                    calculateTotals();
                }
            });
            open.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    int id = inword.getId_();
                    Inward inward = inwardArrayList.get(id);
                    String strTxt = editable.toString();
                    inward.setOPEN(strTxt);
                    calculateRow(inward);
                    calculateTotals();
                }
            });
            balance.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int id = inword.getId_();
                    Inward inward = inwardArrayList.get(id);
                    String strTxt = editable.toString();
                    inward.setBALANCE(strTxt);
                    calculateRow(inward);
                    calculateTotals();
                }
            });
            subject_textview.setText(inword.getName());
            namedView.put(inword.getName(), child);
            namedView.put(inword.getTotalBalance(), child);
            namedView.put(inword.getBF(), child);
            namedView.put(inword.getRCD(), child);
            namedView.put(inword.getOPEN(), child);
            namedView.put(inword.getBALANCE(), child);
            inwordProformaLayout.addView(child);
        }
    }

    private void childVariables() {
        subject_textview = (TextView) child.findViewById(R.id.subject_textview);
        bf = (EditText) child.findViewById(R.id.edit_BF);
        rcd = (EditText) child.findViewById(R.id.edit_RCD);
        open = (EditText) child.findViewById(R.id.edit_Open);
        balance = (EditText) child.findViewById(R.id.edit_Balance);

    }

    private void alternatDisplay() {
        try {
            if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
                recyclerView.setVisibility(View.VISIBLE);
                inwordProformaLayout.setVisibility(View.INVISIBLE);
                totalHEader.setVisibility(View.GONE);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_proform, menu);
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
            try {
                if (menu != null) {
                    upload = menu.findItem(R.id.action_upload);
                    upload.setVisible(false);
                } else {
                    upload.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                uploadToServer();
                recyclerViewListener();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validation() {
        boolean valid = true;
        String getbf = bf.getText().toString();
        String getrcd = rcd.getText().toString();
        String getopen = open.getText().toString();
        String getbalance = balance.getText().toString();

        if (getbf.isEmpty()) {
            bf.setError("Enter the data");
            valid = false;
        } else {
            bf.setError(null);
        }
        if (getrcd.isEmpty()) {
            rcd.setError("Enter the data");
            valid = false;
        } else {
            rcd.setError(null);
        }
        if (getopen.isEmpty()) {
            open.setError("Enter the data");
            valid = false;
        } else {
            open.setError(null);
        }
        if (getbalance.isEmpty()) {
            balance.setError("Enter the data");
            valid = false;
        } else {
            balance.setError(null);
        }
        return valid;
    }

    private void uploadToServer() {
     //   if (validation()) {
            xmlf = "<ROOT>";
            int cont = 1;
            for (int i = 0; i < inwardArrayList.size(); i++) {
                xmlf += "<INSERTXMLDATA FLR='" + inwardArrayList.get(i).getName() + "'";
                xmlf += " BF ='" + inwardArrayList.get(i).getBF() + "'";
                xmlf += " RCD ='" + inwardArrayList.get(i).getRCD() + "'";
                xmlf += " TOTAL ='" + inwardArrayList.get(i).getTotalBalance() + "'";
                xmlf += " OPENBAL ='" + inwardArrayList.get(i).getOPEN() + "'";
                xmlf += " BALANCE ='" + inwardArrayList.get(i).getRCD() + "'";
                xmlf += " ROLE ='" + Role + "'";
                xmlf += " USERID ='" + uid + "'";
                xmlf += " BRANCHID ='" + branchID + "'";
                xmlf += " COUNT ='" + cont++ + "'";
                xmlf += " />";
            }
            xmlf += "<INSERTXMLDATA FLR='" + "TOTAL" + "'";
            xmlf += " BF ='" + totalfbqty + "'";
            xmlf += " RCD ='" + totalrcdqty + "'";
            xmlf += " TOTAL ='" + "" + "'";
            xmlf += " OPENBAL ='" + totalopen + "'";
            xmlf += " BALANCE ='" + totalbalance + "'";
            xmlf += " ROLE ='" + Role + "'";
            xmlf += " USERID ='" + uid + "'";
            xmlf += " BRANCHID ='" + branchID + "'";
            xmlf += " COUNT ='" + cont++ + "'";
            xmlf += " />";

            xmlf += "</ROOT>";

            try {
                jsonObject.accumulate("XmlFile", xmlf);
                jsonObject.accumulate("Branchid", branchID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AsynHttpPost post = new AsynHttpPost(getActivity(), 1, 195, ProjectVariables.INWARDPROFORMA, listener, jsonObject, "");
                post.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
       /* }else {
            Toast.makeText(getActivity(),"Please Enter All data",Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    public void setTotalData() {
        calculateTotals();
    }

    @Override
    public void setRowData(int Index) {
        calculateRowItems(Index);

    }

    private void calculateRowItems(int index) {
        try {
            String Result = "0.00";
            Inward inward = inwardArrayList.get(index);
            String strConsumed = inward.getBF();
            String strPhysical = inward.getRCD();
            if (strConsumed.length() <= 0) {
                strConsumed = "0";
            }
            if (strPhysical.length() <= 0) {
                strPhysical = "0";
            }
            float Consumed = Float.parseFloat(strConsumed);
            float Physical = Float.parseFloat(strPhysical);

            Result = String.valueOf(Consumed + Physical);
            inward.setTotalBalance(Result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateRow(Inward inward) {
        try {
            String Result = "0.00";
            String strConsumed = inward.getBF();
            String strPhysical = inward.getRCD();
            if (strConsumed.length() <= 0) {
                strConsumed = "0";
            }
            if (strPhysical.length() <= 0) {
                strPhysical = "0";
            }
            float Consumed = Float.parseFloat(strConsumed);
            float Physical = Float.parseFloat(strPhysical);

            Result = String.valueOf(Consumed + Physical);
            inward.setTotalBalance(Result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SetTextI18n")
    private void calculateTotals() {
        try {
            mTotalBF.setText("0.00");
            mTotalRCD.setText("0.00");
            mTotalOpen.setText("0.00");
            mTotalBalance.setText("0.00");

            boolean Result = false;
            totalfbqty = Float.valueOf(String.valueOf(0.0f));
            totalrcdqty = Float.valueOf(String.valueOf(0.0f));
            totalopen = Float.valueOf(String.valueOf(0.0f));
            totalbalance = Float.valueOf(String.valueOf(0.0f));

            if (inwardArrayList != null || inwardArrayList.size() > 0) {
                for (Inward inward : inwardArrayList) {
                    totalfbqty += getFloat(inward.getBF());
                    totalrcdqty += getFloat(inward.getRCD());
                    totalopen += getFloat(inward.getOPEN());
                    totalbalance += getFloat(inward.getBALANCE());
                    Result = true;
                }
            }
            if (Result) {
                mTotalBF.setText(String.format("%.2f", totalfbqty));
                mTotalRCD.setText(String.format("%.2f", totalrcdqty));
                mTotalOpen.setText(String.format("%.2f", totalopen));
                mTotalBalance.setText(String.format("%.2f", totalbalance));
            } else {
                Toast.makeText(context, "No Records Found !", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Float getFloat(String str) {
        float Result = 0.0f;
        try {
            if (str == null) {
                str = "0.0";
            } else if (str.length() <= 0) {
                str = "0.0";
            }
            Result = Float.parseFloat(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    @Override
    public void getData(String s, String status, int rType, String temp) {
        if (rType == 195) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String result = jsonObject.getString("Result");
                    if (result.equalsIgnoreCase("inserted successfully")) {
                        Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                        Log.e("manager =", result);
                    }
                    mTotalBF.setText("");
                    mTotalRCD.setText("");
                    mTotalOpen.setText("");
                    mTotalBalance.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (rType == 196) {
            if (proformaAdapter != null) {
                proformaAdapter.notifyDataSetChanged();
                proformaArrayList = new ArrayList<>();
            }
            if (status.equalsIgnoreCase("1")) {
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String result = jsonObject.getString(ProjectVariables.StatusS);
                        if (result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        } else {
                            String bf = jsonObject.getString(ProjectVariables.BF);
                            String balance = jsonObject.getString(ProjectVariables.Balance);
                            String branchid = jsonObject.getString(ProjectVariables.Branchid);
                            String flr = jsonObject.getString(ProjectVariables.FLR);
                            String openbal = jsonObject.getString(ProjectVariables.OpenBal);
                            String rcd = jsonObject.getString(ProjectVariables.RCD);
                            String roles = jsonObject.getString(ProjectVariables.RoleS);
                            String statuss = jsonObject.getString(ProjectVariables.StatusS);
                            String total = jsonObject.getString(ProjectVariables.Total);
                            String userid = jsonObject.getString(ProjectVariables.Userid);

                            Proforma proforma = new Proforma();
                            proforma.setBF(bf);
                            proforma.setBalance(balance);
                            proforma.setBranchid(branchid);
                            proforma.setFLR(flr);
                            proforma.setOpenBal(openbal);
                            proforma.setRCD(rcd);
                            proforma.setRoleS(roles);
                            proforma.setStatusS(statuss);
                            proforma.setTotal(total);
                            proforma.setUserid(userid);
                            proformaArrayList.add(proforma);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_LONG).show();
                }
                proformaAdapter = new ProformaAdapter(getActivity(), R.layout.proform_rowitems, proformaArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(proformaAdapter);
            }
        } else if (rType == 197) {
            try {
                jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    TaskBranches branch = new TaskBranches();
                    branch.setBranchName(jsonObject.getString(ProjectVariables.BNAME));
                    branch.setBranchId(jsonObject.getString(ProjectVariables.BRANCHID));
                    branches.add(branch);
                }
                AppUtil.setBranchesInfo(branches);
                String[] us = new String[branches.size()];
                for (int i = 0; i < branches.size(); i++) {
                    us[i] = branches.get(i).getBranchName();
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectBranches.setAdapter(spinnerArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
