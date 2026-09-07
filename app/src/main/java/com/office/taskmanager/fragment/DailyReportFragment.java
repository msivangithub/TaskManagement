package com.office.taskmanager.fragment;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class DailyReportFragment extends Fragment implements RestfulListener {

    private ArrayList<TaskBranches> branches;
    RestfulListener listener;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String uid, Role, branch, branchID;
    JSONObject obj;
    EditText mBranchId, mSale, mChecking_Counter_Amount_Pcs, mUnpaid, mRg, mAxis,
            mHdfc, mCash_On_Hand, mOpening_Cash, mBank_Deposit, mCash_Short, mSynd_Odh, mUnpaid_Parcels_Amount_Type, mUnpaid_Parcels_Amount, mNight_Cash_or_Opening_Cash,
            mDaily_Units_Power_or_Fuel, mDaily_Extra_Staff, mWeek_Off_Holiday, mDaily_Absent_Staff, mDaily_Present_or_Tota, mTea_x_Present_Staff_Custo, mSpl_Less, mDaily_Water_Jars,
            mPlastic_Covers_or_Jute_Bags_Amt, mHow_Many_Staff_Shop_Open_or_Dvlp, mPrayer, mCompounding_or_Late_Coming, mTiffin_Box, mStaff_Low_Sale_Remainder, mGate_Pass_with_Receipt_Number,
            mCancel_Bills_Sign_with_Photos, mAlterness_Check_Meterage_SS, mAlterness_Check_inward_qty, mAlterness_Check_secu_Alert,
            mMaintaince_Sheet, mNight_Watchman_Call_Alert, mPhCall_Shop_Open_or_Dvlp, mDB_and_SB_Entry,
            mNoof_Parcel_or_LRs, mVisiting_with_report, mEye_Hospital_OP_nd_Surgeries_OP, mEye_Hospital_OP_nd_Surgeries_Surgery_Free, mEye_Hospital_OP_nd_Surgeries_Pay, mBlood_Bank_Stock,
            mDental_Hospital, mRole;
    View view;
    String BranchId, Sale, Checking_Counter_Amount_Pcs, Unpaid, Rg, Axis, Hdfc, Cash_On_Hand, Opening_Cash, Bank_Deposit, Cash_Short, Synd_Odh, Unpaid_Parcels_Amount_Type, Unpaid_Parcels_Amount, Night_Cash_or_Opening_Cash,
            Daily_Units_Power_or_Fuel, Daily_Extra_Staff, Week_Off_Holiday, Daily_Absent_Staff, Daily_Present_or_Tota, Tea_x_Present_Staff_Custo, Spl_Less, Daily_Water_Jars, Plastic_Covers_or_Jute_Bags_Amt,
            How_Many_Staff_Shop_Open_or_Dvlp, Prayer, Compounding_or_Late_Coming, Tiffin_Box, Staff_Low_Sale_Remainder, Gate_Pass_with_Receipt_Number, Cancel_Bills_Sign_with_Photos,
            Alterness_Check_Meterage_SS, Alterness_Check_inward_qty, Alterness_Check_secu_Alert, Maintaince_Sheet, Night_Watchman_Call_Alert, PhCall_Shop_Open_or_Dvlp, DB_and_SB_Entry,
            Noof_Parcel_or_LRs, Visiting_with_report, Eye_Hospital_OP_nd_Surgeries_OP, Eye_Hospital_OP_nd_Surgeries_Surgery_Free, Eye_Hospital_OP_nd_Surgeries_Pay,
            Blood_Bank_Stock, Dental_Hospital;
    MenuItem CheckUnCheck, upload, allChk;
    String getBranchId, getSale, getChecking_Counter_Amount_Pcs, getUnpaid, getRg, getAxis, getHdfc, getCash_On_Hand, getOpening_Cash, getBank_Deposit, getCash_Short, getSynd_Odh,
            getUnpaid_Parcels_Amount_Type, getUnpaid_Parcels_Amount, getNight_Cash_or_Opening_Cash, getDaily_Units_Power_or_Fuel, getDaily_Extra_Staff, getWeek_Off_Holiday, getDaily_Absent_Staff,
            getDaily_Present_or_Tota, getTea_x_Present_Staff_Custo, getSpl_Less, getDaily_Water_Jars, getPlastic_Covers_or_Jute_Bags_Amt, getHow_Many_Staff_Shop_Open_or_Dvlp,
            getPrayer, getCompounding_or_Late_Coming, getTiffin_Box, getStaff_Low_Sale_Remainder, getGate_Pass_with_Receipt_Number, getCancel_Bills_Sign_with_Photos, getAlterness_Check_Meterage_SS,
            getAlterness_Check_inward_qty, getAlterness_Check_secu_Alert, getMaintaince_Sheet, getNight_Watchman_Call_Alert, getPhCall_Shop_Open_or_Dvlp, getDB_and_SB_Entry, getNoof_Parcel_or_LRs,
            getVisiting_with_report, getEye_Hospital_OP_nd_Surgeries_OP, getEye_Hospital_OP_nd_Surgeries_Surgery_Free, getEye_Hospital_OP_nd_Surgeries_Pay, getBlood_Bank_Stock, getDental_Hospital, getRoles;
    TextView mTextBranchId, mTextSale, mTextChecking_Counter_Amount_Pcs, mTextUnpaid, mTextRg, mTextAxis, mTextHdfc, mTextCash_On_Hand, mTextOpening_Cash, mTextBank_Deposit, mTextCash_Short, mTextSynd_Odh,
            mTextUnpaid_Parcels_Amount_Type, mTextUnpaid_Parcels_Amount, mTextNight_Cash_or_Opening_Cash, mTextDaily_Units_Power_or_Fuel, mTextDaily_Extra_Staff, mTextWeek_Off_Holiday, mTextDaily_Absent_Staff,
            mTextDaily_Present_or_Tota, mTextTea_x_Present_Staff_Custo, mTextSpl_Less, mTextDaily_Water_Jars, mTextPlastic_Covers_or_Jute_Bags_Amt, mTextHow_Many_Staff_Shop_Open_or_Dvlp, mTextPrayer,
            mTextCompounding_or_Late_Coming, mTextTiffin_Box, mTextStaff_Low_Sale_Remainder, mTextGate_Pass_with_Receipt_Number, mTextCancel_Bills_Sign_with_Photos, mTextAlterness_Check_Meterage_SS,
            mTextAlterness_Check_inward_qty, mTextAlterness_Check_secu_Alert, mTextMaintaince_Sheet, mTextNight_Watchman_Call_Alert, mTextPhCall_Shop_Open_or_Dvlp, mTextDB_and_SB_Entry, mTextNoof_Parcel_or_LRs,
            mTextVisiting_with_report, mTextEye_Hospital_OP_nd_Surgeries_OP, mTextEye_Hospital_OP_nd_Surgeries_Surgery_Free, mTextEye_Hospital_OP_nd_Surgeries_Pay, mTextBlood_Bank_Stock, mTextDental_Hospital, mTextRoles;

    Context context;
    private RadioButton mRadioC, mRadioM;
    private String checkRadiobtn;
    String companyID = "";
    String cityname = "";
    int branchpos = 0;
    private Spinner selectBranches;
    RadioGroup mRadioGroup;

    public static DailyReportFragment newInstance() {
        Bundle args = new Bundle();
        DailyReportFragment fragment = new DailyReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daily_reports, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Daily Report</font>"));
        listener = this;
        context = getActivity();
        branches = new ArrayList<>();
        obj = new JSONObject();
        uid = SharedPreferenceUtil.getInstance().getString(getActivity(), "Uid", "Uid");
        Role = SharedPreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user");
        branch = SharedPreferenceUtil.getInstance().getString(getActivity(), "BranchName", "BranchName");
        branchID = SharedPreferenceUtil.getInstance().getString(getActivity(), ProjectVariables.COMPNAME, "BranchName");
        intila();
        getBranches();
        return view;
    }

    private void getBranches() {
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
            try {
                AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 107, ProjectVariables.BRANCHES, listener, null, "");
                post.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        selectBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                branches  = AppUtil.getBranchesInfo();
                branchpos = position;
                companyID = branches.get(position).getBranchId();
                cityname = branches.get(position).getBranchName();
                getDailyReport();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDailyReport() {
        jsonObject = new JSONObject();
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
            try {
                jsonObject.accumulate("BranchId", companyID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AsynHttpPost143 post143 = new AsynHttpPost143(getActivity(), 1, 106, ProjectVariables.GETDAILYREPORT, listener, jsonObject, "",true);
                post143.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void intila() {
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        selectBranches = (Spinner) view.findViewById(R.id.id_Branch);
        mBranchId = (EditText) view.findViewById(R.id.id_BranchId);
        mSale = (EditText) view.findViewById(R.id.id_Sale);
        mChecking_Counter_Amount_Pcs = (EditText) view.findViewById(R.id.id_Checking_Counter_Amount_Pcs);
        mUnpaid = (EditText) view.findViewById(R.id.id_Unpaid);
        mRg = (EditText) view.findViewById(R.id.id_Rg);
        mAxis = (EditText) view.findViewById(R.id.id_Axis);
        mHdfc = (EditText) view.findViewById(R.id.id_Hdfc);
        mCash_On_Hand = (EditText) view.findViewById(R.id.id_Cash_On_Hand);
        mOpening_Cash = (EditText) view.findViewById(R.id.id_Opening_Cash);
        mBank_Deposit = (EditText) view.findViewById(R.id.id_Bank_Deposit);
        mCash_Short = (EditText) view.findViewById(R.id.id_Cash_Short);
        mSynd_Odh = (EditText) view.findViewById(R.id.id_Synd_Odh);
        //mUnpaid_Parcels_Amount_Type = (EditText) view.findViewById(R.id.id_Unpaid_Parcels_Amount_Type);
        mUnpaid_Parcels_Amount = (EditText) view.findViewById(R.id.id_Unpaid_Parcels_Amount);
        mNight_Cash_or_Opening_Cash = (EditText) view.findViewById(R.id.id_Night_Cash_or_Opening_Cash);
        mDaily_Units_Power_or_Fuel = (EditText) view.findViewById(R.id.id_Daily_Units_Power_or_Fuel);
        mDaily_Extra_Staff = (EditText) view.findViewById(R.id.id_Daily_Extra_Staff);
        mWeek_Off_Holiday = (EditText) view.findViewById(R.id.id_Week_Off_Holiday);
        mDaily_Absent_Staff = (EditText) view.findViewById(R.id.id_Daily_Absent_Staff);
        mDaily_Present_or_Tota = (EditText) view.findViewById(R.id.id_Daily_Present_or_Tota);
        mTea_x_Present_Staff_Custo = (EditText) view.findViewById(R.id.id_Tea_x_Present_Staff_Custo);
        mSpl_Less = (EditText) view.findViewById(R.id.id_Spl_Less);
        mDaily_Water_Jars = (EditText) view.findViewById(R.id.id_Daily_Water_Jars);
        mPlastic_Covers_or_Jute_Bags_Amt = (EditText) view.findViewById(R.id.id_Plastic_Covers_or_Jute_Bags_Amt);
        mHow_Many_Staff_Shop_Open_or_Dvlp = (EditText) view.findViewById(R.id.id_How_Many_Staff_Shop_Open_or_Dvlp);
        mPrayer = (EditText) view.findViewById(R.id.id_Prayer);
        mCompounding_or_Late_Coming = (EditText) view.findViewById(R.id.id_Compounding_or_Late_Coming);
        mTiffin_Box = (EditText) view.findViewById(R.id.id_Tiffin_Box);
        mStaff_Low_Sale_Remainder = (EditText) view.findViewById(R.id.id_Staff_Low_Sale_Remainder);
        mGate_Pass_with_Receipt_Number = (EditText) view.findViewById(R.id.id_Gate_Pass_with_Receipt_Number);
        mCancel_Bills_Sign_with_Photos = (EditText) view.findViewById(R.id.id_Cancel_Bills_Sign_with_Photos);
        mAlterness_Check_Meterage_SS = (EditText) view.findViewById(R.id.id_Alterness_Check_Meterage_SS);
        mAlterness_Check_inward_qty = (EditText) view.findViewById(R.id.id_Alterness_Check_inward_qty);
        mAlterness_Check_secu_Alert = (EditText) view.findViewById(R.id.id_Alterness_Check_secu_Alert);
        mMaintaince_Sheet = (EditText) view.findViewById(R.id.id_Maintaince_Sheet);
        mNight_Watchman_Call_Alert = (EditText) view.findViewById(R.id.id_Night_Watchman_Call_Alert);
        mPhCall_Shop_Open_or_Dvlp = (EditText) view.findViewById(R.id.id_PhCall_Shop_Open_or_Dvlp);
        mDB_and_SB_Entry = (EditText) view.findViewById(R.id.id_DB_and_SB_Entry);
        mNoof_Parcel_or_LRs = (EditText) view.findViewById(R.id.id_Noof_Parcel_or_LRs);
        mVisiting_with_report = (EditText) view.findViewById(R.id.id_Visiting_with_report);
        mEye_Hospital_OP_nd_Surgeries_OP = (EditText) view.findViewById(R.id.id_Eye_Hospital_OP_nd_Surgeries_OP);
        mEye_Hospital_OP_nd_Surgeries_Surgery_Free = (EditText) view.findViewById(R.id.id_Eye_Hospital_OP_nd_Surgeries_Surgery_Free);
        mEye_Hospital_OP_nd_Surgeries_Pay = (EditText) view.findViewById(R.id.id_Eye_Hospital_OP_nd_Surgeries_Pay);
        mBlood_Bank_Stock = (EditText) view.findViewById(R.id.id_Blood_Bank_Stock);
        mDental_Hospital = (EditText) view.findViewById(R.id.id_Dental_Hospital);
        mRadioC = (RadioButton) view.findViewById(R.id.radio_C);
        mRadioM = (RadioButton) view.findViewById(R.id.radio_M);

        mTextSale = (TextView) view.findViewById(R.id.id_TextSale);
        mTextChecking_Counter_Amount_Pcs = (TextView) view.findViewById(R.id.id_TextChecking_Counter_Amount_Pcs);
        mTextUnpaid = (TextView) view.findViewById(R.id.id_TextUnpaid);
        mTextRg = (TextView) view.findViewById(R.id.id_TextRg);
        mTextAxis = (TextView) view.findViewById(R.id.id_TextAxis);
        mTextHdfc = (TextView) view.findViewById(R.id.id_TextHdfc);
        mTextCash_On_Hand = (TextView) view.findViewById(R.id.id_TextCash_On_Hand);
        mTextOpening_Cash = (TextView) view.findViewById(R.id.id_TextOpening_Cash);
        mTextBank_Deposit = (TextView) view.findViewById(R.id.id_TextBank_Deposit);
        mTextCash_Short = (TextView) view.findViewById(R.id.id_TextCash_Short);
        mTextSynd_Odh = (TextView) view.findViewById(R.id.id_TextSynd_Odh);
        //mTextUnpaid_Parcels_Amount_Type = (TextView) view.findViewById(R.id.id_TextUnpaid_Parcels_Amount_Type);
        mTextUnpaid_Parcels_Amount = (TextView) view.findViewById(R.id.id_TextUnpaid_Parcels_Amount);
        mTextNight_Cash_or_Opening_Cash = (TextView) view.findViewById(R.id.id_TextNight_Cash_or_Opening_Cash);
        mTextDaily_Units_Power_or_Fuel = (TextView) view.findViewById(R.id.id_TextDaily_Units_Power_or_Fuel);
        mTextDaily_Extra_Staff = (TextView) view.findViewById(R.id.id_TextDaily_Extra_Staff);
        mTextWeek_Off_Holiday = (TextView) view.findViewById(R.id.id_TextWeek_Off_Holiday);
        mTextDaily_Absent_Staff = (TextView) view.findViewById(R.id.id_TextDaily_Absent_Staff);
        mTextDaily_Present_or_Tota = (TextView) view.findViewById(R.id.id_TextDaily_Present_or_Tota);
        mTextTea_x_Present_Staff_Custo = (TextView) view.findViewById(R.id.id_TextTea_x_Present_Staff_Custo);
        mTextSpl_Less = (TextView) view.findViewById(R.id.id_TextSpl_Less);
        mTextDaily_Water_Jars = (TextView) view.findViewById(R.id.id_TextDaily_Water_Jars);
        mTextPlastic_Covers_or_Jute_Bags_Amt = (TextView) view.findViewById(R.id.id_TextPlastic_Covers_or_Jute_Bags_Amt);
        mTextHow_Many_Staff_Shop_Open_or_Dvlp = (TextView) view.findViewById(R.id.id_TextHow_Many_Staff_Shop_Open_or_Dvlp);
        mTextPrayer = (TextView) view.findViewById(R.id.id_TextPrayer);
        mTextCompounding_or_Late_Coming = (TextView) view.findViewById(R.id.id_TextCompounding_or_Late_Coming);
        mTextTiffin_Box = (TextView) view.findViewById(R.id.id_TextTiffin_Box);
        mTextStaff_Low_Sale_Remainder = (TextView) view.findViewById(R.id.id_TextStaff_Low_Sale_Remainder);
        mTextGate_Pass_with_Receipt_Number = (TextView) view.findViewById(R.id.id_TextGate_Pass_with_Receipt_Number);
        mTextCancel_Bills_Sign_with_Photos = (TextView) view.findViewById(R.id.id_TextCancel_Bills_Sign_with_Photos);
        mTextAlterness_Check_Meterage_SS = (TextView) view.findViewById(R.id.id_TextAlterness_Check_Meterage_SS);
        mTextAlterness_Check_inward_qty = (TextView) view.findViewById(R.id.id_TextAlterness_Check_inward_qty);
        mTextAlterness_Check_secu_Alert = (TextView) view.findViewById(R.id.id_TextAlterness_Check_secu_Alert);
        mTextMaintaince_Sheet = (TextView) view.findViewById(R.id.id_TextMaintaince_Sheet);
        mTextNight_Watchman_Call_Alert = (TextView) view.findViewById(R.id.id_TextNight_Watchman_Call_Alert);
        mTextPhCall_Shop_Open_or_Dvlp = (TextView) view.findViewById(R.id.id_TextPhCall_Shop_Open_or_Dvlp);
        mTextDB_and_SB_Entry = (TextView) view.findViewById(R.id.id_TextDB_and_SB_Entry);
        mTextNoof_Parcel_or_LRs = (TextView) view.findViewById(R.id.id_TextNoof_Parcel_or_LRs);
        mTextVisiting_with_report = (TextView) view.findViewById(R.id.id_TextVisiting_with_report);
        mTextEye_Hospital_OP_nd_Surgeries_OP = (TextView) view.findViewById(R.id.id_TextEye_Hospital_OP_nd_Surgeries_OP);
        mTextEye_Hospital_OP_nd_Surgeries_Surgery_Free = (TextView) view.findViewById(R.id.id_TextEye_Hospital_OP_nd_Surgeries_Surgery_Free);
        mTextEye_Hospital_OP_nd_Surgeries_Pay = (TextView) view.findViewById(R.id.id_TextEye_Hospital_OP_nd_Surgeries_Pay);
        mTextBlood_Bank_Stock = (TextView) view.findViewById(R.id.id_TextBlood_Bank_Stock);
        mTextDental_Hospital = (TextView) view.findViewById(R.id.id_TextDental_Hospital);
        radioClick();
        setTextValues();
        alternatDisplay();

    }

    private void alternatDisplay() {
        try {
            if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {

                mTextSale.setVisibility(View.VISIBLE);
                mTextChecking_Counter_Amount_Pcs.setVisibility(View.VISIBLE);
                mTextUnpaid.setVisibility(View.VISIBLE);
                mTextRg.setVisibility(View.VISIBLE);
                mTextAxis.setVisibility(View.VISIBLE);
                mTextHdfc.setVisibility(View.VISIBLE);
                mTextCash_On_Hand.setVisibility(View.VISIBLE);
                mTextOpening_Cash.setVisibility(View.VISIBLE);
                mTextBank_Deposit.setVisibility(View.VISIBLE);
                mTextCash_Short.setVisibility(View.VISIBLE);
                mTextSynd_Odh.setVisibility(View.VISIBLE);
                //mTextUnpaid_Parcels_Amount_Type.setVisibility(View.VISIBLE);
                mTextUnpaid_Parcels_Amount.setVisibility(View.VISIBLE);
                mTextNight_Cash_or_Opening_Cash.setVisibility(View.VISIBLE);
                mTextDaily_Units_Power_or_Fuel.setVisibility(View.VISIBLE);
                mTextDaily_Extra_Staff.setVisibility(View.VISIBLE);
                mTextWeek_Off_Holiday.setVisibility(View.VISIBLE);
                mTextDaily_Absent_Staff.setVisibility(View.VISIBLE);
                mTextDaily_Present_or_Tota.setVisibility(View.VISIBLE);
                mTextTea_x_Present_Staff_Custo.setVisibility(View.VISIBLE);
                mTextSpl_Less.setVisibility(View.VISIBLE);
                mTextDaily_Water_Jars.setVisibility(View.VISIBLE);
                mTextPlastic_Covers_or_Jute_Bags_Amt.setVisibility(View.VISIBLE);
                mTextHow_Many_Staff_Shop_Open_or_Dvlp.setVisibility(View.VISIBLE);
                mTextPrayer.setVisibility(View.VISIBLE);
                mTextCompounding_or_Late_Coming.setVisibility(View.VISIBLE);
                mTextTiffin_Box.setVisibility(View.VISIBLE);
                mTextStaff_Low_Sale_Remainder.setVisibility(View.VISIBLE);
                mTextGate_Pass_with_Receipt_Number.setVisibility(View.VISIBLE);
                mTextCancel_Bills_Sign_with_Photos.setVisibility(View.VISIBLE);
                mTextAlterness_Check_Meterage_SS.setVisibility(View.VISIBLE);
                mTextAlterness_Check_inward_qty.setVisibility(View.VISIBLE);
                mTextAlterness_Check_secu_Alert.setVisibility(View.VISIBLE);
                mTextMaintaince_Sheet.setVisibility(View.VISIBLE);
                mTextNight_Watchman_Call_Alert.setVisibility(View.VISIBLE);
                mTextPhCall_Shop_Open_or_Dvlp.setVisibility(View.VISIBLE);
                mTextDB_and_SB_Entry.setVisibility(View.VISIBLE);
                mTextNoof_Parcel_or_LRs.setVisibility(View.VISIBLE);
                mTextVisiting_with_report.setVisibility(View.VISIBLE);
                mTextEye_Hospital_OP_nd_Surgeries_OP.setVisibility(View.VISIBLE);
                mTextEye_Hospital_OP_nd_Surgeries_Surgery_Free.setVisibility(View.VISIBLE);
                mTextEye_Hospital_OP_nd_Surgeries_Pay.setVisibility(View.VISIBLE);
                mTextBlood_Bank_Stock.setVisibility(View.VISIBLE);
                mTextDental_Hospital.setVisibility(View.VISIBLE);

                mRadioGroup.setVisibility(View.GONE);
                mSale.setVisibility(View.GONE);
                mChecking_Counter_Amount_Pcs.setVisibility(View.GONE);
                mUnpaid.setVisibility(View.GONE);
                mRg.setVisibility(View.GONE);
                mAxis.setVisibility(View.GONE);
                mHdfc.setVisibility(View.GONE);
                mCash_On_Hand.setVisibility(View.GONE);
                mOpening_Cash.setVisibility(View.GONE);
                mBank_Deposit.setVisibility(View.GONE);
                mCash_Short.setVisibility(View.GONE);
                mSynd_Odh.setVisibility(View.GONE);
                //mUnpaid_Parcels_Amount_Type.setVisibility(View.GONE);
                mUnpaid_Parcels_Amount.setVisibility(View.GONE);
                mNight_Cash_or_Opening_Cash.setVisibility(View.GONE);
                mDaily_Units_Power_or_Fuel.setVisibility(View.GONE);
                mDaily_Extra_Staff.setVisibility(View.GONE);
                mWeek_Off_Holiday.setVisibility(View.GONE);
                mDaily_Absent_Staff.setVisibility(View.GONE);
                mDaily_Present_or_Tota.setVisibility(View.GONE);
                mTea_x_Present_Staff_Custo.setVisibility(View.GONE);
                mSpl_Less.setVisibility(View.GONE);
                mDaily_Water_Jars.setVisibility(View.GONE);
                mPlastic_Covers_or_Jute_Bags_Amt.setVisibility(View.GONE);
                mHow_Many_Staff_Shop_Open_or_Dvlp.setVisibility(View.GONE);
                mPrayer.setVisibility(View.GONE);
                mCompounding_or_Late_Coming.setVisibility(View.GONE);
                mTiffin_Box.setVisibility(View.GONE);
                mStaff_Low_Sale_Remainder.setVisibility(View.GONE);
                mGate_Pass_with_Receipt_Number.setVisibility(View.GONE);
                mCancel_Bills_Sign_with_Photos.setVisibility(View.GONE);
                mAlterness_Check_Meterage_SS.setVisibility(View.GONE);
                mAlterness_Check_inward_qty.setVisibility(View.GONE);
                mAlterness_Check_secu_Alert.setVisibility(View.GONE);
                mMaintaince_Sheet.setVisibility(View.GONE);
                mNight_Watchman_Call_Alert.setVisibility(View.GONE);
                mPhCall_Shop_Open_or_Dvlp.setVisibility(View.GONE);
                mDB_and_SB_Entry.setVisibility(View.GONE);
                mNoof_Parcel_or_LRs.setVisibility(View.GONE);
                mVisiting_with_report.setVisibility(View.GONE);
                mEye_Hospital_OP_nd_Surgeries_OP.setVisibility(View.GONE);
                mEye_Hospital_OP_nd_Surgeries_Surgery_Free.setVisibility(View.GONE);
                mEye_Hospital_OP_nd_Surgeries_Pay.setVisibility(View.GONE);
                mBlood_Bank_Stock.setVisibility(View.GONE);
                mDental_Hospital.setVisibility(View.GONE);

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextValues() {
        mBranchId.setText(branch);
    }

    private void radioClick() {

        mRadioC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkRadiobtn = "C";
                }
            }
        });
        mRadioM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkRadiobtn = "M";
                }
            }
        });
    }

    private void intialVariables() {
        try {
            Sale = mSale.getText().toString();
            Checking_Counter_Amount_Pcs = mChecking_Counter_Amount_Pcs.getText().toString();
            Unpaid = mUnpaid.getText().toString();
            Rg = mRg.getText().toString();
            Axis = mAxis.getText().toString();
            Hdfc = mHdfc.getText().toString();
            Cash_On_Hand = mCash_On_Hand.getText().toString();
            Opening_Cash = mOpening_Cash.getText().toString();
            Bank_Deposit = mBank_Deposit.getText().toString();
            Cash_Short = mCash_Short.getText().toString();
            Synd_Odh = mSynd_Odh.getText().toString();
            /* Unpaid_Parcels_Amount_Type = mUnpaid_Parcels_Amount_Type.getText().toString();*/
            Unpaid_Parcels_Amount = mUnpaid_Parcels_Amount.getText().toString();
            Night_Cash_or_Opening_Cash = mNight_Cash_or_Opening_Cash.getText().toString();
            Daily_Units_Power_or_Fuel = mDaily_Units_Power_or_Fuel.getText().toString();
            Daily_Extra_Staff = mDaily_Extra_Staff.getText().toString();
            Week_Off_Holiday = mWeek_Off_Holiday.getText().toString();
            Daily_Absent_Staff = mDaily_Absent_Staff.getText().toString();
            Daily_Present_or_Tota = mDaily_Present_or_Tota.getText().toString();
            Tea_x_Present_Staff_Custo = mTea_x_Present_Staff_Custo.getText().toString();
            Spl_Less = mSpl_Less.getText().toString();
            Daily_Water_Jars = mDaily_Water_Jars.getText().toString();
            Plastic_Covers_or_Jute_Bags_Amt = mPlastic_Covers_or_Jute_Bags_Amt.getText().toString();
            How_Many_Staff_Shop_Open_or_Dvlp = mHow_Many_Staff_Shop_Open_or_Dvlp.getText().toString();
            Prayer = mPrayer.getText().toString();
            Compounding_or_Late_Coming = mCompounding_or_Late_Coming.getText().toString();
            Tiffin_Box = mTiffin_Box.getText().toString();
            Staff_Low_Sale_Remainder = mStaff_Low_Sale_Remainder.getText().toString();
            Gate_Pass_with_Receipt_Number = mGate_Pass_with_Receipt_Number.getText().toString();
            Cancel_Bills_Sign_with_Photos = mCancel_Bills_Sign_with_Photos.getText().toString();
            Alterness_Check_Meterage_SS = mAlterness_Check_Meterage_SS.getText().toString();
            Alterness_Check_inward_qty = mAlterness_Check_inward_qty.getText().toString();
            Alterness_Check_secu_Alert = mAlterness_Check_secu_Alert.getText().toString();
            Maintaince_Sheet = mMaintaince_Sheet.getText().toString();
            Night_Watchman_Call_Alert = mNight_Watchman_Call_Alert.getText().toString();
            PhCall_Shop_Open_or_Dvlp = mPhCall_Shop_Open_or_Dvlp.getText().toString();
            DB_and_SB_Entry = mDB_and_SB_Entry.getText().toString();
            Noof_Parcel_or_LRs = mNoof_Parcel_or_LRs.getText().toString();
            Visiting_with_report = mVisiting_with_report.getText().toString();
            Eye_Hospital_OP_nd_Surgeries_OP = mEye_Hospital_OP_nd_Surgeries_OP.getText().toString();
            Eye_Hospital_OP_nd_Surgeries_Surgery_Free = mEye_Hospital_OP_nd_Surgeries_Surgery_Free.getText().toString();
            Eye_Hospital_OP_nd_Surgeries_Pay = mEye_Hospital_OP_nd_Surgeries_Pay.getText().toString();
            Blood_Bank_Stock = mBlood_Bank_Stock.getText().toString();
            Dental_Hospital = mDental_Hospital.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UploadData() {
        intialVariables();
        jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(ProjectVariables.UserId, uid);
            jsonObject.accumulate(ProjectVariables.BranchId, branchID);
            jsonObject.accumulate(ProjectVariables.Sale, Sale);
            jsonObject.accumulate(ProjectVariables.Checking_Counter_Amount_Pcs, Checking_Counter_Amount_Pcs);
            jsonObject.accumulate(ProjectVariables.Unpaid, Unpaid);
            jsonObject.accumulate(ProjectVariables.Rg, Rg);
            jsonObject.accumulate(ProjectVariables.Axis, Axis);
            jsonObject.accumulate(ProjectVariables.Hdfc, Hdfc);
            jsonObject.accumulate(ProjectVariables.Cash_On_Hand, Cash_On_Hand);
            jsonObject.accumulate(ProjectVariables.Opening_Cash, Opening_Cash);
            jsonObject.accumulate(ProjectVariables.Bank_Deposit, Bank_Deposit);
            jsonObject.accumulate(ProjectVariables.Cash_Short, Cash_Short);
            jsonObject.accumulate(ProjectVariables.Synd_Odh, Synd_Odh);
            jsonObject.accumulate(ProjectVariables.Unpaid_Parcels_Amount_Type, checkRadiobtn);
            jsonObject.accumulate(ProjectVariables.Unpaid_Parcels_Amount, Unpaid_Parcels_Amount);
            jsonObject.accumulate(ProjectVariables.Night_Cash_or_Opening_Cash, Night_Cash_or_Opening_Cash);
            jsonObject.accumulate(ProjectVariables.Daily_Units_Power_or_Fuel, Daily_Units_Power_or_Fuel);
            jsonObject.accumulate(ProjectVariables.Daily_Extra_Staff, Daily_Extra_Staff);
            jsonObject.accumulate(ProjectVariables.Week_Off_Holiday, Week_Off_Holiday);
            jsonObject.accumulate(ProjectVariables.Daily_Absent_Staff, Daily_Absent_Staff);
            jsonObject.accumulate(ProjectVariables.Daily_Present_or_Tota, Daily_Present_or_Tota);
            jsonObject.accumulate(ProjectVariables.Tea_x_Present_Staff_Custo, Tea_x_Present_Staff_Custo);
            jsonObject.accumulate(ProjectVariables.Spl_Less, Spl_Less);
            jsonObject.accumulate(ProjectVariables.Daily_Water_Jars, Daily_Water_Jars);
            jsonObject.accumulate(ProjectVariables.Plastic_Covers_or_Jute_Bags_Amt, Plastic_Covers_or_Jute_Bags_Amt);
            jsonObject.accumulate(ProjectVariables.How_Many_Staff_Shop_Open_or_Dvlp, How_Many_Staff_Shop_Open_or_Dvlp);
            jsonObject.accumulate(ProjectVariables.Prayer, Prayer);
            jsonObject.accumulate(ProjectVariables.Compounding_or_Late_Coming, Compounding_or_Late_Coming);
            jsonObject.accumulate(ProjectVariables.Tiffin_Box, Tiffin_Box);
            jsonObject.accumulate(ProjectVariables.Staff_Low_Sale_Remainder, Staff_Low_Sale_Remainder);
            jsonObject.accumulate(ProjectVariables.Gate_Pass_with_Receipt_Number, Gate_Pass_with_Receipt_Number);
            jsonObject.accumulate(ProjectVariables.Cancel_Bills_Sign_with_Photos, Cancel_Bills_Sign_with_Photos);
            jsonObject.accumulate(ProjectVariables.Alterness_Check_Meterage_SS, Alterness_Check_Meterage_SS);
            jsonObject.accumulate(ProjectVariables.Alterness_Check_inward_qty, Alterness_Check_inward_qty);
            jsonObject.accumulate(ProjectVariables.Alterness_Check_secu_Alert, Alterness_Check_secu_Alert);
            jsonObject.accumulate(ProjectVariables.Maintaince_Sheet, Maintaince_Sheet);
            jsonObject.accumulate(ProjectVariables.Night_Watchman_Call_Alert, Night_Watchman_Call_Alert);
            jsonObject.accumulate(ProjectVariables.PhCall_Shop_Open_or_Dvlp, PhCall_Shop_Open_or_Dvlp);
            jsonObject.accumulate(ProjectVariables.DB_and_SB_Entry, DB_and_SB_Entry);
            jsonObject.accumulate(ProjectVariables.Noof_Parcel_or_LRs, Noof_Parcel_or_LRs);
            jsonObject.accumulate(ProjectVariables.Visiting_with_report, Visiting_with_report);
            jsonObject.accumulate(ProjectVariables.Eye_Hospital_OP_nd_Surgeries_OP, Eye_Hospital_OP_nd_Surgeries_OP);
            jsonObject.accumulate(ProjectVariables.Eye_Hospital_OP_nd_Surgeries_Surgery_Free, Eye_Hospital_OP_nd_Surgeries_Surgery_Free);
            jsonObject.accumulate(ProjectVariables.Eye_Hospital_OP_nd_Surgeries_Pay, Eye_Hospital_OP_nd_Surgeries_Pay);
            jsonObject.accumulate(ProjectVariables.Blood_Bank_Stock, Blood_Bank_Stock);
            jsonObject.accumulate(ProjectVariables.Dental_Hospital, Dental_Hospital);
            jsonObject.accumulate(ProjectVariables.Roles, Role);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppUtil.isInternetAvailable(getActivity())) {
            try {
                AsynHttpPost post143 = new AsynHttpPost(getActivity(), 1, 105, ProjectVariables.DAILYREPORT, listener, jsonObject, "");
                post143.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_incharges, menu);
        CheckUnCheck = menu.getItem(0);
        if (Role.equalsIgnoreCase("4") || Role.equalsIgnoreCase("1")) {
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
                return true;
            case R.id.upload_data:
                UploadData();
                return true;
        }
        return (super.onOptionsItemSelected(item)
        );
    }

    @Override
    public void getData(String s, String status, int rType, String temp) {
        if (rType == 105) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String result = jsonObject.getString("Result");
                    if (result.equalsIgnoreCase("inserted successfully")) {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        Log.e("manager =", result);
                        setEdiTextNull();
                    } else {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        setEdiTextNull();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rType == 106) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    JSONArray array = new JSONArray(s);
                    obj = array.getJSONObject(0);
                    String result = obj.getString("Status");
                    if (result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        setTextNull();
                    }else {
                        getJSONObjectValues();
                        setJSONValues();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else if (rType == 107) {
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

    private void setTextNull() {
        mTextSale.setText("");
        mTextChecking_Counter_Amount_Pcs.setText("");
        mTextUnpaid.setText("");
        mTextRg.setText("");
        mTextAxis.setText("");
        mTextHdfc.setText("");
        mTextCash_On_Hand.setText("");
        mTextOpening_Cash.setText("");
        mTextBank_Deposit.setText("");
        mTextCash_Short.setText("");
        mTextSynd_Odh.setText("");
        //mTextUnpaid_Parcels_Amount_Type.setText("");
        mTextUnpaid_Parcels_Amount.setText("");
        mTextNight_Cash_or_Opening_Cash.setText("");
        mTextDaily_Units_Power_or_Fuel.setText("");
        mTextDaily_Extra_Staff.setText("");
        mTextWeek_Off_Holiday.setText("");
        mTextDaily_Absent_Staff.setText("");
        mTextDaily_Present_or_Tota.setText("");
        mTextTea_x_Present_Staff_Custo.setText("");
        mTextSpl_Less.setText("");
        mTextDaily_Water_Jars.setText("");
        mTextPlastic_Covers_or_Jute_Bags_Amt.setText("");
        mTextHow_Many_Staff_Shop_Open_or_Dvlp.setText("");
        mTextPrayer.setText("");
        mTextCompounding_or_Late_Coming.setText("");
        mTextTiffin_Box.setText("");
        mTextStaff_Low_Sale_Remainder.setText("");
        mTextGate_Pass_with_Receipt_Number.setText("");
        mTextCancel_Bills_Sign_with_Photos.setText("");
        mTextAlterness_Check_Meterage_SS.setText("");
        mTextAlterness_Check_inward_qty.setText("");
        mTextAlterness_Check_secu_Alert.setText("");
        mTextMaintaince_Sheet.setText("");
        mTextNight_Watchman_Call_Alert.setText("");
        mTextPhCall_Shop_Open_or_Dvlp.setText("");
        mTextDB_and_SB_Entry.setText("");
        mTextNoof_Parcel_or_LRs.setText("");
        mTextVisiting_with_report.setText("");
        mTextEye_Hospital_OP_nd_Surgeries_OP.setText("");
        mTextEye_Hospital_OP_nd_Surgeries_Surgery_Free.setText("");
        mTextEye_Hospital_OP_nd_Surgeries_Pay.setText("");
        mTextBlood_Bank_Stock.setText("");
        mTextDental_Hospital.setText("");
    }

    private void setEdiTextNull() {
        mSale.setText("");
        mChecking_Counter_Amount_Pcs.setText("");
        mUnpaid.setText("");
        mRg.setText("");
        mAxis.setText("");
        mHdfc.setText("");
        mCash_On_Hand.setText("");
        mOpening_Cash.setText("");
        mBank_Deposit.setText("");
        mCash_Short.setText("");
        mSynd_Odh.setText("");
        //mUnpaid_Parcels_Amount_Type.setText("");
        mUnpaid_Parcels_Amount.setText("");
        mNight_Cash_or_Opening_Cash.setText("");
        mDaily_Units_Power_or_Fuel.setText("");
        mDaily_Extra_Staff.setText("");
        mWeek_Off_Holiday.setText("");
        mDaily_Absent_Staff.setText("");
        mDaily_Present_or_Tota.setText("");
        mTea_x_Present_Staff_Custo.setText("");
        mSpl_Less.setText("");
        mDaily_Water_Jars.setText("");
        mPlastic_Covers_or_Jute_Bags_Amt.setText("");
        mHow_Many_Staff_Shop_Open_or_Dvlp.setText("");
        mPrayer.setText("");
        mCompounding_or_Late_Coming.setText("");
        mTiffin_Box.setText("");
        mStaff_Low_Sale_Remainder.setText("");
        mGate_Pass_with_Receipt_Number.setText("");
        mCancel_Bills_Sign_with_Photos.setText("");
        mAlterness_Check_Meterage_SS.setText("");
        mAlterness_Check_inward_qty.setText("");
        mAlterness_Check_secu_Alert.setText("");
        mMaintaince_Sheet.setText("");
        mNight_Watchman_Call_Alert.setText("");
        mPhCall_Shop_Open_or_Dvlp.setText("");
        mDB_and_SB_Entry.setText("");
        mNoof_Parcel_or_LRs.setText("");
        mVisiting_with_report.setText("");
        mEye_Hospital_OP_nd_Surgeries_OP.setText("");
        mEye_Hospital_OP_nd_Surgeries_Surgery_Free.setText("");
        mEye_Hospital_OP_nd_Surgeries_Pay.setText("");
        mBlood_Bank_Stock.setText("");
        mDental_Hospital.setText("");
    }

    private void getJSONObjectValues() {
        try {
            getSale = obj.getString(ProjectVariables.Sale);
            getChecking_Counter_Amount_Pcs = obj.getString(ProjectVariables.Checking_Counter_Amount_Pcs);
            getUnpaid = obj.getString(ProjectVariables.Unpaid);
            getRg = obj.getString(ProjectVariables.Rg);
            getAxis = obj.getString(ProjectVariables.Axis);
            getHdfc = obj.getString(ProjectVariables.Hdfc);
            getCash_On_Hand = obj.getString(ProjectVariables.Cash_On_Hand);
            getOpening_Cash = obj.getString(ProjectVariables.Opening_Cash);
            getBank_Deposit = obj.getString(ProjectVariables.Bank_Deposit);
            getCash_Short = obj.getString(ProjectVariables.Cash_Short);
            getSynd_Odh = obj.getString(ProjectVariables.Synd_Odh);
            getUnpaid_Parcels_Amount_Type = obj.getString(ProjectVariables.Unpaid_Parcels_Amount_Type);
            getUnpaid_Parcels_Amount = obj.getString(ProjectVariables.Unpaid_Parcels_Amount);
            getNight_Cash_or_Opening_Cash = obj.getString(ProjectVariables.Night_Cash_or_Opening_Cash);
            getDaily_Units_Power_or_Fuel = obj.getString(ProjectVariables.Daily_Units_Power_or_Fuel);
            getDaily_Extra_Staff = obj.getString(ProjectVariables.Daily_Extra_Staff);
            getWeek_Off_Holiday = obj.getString(ProjectVariables.Week_Off_Holiday);
            getDaily_Absent_Staff = obj.getString(ProjectVariables.Daily_Absent_Staff);
            getDaily_Present_or_Tota = obj.getString(ProjectVariables.Daily_Present_or_Tota);
            getTea_x_Present_Staff_Custo = obj.getString(ProjectVariables.Tea_x_Present_Staff_Custo);
            getSpl_Less = obj.getString(ProjectVariables.Spl_Less);
            getDaily_Water_Jars = obj.getString(ProjectVariables.Daily_Water_Jars);
            getPlastic_Covers_or_Jute_Bags_Amt = obj.getString(ProjectVariables.Plastic_Covers_or_Jute_Bags_Amt);
            getHow_Many_Staff_Shop_Open_or_Dvlp = obj.getString(ProjectVariables.How_Many_Staff_Shop_Open_or_Dvlp);
            getPrayer = obj.getString(ProjectVariables.Prayer);
            getCompounding_or_Late_Coming = obj.getString(ProjectVariables.Compounding_or_Late_Coming);
            getTiffin_Box = obj.getString(ProjectVariables.Tiffin_Box);
            getStaff_Low_Sale_Remainder = obj.getString(ProjectVariables.Staff_Low_Sale_Remainder);
            getGate_Pass_with_Receipt_Number = obj.getString(ProjectVariables.Gate_Pass_with_Receipt_Number);
            getCancel_Bills_Sign_with_Photos = obj.getString(ProjectVariables.Cancel_Bills_Sign_with_Photos);
            getAlterness_Check_Meterage_SS = obj.getString(ProjectVariables.Alterness_Check_Meterage_SS);
            getAlterness_Check_inward_qty = obj.getString(ProjectVariables.Alterness_Check_inward_qty);
            getAlterness_Check_secu_Alert = obj.getString(ProjectVariables.Alterness_Check_secu_Alert);
            getMaintaince_Sheet = obj.getString(ProjectVariables.Maintaince_Sheet);
            getNight_Watchman_Call_Alert = obj.getString(ProjectVariables.Night_Watchman_Call_Alert);
            getPhCall_Shop_Open_or_Dvlp = obj.getString(ProjectVariables.PhCall_Shop_Open_or_Dvlp);
            getDB_and_SB_Entry = obj.getString(ProjectVariables.DB_and_SB_Entry);
            getNoof_Parcel_or_LRs = obj.getString(ProjectVariables.Noof_Parcel_or_LRs);
            getVisiting_with_report = obj.getString(ProjectVariables.Visiting_with_report);
            getEye_Hospital_OP_nd_Surgeries_OP = obj.getString(ProjectVariables.Eye_Hospital_OP_nd_Surgeries_OP);
            getEye_Hospital_OP_nd_Surgeries_Surgery_Free = obj.getString(ProjectVariables.Eye_Hospital_OP_nd_Surgeries_Surgery_Free);
            getEye_Hospital_OP_nd_Surgeries_Pay = obj.getString(ProjectVariables.Eye_Hospital_OP_nd_Surgeries_Pay);
            getBlood_Bank_Stock = obj.getString(ProjectVariables.Blood_Bank_Stock);
            getDental_Hospital = obj.getString(ProjectVariables.Dental_Hospital);
            getRoles = obj.getString(ProjectVariables.Roles);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setJSONValues() {
        try {
            mTextSale.setText(getSale);
            mTextChecking_Counter_Amount_Pcs.setText(getChecking_Counter_Amount_Pcs);
            mTextUnpaid.setText(getUnpaid);
            mTextRg.setText(getRg);
            mTextAxis.setText(getAxis);
            mTextHdfc.setText(getHdfc);
            mTextCash_On_Hand.setText(getCash_On_Hand);
            mTextOpening_Cash.setText(getOpening_Cash);
            mTextBank_Deposit.setText(getBank_Deposit);
            mTextCash_Short.setText(getCash_Short);
            mTextSynd_Odh.setText(getSynd_Odh);
            // mTextUnpaid_Parcels_Amount_Type.setText(getUnpaid_Parcels_Amount_Type);
            mTextUnpaid_Parcels_Amount.setText(getUnpaid_Parcels_Amount);
            mTextNight_Cash_or_Opening_Cash.setText(getNight_Cash_or_Opening_Cash);
            mTextDaily_Units_Power_or_Fuel.setText(getDaily_Units_Power_or_Fuel);
            mTextDaily_Extra_Staff.setText(getDaily_Extra_Staff);
            mTextWeek_Off_Holiday.setText(getWeek_Off_Holiday);
            mTextDaily_Absent_Staff.setText(getDaily_Absent_Staff);
            mTextDaily_Present_or_Tota.setText(getDaily_Present_or_Tota);
            mTextTea_x_Present_Staff_Custo.setText(getTea_x_Present_Staff_Custo);
            mTextSpl_Less.setText(getSpl_Less);
            mTextDaily_Water_Jars.setText(getDaily_Water_Jars);
            mTextPlastic_Covers_or_Jute_Bags_Amt.setText(getPlastic_Covers_or_Jute_Bags_Amt);
            mTextHow_Many_Staff_Shop_Open_or_Dvlp.setText(getHow_Many_Staff_Shop_Open_or_Dvlp);
            mTextPrayer.setText(getPrayer);
            mTextCompounding_or_Late_Coming.setText(getCompounding_or_Late_Coming);
            mTextTiffin_Box.setText(getTiffin_Box);
            mTextStaff_Low_Sale_Remainder.setText(getStaff_Low_Sale_Remainder);
            mTextGate_Pass_with_Receipt_Number.setText(getGate_Pass_with_Receipt_Number);
            mTextCancel_Bills_Sign_with_Photos.setText(getCancel_Bills_Sign_with_Photos);
            mTextAlterness_Check_Meterage_SS.setText(getAlterness_Check_Meterage_SS);
            mTextAlterness_Check_inward_qty.setText(getAlterness_Check_inward_qty);
            mTextAlterness_Check_secu_Alert.setText(getAlterness_Check_secu_Alert);
            mTextMaintaince_Sheet.setText(getMaintaince_Sheet);
            mTextNight_Watchman_Call_Alert.setText(getNight_Watchman_Call_Alert);
            mTextPhCall_Shop_Open_or_Dvlp.setText(getPhCall_Shop_Open_or_Dvlp);
            mTextDB_and_SB_Entry.setText(getDB_and_SB_Entry);
            mTextNoof_Parcel_or_LRs.setText(getNoof_Parcel_or_LRs);
            mTextVisiting_with_report.setText(getVisiting_with_report);
            mTextEye_Hospital_OP_nd_Surgeries_OP.setText(getEye_Hospital_OP_nd_Surgeries_OP);
            mTextEye_Hospital_OP_nd_Surgeries_Surgery_Free.setText(getEye_Hospital_OP_nd_Surgeries_Surgery_Free);
            mTextEye_Hospital_OP_nd_Surgeries_Pay.setText(getEye_Hospital_OP_nd_Surgeries_Pay);
            mTextBlood_Bank_Stock.setText(getBlood_Bank_Stock);
            mTextDental_Hospital.setText(getDental_Hospital);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


