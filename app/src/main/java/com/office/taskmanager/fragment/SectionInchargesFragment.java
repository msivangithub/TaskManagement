package com.office.taskmanager.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.office.taskmanager.Pojo.TaskBranches;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.AsynHttpPost143;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.ProjectVariables;
import com.office.taskmanager.util.SharedPreferenceUtil;
import com.office.taskmanager.util.TransparentProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class SectionInchargesFragment extends Fragment implements RestfulListener {

    /*[{"RoId":"4","RoleName":"Admin"},
    {"RoId":"5","RoleName":"Manger"},
    {"RoId":"3","RoleName":"User"},
    {"RoId":"6","RoleName":"Purchase Incharge"}]*/
    private ArrayList<TaskBranches> branches;
    private TransparentProgressDialog progressdailog;
    private Handler handler;
    private Runnable runnable;
    CheckBox mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6, mCheckBox7, mCheckBox8, mCheckBox9, mCheckBox10, mCheckBox11,
            mCheckBox12, mCheckBox13, mCheckBox14, mCheckBox15, mCheckBox16;
    RadioButton mLastMonthdate, mThisMontDate;
    String radioButton;
    String checkbox1 = "No", checkbox2 = "No", checkbox3 = "No", checkbox4 = "No", checkbox5 = "No", checkbox6 = "No", checkbox7 = "No", checkbox8 = "No", checkbox9 = "No",
            checkbox10 = "No", checkbox11 = "No", checkbox12 = "No", checkbox13 = "No", checkbox14 = "No", checkbox15 = "No", checkbox16 = "No";
    View view;
    TextView mClearAdavance, mTextViewcheckbox2, mTextViewcheckbox3, mTextViewcheckbox4, mTextViewcheckbox6, mTextViewcheckbox7, mTextViewcheckbox8, mTextViewcheckbox9,
            mTextViewcheckbox10, mTextViewcheckbox11, mTextViewcheckbox12, mTextViewcheckbox14, mTextViewcheckbox15, mTextViewcheckbox16, mText_radioButn, mTextSDPT, mTextJGTL;
    String checked;
    boolean isChecked = true;
    RestfulListener listener;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String uid, userroles, branchID;
    Spinner spinner1, spinner2;
    String sdpt, jgtl;
    Context context;
    MenuItem CheckUnCheck, upload, allChk;
    Menu menu;
    String companyID = "";
    String cityname = "";
    int branchpos = 0;
    private Spinner selectBranches;
    String ClrAdvPayment = null, CounterCheckMrng = null, DiscBillList = null, EveryMonthDiscount = null,
            ImprovementInSales = null, Incentive = null, MarketSurvey = null, MinStockQty = null, MonthlyReport = null,
            NoOfVisitsJGTL = null, NoOfVisitsSDPT = null, PedingLRValue = null, PendingOrder = null, Role = null, S_Create_date = null,
            S_modify_date = null, SlowStockReport = null, StartingFoldingAndSetting = null,
            Status = null, StockReport = null, TravelCharges = null, UserId = null, Flag = null;

    public static SectionInchargesFragment newInstance() {
        Bundle args = new Bundle();
        SectionInchargesFragment fragment = new SectionInchargesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.section_incharges_task, container, false);
        listener = this;
        context = getActivity();
        branches = new ArrayList<>();
        setHasOptionsMenu(true);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Manager//SectionIncharges</font>"));
        uid = SharedPreferenceUtil.getInstance().getString(getActivity(), "Uid", "Uid");
        userroles = SharedPreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user");
        branchID = SharedPreferenceUtil.getInstance().getString(getActivity(), ProjectVariables.COMPNAME, "BranchName");
        intialVariables();
        getBranches();

        return view;
    }

    private void getBranches() {
        if (userroles.equalsIgnoreCase("4") || userroles.equalsIgnoreCase("1")) {
            try {
                AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 146, ProjectVariables.BRANCHES, listener, null, "");
                post.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        selectBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                branches = AppUtil.getBranchesInfo();
                companyID = branches.get(position).getBranchId();
                cityname = branches.get(position).getBranchName();
                getManagerDailyReport();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getManagerDailyReport() {
        jsonObject = new JSONObject();
        if (userroles.equalsIgnoreCase("4") || userroles.equalsIgnoreCase("1")) {
            try {
                jsonObject.accumulate("BranchId", companyID);
                Log.e("BranchID ", companyID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AsynHttpPost143 post143 = new AsynHttpPost143(getActivity(), 1, 149, ProjectVariables.GETMANAGERDAILYFEEDBACK, listener, jsonObject, "",true);
                post143.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void intialVariables() {
        selectBranches = (Spinner) view.findViewById(R.id.id_Branch);
        mClearAdavance = (TextView) view.findViewById(R.id.id_clearAdavance);
        mTextViewcheckbox2 = (TextView) view.findViewById(R.id.text_checkbox2);
        mTextViewcheckbox3 = (TextView) view.findViewById(R.id.text_checkbox3);
        mTextViewcheckbox4 = (TextView) view.findViewById(R.id.text_checkbox4);
        mTextViewcheckbox6 = (TextView) view.findViewById(R.id.text_checkbox6);
        mTextViewcheckbox7 = (TextView) view.findViewById(R.id.text_checkbox7);
        mTextViewcheckbox8 = (TextView) view.findViewById(R.id.text_checkbox8);
        mTextViewcheckbox9 = (TextView) view.findViewById(R.id.text_checkbox9);
        mTextViewcheckbox10 = (TextView) view.findViewById(R.id.text_checkbox10);
        mTextViewcheckbox11 = (TextView) view.findViewById(R.id.text_checkbox11);
        mTextViewcheckbox12 = (TextView) view.findViewById(R.id.text_checkbox12);
        mTextViewcheckbox14 = (TextView) view.findViewById(R.id.text_checkbox14);
        mTextViewcheckbox15 = (TextView) view.findViewById(R.id.text_checkbox15);
        mTextViewcheckbox16 = (TextView) view.findViewById(R.id.text_checkbox16);
        mText_radioButn = (TextView) view.findViewById(R.id.text_radioButn);
        mTextSDPT = (TextView) view.findViewById(R.id.text_sdpt);
        mTextJGTL = (TextView) view.findViewById(R.id.text_jgtl);
        mText_radioButn.setSelected(true);

        mCheckBox1 = (CheckBox) view.findViewById(R.id.id_checkbox1);
        mCheckBox2 = (CheckBox) view.findViewById(R.id.id_checkbox2);
        mCheckBox3 = (CheckBox) view.findViewById(R.id.id_checkbox3);
        mCheckBox4 = (CheckBox) view.findViewById(R.id.id_checkbox4);
        mCheckBox5 = (CheckBox) view.findViewById(R.id.id_checkbox5);
        mCheckBox6 = (CheckBox) view.findViewById(R.id.id_checkbox6);
        mCheckBox7 = (CheckBox) view.findViewById(R.id.id_checkbox7);
        mCheckBox8 = (CheckBox) view.findViewById(R.id.id_checkbox8);
        mCheckBox9 = (CheckBox) view.findViewById(R.id.id_checkbox9);
        mCheckBox10 = (CheckBox) view.findViewById(R.id.id_checkbox10);
        mCheckBox11 = (CheckBox) view.findViewById(R.id.id_checkbox11);
        mCheckBox12 = (CheckBox) view.findViewById(R.id.id_checkbox12);
        mCheckBox13 = (CheckBox) view.findViewById(R.id.id_checkbox13);
        mCheckBox14 = (CheckBox) view.findViewById(R.id.id_checkbox14);
        mCheckBox15 = (CheckBox) view.findViewById(R.id.id_checkbox15);
        mCheckBox16 = (CheckBox) view.findViewById(R.id.id_checkbox16);
        spinner1 = (Spinner) view.findViewById(R.id.id_sdpt);
        spinner2 = (Spinner) view.findViewById(R.id.id_jgtl);
        mLastMonthdate = (RadioButton) view.findViewById(R.id.id_lastMonthdate);
        mThisMontDate = (RadioButton) view.findViewById(R.id.id_thisMonthdate);

        checkBoxOnClick();
        spinnerItemOnClick();
        radioButtonOnClick();
        alternatDisplay();
    }

    private void alternatDisplay() {
        try {
            if (userroles.equalsIgnoreCase("4") || userroles.equalsIgnoreCase("1")) {
                mClearAdavance.setVisibility(View.VISIBLE);
                mTextViewcheckbox2.setVisibility(View.VISIBLE);
                mTextViewcheckbox3.setVisibility(View.VISIBLE);
                mTextViewcheckbox4.setVisibility(View.VISIBLE);
                mTextViewcheckbox6.setVisibility(View.VISIBLE);
                mTextViewcheckbox7.setVisibility(View.VISIBLE);
                mTextViewcheckbox8.setVisibility(View.VISIBLE);
                mTextViewcheckbox9.setVisibility(View.VISIBLE);
                mTextViewcheckbox10.setVisibility(View.VISIBLE);
                mTextViewcheckbox11.setVisibility(View.VISIBLE);
                mTextViewcheckbox12.setVisibility(View.VISIBLE);
                mTextViewcheckbox14.setVisibility(View.VISIBLE);
                mTextViewcheckbox15.setVisibility(View.VISIBLE);
                mTextViewcheckbox16.setVisibility(View.VISIBLE);
                mText_radioButn.setVisibility(View.VISIBLE);
                mTextSDPT.setVisibility(View.VISIBLE);
                mTextJGTL.setVisibility(View.VISIBLE);

                mCheckBox1.setVisibility(View.GONE);
                mCheckBox2.setVisibility(View.GONE);
                mCheckBox3.setVisibility(View.GONE);
                mCheckBox4.setVisibility(View.GONE);
                mCheckBox5.setVisibility(View.GONE);
                mCheckBox6.setVisibility(View.GONE);
                mCheckBox7.setVisibility(View.GONE);
                mCheckBox8.setVisibility(View.GONE);
                mCheckBox9.setVisibility(View.GONE);
                mCheckBox10.setVisibility(View.GONE);
                mCheckBox11.setVisibility(View.GONE);
                mCheckBox12.setVisibility(View.GONE);
                mCheckBox13.setVisibility(View.GONE);
                mCheckBox14.setVisibility(View.GONE);
                mCheckBox15.setVisibility(View.GONE);
                mCheckBox16.setVisibility(View.GONE);
                spinner1.setVisibility(View.GONE);
                spinner2.setVisibility(View.GONE);
            } else {
                mCheckBox1.setVisibility(View.VISIBLE);
                mClearAdavance.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void radioButtonOnClick() {
        mLastMonthdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    radioButton = "LastMonthdate";
                }
            }
        });
        mThisMontDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    radioButton = "ThisMontDate";
                }
            }
        });
    }

    private void spinnerItemOnClick() {
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sdpt = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jgtl = spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void checkBoxOnClick() {
        mCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox1 = "yes";
                } else {
                    checkbox1 = "No";
                }
            }
        });
        mCheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox2 = "yes";
                } else {
                    checkbox2 = "No";
                }
            }
        });
        mCheckBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox3 = "yes";
                } else {
                    checkbox3 = "No";
                }
            }
        });
        mCheckBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox4 = "yes";
                } else {
                    checkbox4 = "No";
                }
            }
        });
        mCheckBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox5 = "yes";
                } else {
                    checkbox5 = "No";
                }
            }
        });
        mCheckBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox6 = "yes";
                } else {
                    checkbox6 = "No";
                }
            }
        });
        mCheckBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox7 = "yes";
                } else {
                    checkbox7 = "No";
                }
            }
        });
        mCheckBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox8 = "yes";
                } else {
                    checkbox8 = "No";
                }
            }
        });
        mCheckBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox9 = "yes";
                } else {
                    checkbox9 = "No";
                }
            }
        });
        mCheckBox10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox10 = "yes";
                } else {
                    checkbox10 = "No";
                }
            }
        });
        mCheckBox11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox11 = "yes";
                } else {
                    checkbox11 = "No";
                }
            }
        });
        mCheckBox12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox12 = "yes";
                } else {
                    checkbox12 = "No";
                }
            }
        });
        mCheckBox13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox13 = "yes";
                } else {
                    checkbox13 = "No";
                }
            }
        });
        mCheckBox14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox14 = "yes";
                } else {
                    checkbox14 = "No";
                }
            }
        });
        mCheckBox15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox15 = "yes";
                } else {
                    checkbox15 = "No";
                }
            }
        });
        mCheckBox16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkbox16 = "yes";
                } else {
                    checkbox16 = "No";
                }
            }
        });
    }

    boolean IsValidatedToSave() {
        boolean Result = false;
        try {
            if (!Result) {
                Result = mCheckBox1.isChecked();
            }
            if (!Result) {
                Result = mCheckBox2.isChecked();
            }
            if (!Result) {
                Result = mCheckBox3.isChecked();
            }
            if (!Result) {
                Result = mCheckBox4.isChecked();
            }
            if (!Result) {
                Result = mCheckBox5.isChecked();
            }
            if (!Result) {
                Result = mCheckBox6.isChecked();
            }
            if (!Result) {
                Result = mCheckBox7.isChecked();
            }
            if (!Result) {
                Result = mCheckBox8.isChecked();
            }
            if (!Result) {
                Result = mCheckBox9.isChecked();
            }
            if (!Result) {
                Result = mCheckBox10.isChecked();
            }
            if (!Result) {
                Result = mCheckBox11.isChecked();
            }
            if (!Result) {
                Result = mCheckBox12.isChecked();
            }
            if (!Result) {
                Result = mCheckBox13.isChecked();
            }
            if (!Result) {
                Result = mCheckBox14.isChecked();
            }
            if (!Result) {
                Result = mCheckBox15.isChecked();
            }
            if (!Result) {
                Result = mCheckBox16.isChecked();
            }
            if (!Result) {
                Result = mLastMonthdate.isChecked();
            }
            if (!Result) {
                Result = mThisMontDate.isChecked();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    void Toggle_Selection(boolean Status) {
        try {
            isChecked = Status;

            mCheckBox1.setChecked(isChecked);
            mCheckBox2.setChecked(isChecked);
            mCheckBox3.setChecked(isChecked);
            mCheckBox4.setChecked(isChecked);
            mCheckBox5.setChecked(isChecked);
            mCheckBox6.setChecked(isChecked);
            mCheckBox7.setChecked(isChecked);
            mCheckBox8.setChecked(isChecked);
            mCheckBox9.setChecked(isChecked);
            mCheckBox10.setChecked(isChecked);
            mCheckBox11.setChecked(isChecked);
            mCheckBox12.setChecked(isChecked);
            mCheckBox13.setChecked(isChecked);
            mCheckBox14.setChecked(isChecked);
            mCheckBox15.setChecked(isChecked);
            mCheckBox16.setChecked(isChecked);

            if (isChecked) {
                CheckUnCheck.setTitle("Unselect All");
            } else {
                CheckUnCheck.setTitle("Select All");
            }
            isChecked = !isChecked;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_incharges, menu);
        CheckUnCheck = menu.getItem(0);
        if (userroles.equalsIgnoreCase("4") || userroles.equalsIgnoreCase("1")) {
            try {
                if (menu != null) {
                    upload = menu.findItem(R.id.upload_data);
                    allChk = menu.findItem(R.id.allchecked_items);
                    upload.setVisible(false);
                    allChk.setVisible(false);
                } else {
                    upload.setVisible(true);
                    allChk.setVisible(true);
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

            case R.id.allchecked_items:
                Toggle_Selection(isChecked);
                return true;
            case R.id.upload_data:
                if (IsValidatedToSave()) {
                    UploadData();
                } else {
                    Toast.makeText(getActivity(), "Please Select at list one items", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return (super.onOptionsItemSelected(item)
        );
    }

    private void UploadData() {
        jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(ProjectVariables.BranchId, branchID);
            jsonObject.accumulate(ProjectVariables.UserId, uid);
            jsonObject.accumulate(ProjectVariables.ClrAdvPayment, checkbox1);
            jsonObject.accumulate(ProjectVariables.TravelCharges, checkbox2);
            jsonObject.accumulate(ProjectVariables.PendingOrder, checkbox4);
            jsonObject.accumulate(ProjectVariables.PedingLRValue, checkbox3);
            jsonObject.accumulate(ProjectVariables.NoOfVisitsSDPT, sdpt);
            jsonObject.accumulate(ProjectVariables.NoOfVisitsJGTL, jgtl);
            jsonObject.accumulate(ProjectVariables.DiscBillList, checkbox6);
            jsonObject.accumulate(ProjectVariables.CounterCheckMrng, checkbox7);
            jsonObject.accumulate(ProjectVariables.StockReport, checkbox8);
            jsonObject.accumulate(ProjectVariables.EveryMonthDiscount, checkbox9);
            jsonObject.accumulate(ProjectVariables.SlowStockReport, checkbox10);
            jsonObject.accumulate(ProjectVariables.MinStockQty, checkbox11);
            jsonObject.accumulate(ProjectVariables.ImprovementInSales, checkbox12);
            jsonObject.accumulate(ProjectVariables.MarketSurvey, radioButton);
            jsonObject.accumulate(ProjectVariables.MonthlyReport, checkbox14);
            jsonObject.accumulate(ProjectVariables.Incentive, checkbox15);
            jsonObject.accumulate(ProjectVariables.StartingFoldingAndSetting, checkbox16);
            jsonObject.accumulate(ProjectVariables.Role, userroles);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppUtil.isInternetAvailable(getActivity())) {
            try {
                AsynHttpPost post143 = new AsynHttpPost(getActivity(), 1, 147, ProjectVariables.MANAGERDAILYFEEDBACK, listener, jsonObject, "");
                post143.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void getData(String s, String status, int rType, String temp) {
        if (rType == 147) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    Log.e("JSON DATA = ", status);
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String result = jsonObject.getString("Result");
                    if (result.equalsIgnoreCase("inserted successfully")) {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        Toggle_Selection(false);
                        Log.e("manager =", result);
                    } else {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        Toggle_Selection(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rType == 149) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    JSONArray array = new JSONArray(s);
                    JSONObject obj = array.getJSONObject(0);
                    String result = obj.getString("Status");
                    if (result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        setTextNull();
                    } else {
                        getJSONVariables(obj);
                        setTextValues();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rType == 146) {
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
                if (getActivity()!=null) {
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectBranches.setAdapter(spinnerArrayAdapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setTextNull() {
        mClearAdavance.setText("");
        mTextViewcheckbox2.setText("");
        mTextViewcheckbox3.setText("");
        mTextViewcheckbox4.setText("");
        mTextViewcheckbox6.setText("");
        mTextViewcheckbox7.setText("");
        mTextViewcheckbox8.setText("");
        mTextViewcheckbox9.setText("");
        mTextViewcheckbox10.setText("");
        mTextViewcheckbox11.setText("");
        mTextViewcheckbox12.setText("");
        mTextViewcheckbox14.setText("");
        mTextViewcheckbox15.setText("");
        mTextViewcheckbox16.setText("");
        mText_radioButn.setText("");
        mTextSDPT.setText("");
        mTextJGTL.setText("");
    }

    private void getJSONVariables(JSONObject obj) {
        try {
            ClrAdvPayment = obj.getString(ProjectVariables.ClrAdvPayment);
            CounterCheckMrng = obj.getString(ProjectVariables.CounterCheckMrng);
            DiscBillList = obj.getString(ProjectVariables.DiscBillList);
            EveryMonthDiscount = obj.getString(ProjectVariables.EveryMonthDiscount);
            ImprovementInSales = obj.getString(ProjectVariables.ImprovementInSales);
            Incentive = obj.getString(ProjectVariables.Incentive);
            MarketSurvey = obj.getString(ProjectVariables.MarketSurvey);
            MinStockQty = obj.getString(ProjectVariables.MinStockQty);
            MonthlyReport = obj.getString(ProjectVariables.MonthlyReport);
            NoOfVisitsJGTL = obj.getString(ProjectVariables.NoOfVisitsJGTL);
            NoOfVisitsSDPT = obj.getString(ProjectVariables.NoOfVisitsSDPT);
            PedingLRValue = obj.getString(ProjectVariables.PedingLRValue);
            PendingOrder = obj.getString(ProjectVariables.PendingOrder);
            Role = obj.getString(ProjectVariables.Role);
            Flag = obj.getString(ProjectVariables.Flag);
                   /*S_Create_date = obj.getString(ProjectVariables.S_Create_date);
                    S_modify_date = obj.getString(ProjectVariables.S_modify_date);*/
            SlowStockReport = obj.getString(ProjectVariables.SlowStockReport);
            StartingFoldingAndSetting = obj.getString(ProjectVariables.StartingFoldingAndSetting);
                   /*Status = obj.getString(ProjectVariables.Status);*/
            StockReport = obj.getString(ProjectVariables.StockReport);
            TravelCharges = obj.getString(ProjectVariables.TravelCharges);
            UserId = obj.getString(ProjectVariables.UserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextValues() {
        try {
            mTextJGTL.setText(NoOfVisitsJGTL);
            mTextSDPT.setText(NoOfVisitsSDPT);
            if (ClrAdvPayment.equalsIgnoreCase("yes")) {
                mClearAdavance.setTextColor(Color.parseColor("#388E3C"));
                mClearAdavance.setText(ClrAdvPayment);
            } else {
                mClearAdavance.setTextColor(Color.parseColor("#D50000"));
                mClearAdavance.setText(ClrAdvPayment);
            }

            if (TravelCharges.equalsIgnoreCase("yes")) {
                mTextViewcheckbox2.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox2.setText(TravelCharges);
            } else {
                mTextViewcheckbox2.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox2.setText(TravelCharges);
            }

            if (PedingLRValue.equalsIgnoreCase("yes")) {
                mTextViewcheckbox3.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox3.setText(PedingLRValue);
            } else {
                mTextViewcheckbox3.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox3.setText(PedingLRValue);
            }

            if (PendingOrder.equalsIgnoreCase("yes")) {
                mTextViewcheckbox4.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox4.setText(PendingOrder);
            } else {
                mTextViewcheckbox4.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox4.setText(PendingOrder);
            }

            if (DiscBillList.equalsIgnoreCase("yes")) {
                mTextViewcheckbox6.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox6.setText(DiscBillList);
            } else {
                mTextViewcheckbox6.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox6.setText(DiscBillList);
            }

            if (CounterCheckMrng.equalsIgnoreCase("yes")) {
                mTextViewcheckbox7.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox7.setText(CounterCheckMrng);
            } else {
                mTextViewcheckbox7.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox7.setText(CounterCheckMrng);
            }

            if (StockReport.equalsIgnoreCase("yes")) {
                mTextViewcheckbox8.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox8.setText(StockReport);
            } else {
                mTextViewcheckbox8.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox8.setText(StockReport);
            }

            if (EveryMonthDiscount.equalsIgnoreCase("yes")) {
                mTextViewcheckbox9.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox9.setText(EveryMonthDiscount);
            } else {
                mTextViewcheckbox9.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox9.setText(EveryMonthDiscount);
            }

            if (SlowStockReport.equalsIgnoreCase("yes")) {
                mTextViewcheckbox10.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox10.setText(SlowStockReport);
            } else {
                mTextViewcheckbox10.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox10.setText(SlowStockReport);
            }

            if (MinStockQty.equalsIgnoreCase("yes")) {
                mTextViewcheckbox11.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox11.setText(MinStockQty);
            } else {
                mTextViewcheckbox11.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox11.setText(MinStockQty);
            }

            if (ImprovementInSales.equalsIgnoreCase("yes")) {
                mTextViewcheckbox12.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox12.setText(ImprovementInSales);
            } else {
                mTextViewcheckbox12.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox12.setText(ImprovementInSales);
            }

            if (MonthlyReport.equalsIgnoreCase("yes")) {
                mTextViewcheckbox14.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox14.setText(MonthlyReport);
            } else {
                mTextViewcheckbox14.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox14.setText(MonthlyReport);
            }

            if (Incentive.equalsIgnoreCase("yes")) {
                mTextViewcheckbox15.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox15.setText(Incentive);
            } else {
                mTextViewcheckbox15.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox15.setText(Incentive);
            }

            if (StartingFoldingAndSetting.equalsIgnoreCase("yes")) {
                mTextViewcheckbox16.setTextColor(Color.parseColor("#388E3C"));
                mTextViewcheckbox16.setText(StartingFoldingAndSetting);
            } else {
                mTextViewcheckbox16.setTextColor(Color.parseColor("#D50000"));
                mTextViewcheckbox16.setText(StartingFoldingAndSetting);
            }

            if (MarketSurvey.equalsIgnoreCase("yes")) {
                mText_radioButn.setTextColor(Color.parseColor("#388E3C"));
                mText_radioButn.setText(MarketSurvey);
            } else {
                mText_radioButn.setTextColor(Color.parseColor("#388E3C"));
                mText_radioButn.setText(MarketSurvey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





