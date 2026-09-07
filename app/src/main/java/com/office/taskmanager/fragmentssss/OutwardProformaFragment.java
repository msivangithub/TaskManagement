package com.office.taskmanager.fragmentssss;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.office.taskmanager.Adaptes.OutwardAdapter;
import com.office.taskmanager.Pojo.MyData;
import com.office.taskmanager.Pojo.Outward;
import com.office.taskmanager.Pojo.Proforma1;
import com.office.taskmanager.Pojo.TaskBranches;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.AsynHttpPost143;
import com.office.taskmanager.services.RestfulListener;
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
public class OutwardProformaFragment extends Fragment implements RestfulListener {

    private View view;
    private Context context;
    private String uid, Role, branch, branchID;
    private RestfulListener listener;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private MenuItem CheckUnCheck, upload, allChk;
    private ArrayList<Outward> outwardArrayList;
    private HashMap<String, View> ViewHashMap;
    private LinearLayout inwordProformaLayout, totalHEader;
    private View child;
    private String xmlf;
    private Spinner selectBranches;
    private ArrayList<TaskBranches> branches;
    private String companyID = "";
    private String cityname = "";
    int branchpos = 0;
    private RecyclerView recyclerView;
    private OutwardAdapter outwardAdapter;
    private ArrayList<Proforma1> proforma1ArrayList;

    public static OutwardProformaFragment newInstance() {

        Bundle args = new Bundle();

        OutwardProformaFragment fragment = new OutwardProformaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outwardproforma, container, false);
        setHasOptionsMenu(true);
        context = getActivity();
        listener = this;
        outwardArrayList = new ArrayList<Outward>();
        proforma1ArrayList = new ArrayList<>();
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
        inwordProformaLayout = (LinearLayout) view.findViewById(R.id.inwordProformaLayout);
        selectBranches = (Spinner) view.findViewById(R.id.id_Branch);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        alternatDisplay();
        recyclerViewListener();
    }

    private void alternatDisplay() {
        try {
            if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
                recyclerView.setVisibility(View.VISIBLE);
                inwordProformaLayout.setVisibility(View.INVISIBLE);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBranches() {
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
            try {
                AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 200, ProjectVariables.BRANCHES, listener, null, "");
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
                getOutwardDetails();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getOutwardDetails() {
        jsonObject = new JSONObject();
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
            try {
                jsonObject.accumulate("BranchId", companyID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AsynHttpPost143 post143 = new AsynHttpPost143(getActivity(), 1, 201, ProjectVariables.GetOutwardProforma, listener, jsonObject, "", true);
                post143.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void recyclerViewListener() {
        outwardArrayList = new ArrayList<>();
        for (int i = 0; i < MyData.outwardArray.length; i++) {
            Outward outward = new Outward();
            outward.setId_(MyData.id_outware[i]);
            outward.setName(MyData.outwardArray[i]);
            outwardArrayList.add(outward);
        }
        ViewHashMap = new HashMap<>();
        inwordProformaLayout.removeAllViews();
        for (final Outward outward : outwardArrayList) {
            child = getActivity().getLayoutInflater().inflate(R.layout.outware_items, null);
            final TextView subject_textview = (TextView) child.findViewById(R.id.subject_textview);
            final EditText editText = (EditText) child.findViewById(R.id.edit_values);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    int id = outward.getId_();
                    Outward outwr = outwardArrayList.get(id);
                    String values = editable.toString();
                    outwr.setValues(values);
                }
            });
            subject_textview.setText(outward.getName());
            ViewHashMap.put(outward.getName(), child);
            ViewHashMap.put(outward.getValues(), child);
            inwordProformaLayout.addView(child);
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

    private void uploadToServer() {
        xmlf = "<ROOT>";
        int cont = 1;
        for (int i = 0; i < outwardArrayList.size(); i++) {
            xmlf += "<INSERTXMLDATA ProformaType='" + outwardArrayList.get(i).getName() + "'";
            xmlf += " ProformaValue ='" + outwardArrayList.get(i).getValues() + "'";
            xmlf += " ROLE ='" + Role + "'";
            xmlf += " USERID ='" + uid + "'";
            xmlf += " BRANCHID ='" + branchID + "'";
            xmlf += " COUNT ='" + cont++ + "'";
            xmlf += " />";
        }
        xmlf += "</ROOT>";
        try {
            jsonObject.accumulate("XmlFile", xmlf);
            jsonObject.accumulate("Branchid", branchID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            AsynHttpPost post = new AsynHttpPost(getActivity(), 1, 199, ProjectVariables.OutwardProforma, listener, jsonObject, "");
            post.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getData(String s, String status, int rType, String temp) {
        if (rType == 199) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String result = jsonObject.getString("Result");
                    if (result.equalsIgnoreCase("inserted successfully")) {
                        Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                        Log.e("manager =", result);
                        //recyclerViewListener();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rType == 200) {
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

        } else if (rType == 201) {
            if (outwardAdapter != null) {
                outwardAdapter.notifyDataSetChanged();
                proforma1ArrayList = new ArrayList<>();
            }
            if (status.equalsIgnoreCase("1")) {
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String result = jsonObject.getString("Status");
                        if (result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        } else {
                            String Branchid = jsonObject.getString("Branchid");
                            String Date = jsonObject.getString("Date");
                            String Flag = jsonObject.getString("Flag");
                            String ProformaType = jsonObject.getString("ProformaType");
                            String ProformaValue = jsonObject.getString("ProformaValue");
                            String Role = jsonObject.getString("Role");
                            String Status = jsonObject.getString("Status");
                            String Userid = jsonObject.getString("Userid");

                            Proforma1 proforma = new Proforma1();
                            proforma.setBranchid(Branchid);
                            proforma.setDate(Date);
                            proforma.setFlag(Flag);
                            proforma.setProformaType(ProformaType);
                            proforma.setProformaValue(ProformaValue);
                            proforma.setRole(Role);
                            proforma.setStatus(Status);
                            proforma.setUserid(Userid);
                            proforma1ArrayList.add(proforma);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_LONG).show();
                }
                outwardAdapter = new OutwardAdapter(getActivity(), R.layout.recyclear_outware_items, proforma1ArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(outwardAdapter);
            }
        }
    }
}
