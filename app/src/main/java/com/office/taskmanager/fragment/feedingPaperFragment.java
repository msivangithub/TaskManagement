package com.office.taskmanager.fragment;


import android.content.Context;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
public class feedingPaperFragment extends Fragment implements RestfulListener {
    public static final String TASK_HEADING = "task_heading";

    /*[{"RoId":"4","RoleName":"Admin"},
    {"RoId":"5","RoleName":"Manger"},
    {"RoId":"3","RoleName":"User"},
    {"RoId":"6","RoleName":"Purchase Incharge"}]*/

    private ArrayList<TaskBranches> branches;
    String DressMaterial = "No", HalfSarees = "No", FreeSize_SS = "No", Kurthies = "No", Tops, ShortTops = "No", JeansL = "No", S_SGirls_32_40 = "No", Western_32_36 = "No", TopsG_32_40 = "No", JeansG_32_40 = "No", LeggingG_32_40 = "No",
            HalfSareesG = "No", Frocks_16_30 = "No", LeggingG_16_30 = "No", Western_16_30 = "No", JeansG_16_30 = "No", TopsG_16_30 = "No", F_Nightie = "No", Nighties_L = "NO",
            NightSuit, N_PL = "No", N_PBoys = "No", Bra = "No", Panties = "No", K_BabyBed = "No", K_BabyBlanket = "No", K_BabyCarryBag = "No", K_BabyTowel = "No", K_BabySocks_Glous = "No", Shorts_3 = "No", Banians = "No", Underwear = "No", Towels_HL = "No",
            Towels_Turkish = "No", Door_Curtain = "No", Blankets = "No", Purses = "No", Bed_Sheets = "No", Chaddars = "No", Jeans = "No", Cotton_Pants = "No", Shirts_Cargo = "No", Formal_Shirts = "No", T_Shirts = "No", Branded_Jeans = "No",
            Branded_Shirts = "No", KP_Partywear_Suits = "No", Burmadas = "No", Shirts_Panamerica_Scapes = "No";
    boolean isChecked = true;
    CheckBox mDressMaterial, mHalfSarees, mFreeSize_SS, mKurthies, mTops, mShortTops, mJeansL, mS_SGirls_32_40, mWestern_32_36, mTopsG_32_40, mJeansG_32_40, mLeggingG_32_40, mHalfSareesG, mFrocks_16_30, mLeggingG_16_30,
            mWestern_16_30, mJeansG_16_30, mTopsG_16_30, mF_Nightie, mNighties_L, mNightSuit, mN_PL, mN_PBoys, mBra, mPanties, mK_BabyBed, mK_BabyBlanket, mK_BabyCarryBag, mK_BabyTowel, mK_BabySocks_Glous, mShorts_3, mBanians, mUnderwear, mTowels_HL,
            mTowels_Turkish, mDoor_Curtain, mBlankets, mPurses, mBed_Sheets, mChaddars, mJeans, mCotton_Pants, mShirts_Cargo, mFormal_Shirts, mT_Shirts, mBranded_Jeans, mBranded_Shirts, mKP_Partywear_Suits, mBurmadas, mShirts_Panamerica_Scapes;

    TextView mTextDressMaterial, mTextHalfSarees, mTextFreeSize_SS, mTextKurthies, mTextTops, mTextShortTops, mTextJeansL, mTextS_SGirls_32_40, mTextWestern_32_36, mTextTopsG_32_40, mTextJeansG_32_40, mTextLeggingG_32_40, mTextHalfSareesG, mTextFrocks_16_30,
            mTextLeggingG_16_30, mTextWestern_16_30, mTextJeansG_16_30, mTextTopsG_16_30, mTextF_Nightie, mTextNighties_L, mTextNightSuit, mTextN_PL, mTextN_PBoys, mTextBra, mTextPanties, mTextK_BabyBed, mTextK_BabyBlanket,
            mTextK_BabyCarryBag, mTextK_BabyTowel, mTextK_BabySocks_Glous, mTextShorts_3, mTextBanians, mTextUnderwear, mTextTowels_HL, mTextTowels_Turkish, mTextDoor_Curtain, mTextBlankets, mTextPurses, mTextBed_Sheets, mTextChaddars, mTextJeans,
            mTextCotton_Pants, mTextShirts_Cargo, mTextFormal_Shirts, mTextT_Shirts, mTextBranded_Jeans, mTextBranded_Shirts, mTextKP_Partywear_Suits, mTextBurmadas, mTextShirts_Panamerica_Scapes;

    String TextDressMaterial = null, TextHalfSarees = null, TextFreeSize_SS = null, TextKurthies = null, TextTops = null, TextShortTops = null, TextJeansL = null, TextS_SGirls_32_40 = null, TextWestern_32_36 = null, TextTopsG_32_40 = null, TextJeansG_32_40 = null,
            TextLeggingG_32_40 = null, TextHalfSareesG = null, TextFrocks_16_30 = null, TextLeggingG_16_30 = null, TextWestern_16_30 = null, TextJeansG_16_30 = null, TextTopsG_16_30 = null, TextF_Nightie = null, TextNighties_L = null, TextNightSuit = null,
            TextN_PL = null, TextN_PBoys = null, TextBra = null, TextPanties = null, TextK_BabyBed = null, TextK_BabyBlanket = null, TextK_BabyCarryBag = null, TextK_BabyTowel = null, TextK_BabySocks_Glous = null, TextShorts_3 = null, TextBanians = null,
            TextUnderwear = null, TextTowels_HL = null, TextTowels_Turkish = null, TextDoor_Curtain = null, TextBlankets = null, TextPurses = null, TextBed_Sheets = null, TextChaddars = null, TextJeans = null, TextCotton_Pants = null, TextShirts_Cargo = null,
            TextFormal_Shirts = null, TextT_Shirts = null, TextBranded_Jeans = null, TextBranded_Shirts = null, TextKP_Partywear_Suits = null, TextBurmadas = null, TextShirts_Panamerica_Scapes = null;

    String uid, userroles, branchID;
    View view;
    Context context;
    RestfulListener listener;
    JSONObject jsonObject;
    JSONArray jsonArray;
    MenuItem CheckUnCheck, upload, allChk;
    String companyID = "";
    String cityname = "";
    int branchpos = 0;
    private Spinner selectBranches;

    public static feedingPaperFragment newInstance() {

        Bundle args = new Bundle();
        feedingPaperFragment fragment = new feedingPaperFragment();
       /* args.putString(TASK_HEADING,taskheading);*/
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feeding_paper, container, false);
        listener = this;
        context = getActivity();

        branches = new ArrayList<>();
        setHasOptionsMenu(true);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Feeding Paper</font>"));
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
                AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 151, ProjectVariables.BRANCHES, listener, null, "");
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
                getFeedingPaperReport();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getFeedingPaperReport() {
        jsonObject = new JSONObject();
        if (userroles.equalsIgnoreCase("4") || userroles.equalsIgnoreCase("1")) {
            try {
                jsonObject.accumulate("BranchId", companyID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AsynHttpPost143 post143 = new AsynHttpPost143(getActivity(), 1, 150, ProjectVariables.GETFEEDINGPAPER, listener, jsonObject, "", true);
                post143.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void intialVariables() {

        selectBranches = (Spinner) view.findViewById(R.id.id_Branch);
        mTextDressMaterial = (TextView) view.findViewById(R.id.id_TextDressMaterial);
        mTextHalfSarees = (TextView) view.findViewById(R.id.id_TextHalfSarees);
        mTextFreeSize_SS = (TextView) view.findViewById(R.id.id_TextFreeSize_SS);
        mTextKurthies = (TextView) view.findViewById(R.id.id_TextKurthies);
        mTextTops = (TextView) view.findViewById(R.id.id_TextTops);
        mTextShortTops = (TextView) view.findViewById(R.id.id_TextShortTops);
        mTextJeansL = (TextView) view.findViewById(R.id.id_TextJeansL);
        mTextS_SGirls_32_40 = (TextView) view.findViewById(R.id.id_TextS_SGirls_32_40);
        mTextWestern_32_36 = (TextView) view.findViewById(R.id.id_TextWestern_32_36);
        mTextTopsG_32_40 = (TextView) view.findViewById(R.id.id_TextTopsG_32_40);
        mTextJeansG_32_40 = (TextView) view.findViewById(R.id.id_TextJeansG_32_40);
        mTextLeggingG_32_40 = (TextView) view.findViewById(R.id.id_TextLeggingG_32_40);
        mTextHalfSareesG = (TextView) view.findViewById(R.id.id_TextHalfSareesG);
        mTextFrocks_16_30 = (TextView) view.findViewById(R.id.id_TextFrocks_16_30);
        mTextLeggingG_16_30 = (TextView) view.findViewById(R.id.id_TextLeggingG_16_30);
        mTextWestern_16_30 = (TextView) view.findViewById(R.id.id_TextWestern_16_30);
        mTextJeansG_16_30 = (TextView) view.findViewById(R.id.id_TextJeansG_16_30);
        mTextTopsG_16_30 = (TextView) view.findViewById(R.id.id_TextTopsG_16_30);
        mTextF_Nightie = (TextView) view.findViewById(R.id.id_TextF_Nightie);
        mTextNighties_L = (TextView) view.findViewById(R.id.id_TextNighties_L);
        mTextNightSuit = (TextView) view.findViewById(R.id.id_TextNightSuit);
        mTextN_PL = (TextView) view.findViewById(R.id.id_TextN_PL);
        mTextN_PBoys = (TextView) view.findViewById(R.id.id_TextN_PBoys);
        mTextBra = (TextView) view.findViewById(R.id.id_TextBra);
        mTextPanties = (TextView) view.findViewById(R.id.id_TextPanties);
        mTextK_BabyBed = (TextView) view.findViewById(R.id.id_TextK_BabyBed);
        mTextK_BabyBlanket = (TextView) view.findViewById(R.id.id_TextK_BabyBlanket);
        mTextK_BabyCarryBag = (TextView) view.findViewById(R.id.id_TextK_BabyCarryBag);
        mTextK_BabyTowel = (TextView) view.findViewById(R.id.id_TextK_BabyTowel);
        mTextK_BabySocks_Glous = (TextView) view.findViewById(R.id.id_TextK_BabySocks_Glous);
        mTextShorts_3 = (TextView) view.findViewById(R.id.id_TextShorts_3);

        mTextBanians = (TextView) view.findViewById(R.id.id_TextBanians);
        mTextUnderwear = (TextView) view.findViewById(R.id.id_TextUnderwear);
        mTextTowels_HL = (TextView) view.findViewById(R.id.id_TextTowels_HL);
        mTextTowels_Turkish = (TextView) view.findViewById(R.id.id_TextTowels_Turkish);
        mTextDoor_Curtain = (TextView) view.findViewById(R.id.id_TextDoor_Curtain);
        mTextBlankets = (TextView) view.findViewById(R.id.id_TextBlankets);
        mTextPurses = (TextView) view.findViewById(R.id.id_TextPurses);
        mTextBed_Sheets = (TextView) view.findViewById(R.id.id_TextBed_Sheets);
        mTextChaddars = (TextView) view.findViewById(R.id.id_TextChaddars);
        mTextJeans = (TextView) view.findViewById(R.id.id_TextJeans);
        mTextCotton_Pants = (TextView) view.findViewById(R.id.id_TextCotton_Pants);
        mTextShirts_Cargo = (TextView) view.findViewById(R.id.id_TextShirts_Cargo);
        mTextFormal_Shirts = (TextView) view.findViewById(R.id.id_TextFormal_Shirts);
        mTextT_Shirts = (TextView) view.findViewById(R.id.id_TextT_Shirts);
        mTextBranded_Jeans = (TextView) view.findViewById(R.id.id_TextBranded_Jeans);
        mTextBranded_Shirts = (TextView) view.findViewById(R.id.id_TextBranded_Shirts);
        mTextKP_Partywear_Suits = (TextView) view.findViewById(R.id.id_TextKP_Partywear_Suits);
        mTextBurmadas = (TextView) view.findViewById(R.id.id_TextBurmadas);
        mTextShirts_Panamerica_Scapes = (TextView) view.findViewById(R.id.id_TextShirts_Panamerica_Scapes);


        mDressMaterial = (CheckBox) view.findViewById(R.id.id_DressMaterial);
        mHalfSarees = (CheckBox) view.findViewById(R.id.id_mHalfSarees);
        mFreeSize_SS = (CheckBox) view.findViewById(R.id.id_mFreeSize_SS);
        mKurthies = (CheckBox) view.findViewById(R.id.id_mKurthies);
        mTops = (CheckBox) view.findViewById(R.id.id_mTops);
        mShortTops = (CheckBox) view.findViewById(R.id.id_mShortTops);
        mJeansL = (CheckBox) view.findViewById(R.id.id_mJeansL);
        mS_SGirls_32_40 = (CheckBox) view.findViewById(R.id.id_mS_SGirls_32_40);
        mWestern_32_36 = (CheckBox) view.findViewById(R.id.id_mWestern_32_36);
        mTopsG_32_40 = (CheckBox) view.findViewById(R.id.id_mTopsG_32_40);
        mJeansG_32_40 = (CheckBox) view.findViewById(R.id.id_mJeansG_32_40);
        mLeggingG_32_40 = (CheckBox) view.findViewById(R.id.id_mLeggingG_32_40);
        mHalfSareesG = (CheckBox) view.findViewById(R.id.id_mHalfSareesG);
        mFrocks_16_30 = (CheckBox) view.findViewById(R.id.id_mFrocks_16_30);
        mLeggingG_16_30 = (CheckBox) view.findViewById(R.id.id_mLeggingG_16_30);
        mWestern_16_30 = (CheckBox) view.findViewById(R.id.id_mWestern_16_30);
        mJeansG_16_30 = (CheckBox) view.findViewById(R.id.id_mJeansG_16_30);
        mTopsG_16_30 = (CheckBox) view.findViewById(R.id.id_mTopsG_16_30);
        mF_Nightie = (CheckBox) view.findViewById(R.id.id_mF_Nightie);
        mNighties_L = (CheckBox) view.findViewById(R.id.id_mNighties_L);
        mNightSuit = (CheckBox) view.findViewById(R.id.id_mNightSuit);
        mN_PL = (CheckBox) view.findViewById(R.id.id_mN_PL);
        mN_PBoys = (CheckBox) view.findViewById(R.id.id_mN_PBoys);
        mBra = (CheckBox) view.findViewById(R.id.id_mBra);
        mPanties = (CheckBox) view.findViewById(R.id.id_mPanties);
        mK_BabyBed = (CheckBox) view.findViewById(R.id.id_mK_BabyBed);
        mK_BabyBlanket = (CheckBox) view.findViewById(R.id.id_mK_BabyBlanket);
        mK_BabyCarryBag = (CheckBox) view.findViewById(R.id.id_mK_BabyCarryBag);
        mK_BabyTowel = (CheckBox) view.findViewById(R.id.id_mK_BabyTowel);
        mK_BabySocks_Glous = (CheckBox) view.findViewById(R.id.id_mK_BabySocks_Glous);
        mShorts_3 = (CheckBox) view.findViewById(R.id.id_mShorts_3);

        mBanians = (CheckBox) view.findViewById(R.id.id_mBanians);
        mUnderwear = (CheckBox) view.findViewById(R.id.id_mUnderwear);
        mTowels_HL = (CheckBox) view.findViewById(R.id.id_mTowels_HL);
        mTowels_Turkish = (CheckBox) view.findViewById(R.id.id_mTowels_Turkish);
        mDoor_Curtain = (CheckBox) view.findViewById(R.id.id_mDoor_Curtain);
        mBlankets = (CheckBox) view.findViewById(R.id.id_mBlankets);
        mPurses = (CheckBox) view.findViewById(R.id.id_mPurses);
        mBed_Sheets = (CheckBox) view.findViewById(R.id.id_mBed_Sheets);
        mChaddars = (CheckBox) view.findViewById(R.id.id_mChaddars);
        mJeans = (CheckBox) view.findViewById(R.id.id_mJeans);
        mCotton_Pants = (CheckBox) view.findViewById(R.id.id_mCotton_Pants);
        mShirts_Cargo = (CheckBox) view.findViewById(R.id.id_mShirts_Cargo);
        mFormal_Shirts = (CheckBox) view.findViewById(R.id.id_mFormal_Shirts);
        mT_Shirts = (CheckBox) view.findViewById(R.id.id_mT_Shirts);
        mBranded_Jeans = (CheckBox) view.findViewById(R.id.id_mBranded_Jeans);
        mBranded_Shirts = (CheckBox) view.findViewById(R.id.id_mBranded_Shirts);
        mKP_Partywear_Suits = (CheckBox) view.findViewById(R.id.id_mKP_Partywear_Suits);
        mBurmadas = (CheckBox) view.findViewById(R.id.id_mBurmadas);
        mShirts_Panamerica_Scapes = (CheckBox) view.findViewById(R.id.id_mShirts_Panamerica_Scapes);

        CheckBoxOnClick();
        alternatDisplay();
    }

    private void alternatDisplay() {
        try {
            if (userroles.equalsIgnoreCase("4") || userroles.equalsIgnoreCase("1")) {
                mTextDressMaterial.setVisibility(View.VISIBLE);
                mTextHalfSarees.setVisibility(View.VISIBLE);
                mTextFreeSize_SS.setVisibility(View.VISIBLE);
                mTextKurthies.setVisibility(View.VISIBLE);
                mTextTops.setVisibility(View.VISIBLE);
                mTextShortTops.setVisibility(View.VISIBLE);
                mTextJeansL.setVisibility(View.VISIBLE);
                mTextS_SGirls_32_40.setVisibility(View.VISIBLE);
                mTextWestern_32_36.setVisibility(View.VISIBLE);
                mTextTopsG_32_40.setVisibility(View.VISIBLE);
                mTextJeansG_32_40.setVisibility(View.VISIBLE);
                mTextLeggingG_32_40.setVisibility(View.VISIBLE);
                mTextHalfSareesG.setVisibility(View.VISIBLE);
                mTextFrocks_16_30.setVisibility(View.VISIBLE);
                mTextLeggingG_16_30.setVisibility(View.VISIBLE);
                mTextWestern_16_30.setVisibility(View.VISIBLE);
                mTextJeansG_16_30.setVisibility(View.VISIBLE);
                mTextTopsG_16_30.setVisibility(View.VISIBLE);
                mTextF_Nightie.setVisibility(View.VISIBLE);
                mTextNighties_L.setVisibility(View.VISIBLE);
                mTextNightSuit.setVisibility(View.VISIBLE);
                mTextN_PL.setVisibility(View.VISIBLE);
                mTextN_PBoys.setVisibility(View.VISIBLE);
                mTextBra.setVisibility(View.VISIBLE);
                mTextPanties.setVisibility(View.VISIBLE);
                mTextK_BabyBed.setVisibility(View.VISIBLE);
                mTextK_BabyBlanket.setVisibility(View.VISIBLE);
                mTextK_BabyCarryBag.setVisibility(View.VISIBLE);
                mTextK_BabyTowel.setVisibility(View.VISIBLE);
                mTextK_BabySocks_Glous.setVisibility(View.VISIBLE);
                mTextShorts_3.setVisibility(View.VISIBLE);
                mTextBanians.setVisibility(View.VISIBLE);
                mTextUnderwear.setVisibility(View.VISIBLE);
                mTextTowels_HL.setVisibility(View.VISIBLE);
                mTextTowels_Turkish.setVisibility(View.VISIBLE);
                mTextDoor_Curtain.setVisibility(View.VISIBLE);
                mTextBlankets.setVisibility(View.VISIBLE);
                mTextPurses.setVisibility(View.VISIBLE);
                mTextBed_Sheets.setVisibility(View.VISIBLE);
                mTextChaddars.setVisibility(View.VISIBLE);
                mTextJeans.setVisibility(View.VISIBLE);
                mTextCotton_Pants.setVisibility(View.VISIBLE);
                mTextShirts_Cargo.setVisibility(View.VISIBLE);
                mTextFormal_Shirts.setVisibility(View.VISIBLE);
                mTextT_Shirts.setVisibility(View.VISIBLE);
                mTextBranded_Jeans.setVisibility(View.VISIBLE);
                mTextBranded_Shirts.setVisibility(View.VISIBLE);
                mTextKP_Partywear_Suits.setVisibility(View.VISIBLE);
                mTextBurmadas.setVisibility(View.VISIBLE);
                mTextShirts_Panamerica_Scapes.setVisibility(View.VISIBLE);

                mDressMaterial.setVisibility(View.GONE);
                mHalfSarees.setVisibility(View.GONE);
                mFreeSize_SS.setVisibility(View.GONE);
                mKurthies.setVisibility(View.GONE);
                mTops.setVisibility(View.GONE);
                mShortTops.setVisibility(View.GONE);
                mJeansL.setVisibility(View.GONE);
                mS_SGirls_32_40.setVisibility(View.GONE);
                mWestern_32_36.setVisibility(View.GONE);
                mTopsG_32_40.setVisibility(View.GONE);
                mJeansG_32_40.setVisibility(View.GONE);
                mLeggingG_32_40.setVisibility(View.GONE);
                mHalfSareesG.setVisibility(View.GONE);
                mFrocks_16_30.setVisibility(View.GONE);
                mLeggingG_16_30.setVisibility(View.GONE);
                mWestern_16_30.setVisibility(View.GONE);
                mJeansG_16_30.setVisibility(View.GONE);
                mTopsG_16_30.setVisibility(View.GONE);
                mF_Nightie.setVisibility(View.GONE);
                mNighties_L.setVisibility(View.GONE);
                mNightSuit.setVisibility(View.GONE);
                mN_PL.setVisibility(View.GONE);
                mN_PBoys.setVisibility(View.GONE);
                mBra.setVisibility(View.GONE);
                mPanties.setVisibility(View.GONE);
                mK_BabyBed.setVisibility(View.GONE);
                mK_BabyBlanket.setVisibility(View.GONE);
                mK_BabyCarryBag.setVisibility(View.GONE);
                mK_BabyTowel.setVisibility(View.GONE);
                mK_BabySocks_Glous.setVisibility(View.GONE);
                mShorts_3.setVisibility(View.GONE);
                mBanians.setVisibility(View.GONE);
                mUnderwear.setVisibility(View.GONE);
                mTowels_HL.setVisibility(View.GONE);
                mTowels_Turkish.setVisibility(View.GONE);
                mDoor_Curtain.setVisibility(View.GONE);
                mBlankets.setVisibility(View.GONE);
                mPurses.setVisibility(View.GONE);
                mBed_Sheets.setVisibility(View.GONE);
                mChaddars.setVisibility(View.GONE);
                mJeans.setVisibility(View.GONE);
                mCotton_Pants.setVisibility(View.GONE);
                mShirts_Cargo.setVisibility(View.GONE);
                mFormal_Shirts.setVisibility(View.GONE);
                mT_Shirts.setVisibility(View.GONE);
                mBranded_Jeans.setVisibility(View.GONE);
                mBranded_Shirts.setVisibility(View.GONE);
                mKP_Partywear_Suits.setVisibility(View.GONE);
                mBurmadas.setVisibility(View.GONE);
                mShirts_Panamerica_Scapes.setVisibility(View.GONE);
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CheckBoxOnClick() {

        mDressMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    DressMaterial = "yes";
                } else {
                    DressMaterial = "No";
                }
            }
        });
        mHalfSarees.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    HalfSarees = "yes";
                } else {
                    HalfSarees = "No";
                }
            }
        });
        mFreeSize_SS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    FreeSize_SS = "yes";
                } else {
                    FreeSize_SS = "No";
                }
            }
        });
        mKurthies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Kurthies = "yes";
                } else {
                    Kurthies = "No";
                }
            }
        });
        mTops.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Tops = "yes";
                } else {
                    Tops = "No";
                }
            }
        });
        mShortTops.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    ShortTops = "yes";
                } else {
                    ShortTops = "No";
                }
            }
        });
        mJeansL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    JeansL = "yes";
                } else {
                    JeansL = "No";
                }
            }
        });
        mS_SGirls_32_40.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    S_SGirls_32_40 = "yes";
                } else {
                    S_SGirls_32_40 = "No";
                }
            }
        });
        mWestern_32_36.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Western_32_36 = "yes";
                } else {
                    Western_32_36 = "No";
                }
            }
        });
        mTopsG_32_40.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    TopsG_32_40 = "yes";
                } else {
                    TopsG_32_40 = "No";
                }
            }
        });
        mJeansG_32_40.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    JeansG_32_40 = "yes";
                } else {
                    JeansG_32_40 = "No";
                }
            }
        });
        mLeggingG_32_40.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    LeggingG_32_40 = "yes";
                } else {
                    LeggingG_32_40 = "No";
                }
            }
        });
        mHalfSareesG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    HalfSareesG = "yes";
                } else {
                    HalfSareesG = "No";
                }
            }
        });
        mFrocks_16_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Frocks_16_30 = "yes";
                } else {
                    Frocks_16_30 = "No";
                }
            }
        });
        mLeggingG_16_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    LeggingG_16_30 = "yes";
                } else {
                    LeggingG_16_30 = "No";
                }
            }
        });
        mWestern_16_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Western_16_30 = "yes";
                } else {
                    Western_16_30 = "No";
                }
            }
        });
        mJeansG_16_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    JeansG_16_30 = "yes";
                } else {
                    JeansG_16_30 = "No";
                }
            }
        });
        mTopsG_16_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    TopsG_16_30 = "yes";
                } else {
                    TopsG_16_30 = "No";
                }
            }
        });
        mF_Nightie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    F_Nightie = "yes";
                } else {
                    F_Nightie = "No";
                }
            }
        });
        mNighties_L.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Nighties_L = "yes";
                } else {
                    Nighties_L = "No";
                }
            }
        });
        mNightSuit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    NightSuit = "yes";
                } else {
                    NightSuit = "No";
                }
            }
        });
        mN_PL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    N_PL = "yes";
                } else {
                    N_PL = "No";
                }
            }
        });
        mN_PBoys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    N_PBoys = "yes";
                } else {
                    N_PBoys = "No";
                }
            }
        });
        mBra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Bra = "yes";
                } else {
                    Bra = "No";
                }
            }
        });
        mPanties.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Panties = "yes";
                } else {
                    Panties = "No";
                }
            }
        });
        mK_BabyBed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    K_BabyBed = "yes";
                } else {
                    K_BabyBed = "No";
                }
            }
        });
        mK_BabyBlanket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    K_BabyBlanket = "yes";
                } else {
                    K_BabyBlanket = "No";
                }
            }
        });
        mK_BabyCarryBag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    K_BabyCarryBag = "yes";
                } else {
                    K_BabyCarryBag = "No";
                }
            }
        });
        mK_BabyTowel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    K_BabyTowel = "yes";
                } else {
                    K_BabyTowel = "No";
                }
            }
        });
        mK_BabySocks_Glous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    K_BabySocks_Glous = "yes";
                } else {
                    K_BabySocks_Glous = "No";
                }
            }
        });
        mShorts_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Shorts_3 = "yes";
                } else {
                    Shorts_3 = "No";
                }
            }
        });
        mBanians.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Banians = "yes";
                } else {
                    Banians = "No";
                }
            }
        });
        mUnderwear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Underwear = "yes";
                } else {
                    Underwear = "No";
                }
            }
        });
        mTowels_HL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Towels_HL = "yes";
                } else {
                    Towels_HL = "No";
                }
            }
        });
        mTowels_Turkish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Towels_Turkish = "yes";
                } else {
                    Towels_Turkish = "No";
                }
            }
        });
        mDoor_Curtain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Door_Curtain = "yes";
                } else {
                    Door_Curtain = "No";
                }
            }
        });
        mBlankets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Blankets = "yes";
                } else {
                    Blankets = "No";
                }
            }
        });
        mPurses.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Purses = "yes";
                } else {
                    Purses = "No";
                }
            }
        });
        mBed_Sheets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Bed_Sheets = "yes";
                } else {
                    Bed_Sheets = "No";
                }
            }
        });
        mChaddars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Chaddars = "yes";
                } else {
                    Chaddars = "No";
                }
            }
        });
        mJeans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Jeans = "yes";
                } else {
                    Jeans = "No";
                }
            }
        });
        mCotton_Pants.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Cotton_Pants = "yes";
                } else {
                    Cotton_Pants = "No";
                }
            }
        });
        mShirts_Cargo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Shirts_Cargo = "yes";
                } else {
                    Shirts_Cargo = "No";
                }
            }
        });
        mFormal_Shirts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Formal_Shirts = "yes";
                } else {
                    Formal_Shirts = "No";
                }
            }
        });
        mT_Shirts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    T_Shirts = "yes";
                } else {
                    T_Shirts = "No";
                }
            }
        });
        mBranded_Jeans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Branded_Jeans = "yes";
                } else {
                    Branded_Jeans = "No";
                }
            }
        });
        mBranded_Shirts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Branded_Shirts = "yes";
                } else {
                    Branded_Shirts = "No";
                }
            }
        });
        mKP_Partywear_Suits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    KP_Partywear_Suits = "yes";
                } else {
                    KP_Partywear_Suits = "No";
                }
            }
        });
        mBurmadas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Burmadas = "yes";
                } else {
                    Burmadas = "No";
                }
            }
        });
        mShirts_Panamerica_Scapes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Shirts_Panamerica_Scapes = "yes";
                } else {
                    Shirts_Panamerica_Scapes = "No";
                }
            }
        });

    }

    void Togle_Selection(Boolean status) {
        try {
            isChecked = status;
            mDressMaterial.setChecked(isChecked);
            mHalfSarees.setChecked(isChecked);
            mFreeSize_SS.setChecked(isChecked);
            mKurthies.setChecked(isChecked);
            mTops.setChecked(isChecked);
            mShortTops.setChecked(isChecked);
            mJeansL.setChecked(isChecked);
            mS_SGirls_32_40.setChecked(isChecked);
            mWestern_32_36.setChecked(isChecked);
            mTopsG_32_40.setChecked(isChecked);
            mJeansG_32_40.setChecked(isChecked);
            mLeggingG_32_40.setChecked(isChecked);
            mHalfSareesG.setChecked(isChecked);
            mFrocks_16_30.setChecked(isChecked);
            mLeggingG_16_30.setChecked(isChecked);
            mWestern_16_30.setChecked(isChecked);
            mJeansG_16_30.setChecked(isChecked);
            mTopsG_16_30.setChecked(isChecked);
            mF_Nightie.setChecked(isChecked);
            mNighties_L.setChecked(isChecked);
            mNightSuit.setChecked(isChecked);
            mN_PL.setChecked(isChecked);
            mN_PBoys.setChecked(isChecked);
            mBra.setChecked(isChecked);
            mPanties.setChecked(isChecked);
            mK_BabyBed.setChecked(isChecked);
            mK_BabyBlanket.setChecked(isChecked);
            mK_BabyCarryBag.setChecked(isChecked);
            mK_BabyTowel.setChecked(isChecked);
            mK_BabySocks_Glous.setChecked(isChecked);
            mShorts_3.setChecked(isChecked);
            mBanians.setChecked(isChecked);
            mUnderwear.setChecked(isChecked);
            mTowels_HL.setChecked(isChecked);
            mTowels_Turkish.setChecked(isChecked);
            mDoor_Curtain.setChecked(isChecked);
            mBlankets.setChecked(isChecked);
            mPurses.setChecked(isChecked);
            mBed_Sheets.setChecked(isChecked);
            mChaddars.setChecked(isChecked);
            mJeans.setChecked(isChecked);
            mCotton_Pants.setChecked(isChecked);
            mShirts_Cargo.setChecked(isChecked);
            mFormal_Shirts.setChecked(isChecked);
            mT_Shirts.setChecked(isChecked);
            mBranded_Jeans.setChecked(isChecked);
            mBranded_Shirts.setChecked(isChecked);
            mKP_Partywear_Suits.setChecked(isChecked);
            mBurmadas.setChecked(isChecked);
            mShirts_Panamerica_Scapes.setChecked(isChecked);
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


    Boolean CheckBox_validation() {
        Boolean result = false;
        try {
            if (!result) {
                result = mDressMaterial.isChecked();
            }
            if (!result) {
                result = mHalfSarees.isChecked();
            }
            if (!result) {
                result = mFreeSize_SS.isChecked();
            }
            if (!result) {
                result = mKurthies.isChecked();
            }
            if (!result) {
                result = mTops.isChecked();
            }
            if (!result) {
                result = mShortTops.isChecked();
            }
            if (!result) {
                result = mJeansL.isChecked();
            }
            if (!result) {
                result = mS_SGirls_32_40.isChecked();
            }
            if (!result) {
                result = mWestern_32_36.isChecked();
            }
            if (!result) {
                result = mTopsG_32_40.isChecked();
            }
            if (!result) {
                result = mJeansG_32_40.isChecked();
            }
            if (!result) {
                result = mLeggingG_32_40.isChecked();
            }
            if (!result) {
                result = mHalfSareesG.isChecked();
            }
            if (!result) {
                result = mFrocks_16_30.isChecked();
            }
            if (!result) {
                result = mLeggingG_16_30.isChecked();
            }
            if (!result) {
                result = mWestern_16_30.isChecked();
            }
            if (!result) {
                result = mJeansG_16_30.isChecked();
            }
            if (!result) {
                result = mTopsG_16_30.isChecked();
            }
            if (!result) {
                result = mF_Nightie.isChecked();
            }
            if (!result) {
                result = mNighties_L.isChecked();
            }
            if (!result) {
                result = mNightSuit.isChecked();
            }
            if (!result) {
                result = mN_PL.isChecked();
            }
            if (!result) {
                result = mN_PBoys.isChecked();
            }
            if (!result) {
                result = mBra.isChecked();
            }
            if (!result) {
                result = mPanties.isChecked();
            }
            if (!result) {
                result = mK_BabyBed.isChecked();
            }
            if (!result) {
                result = mJeansG_32_40.isChecked();
            }
            if (!result) {
                result = mK_BabyCarryBag.isChecked();
            }
            if (!result) {
                result = mK_BabyTowel.isChecked();
            }
            if (!result) {
                result = mK_BabySocks_Glous.isChecked();
            }
            if (!result) {
                result = mShorts_3.isChecked();
            }

            if (!result) {
                result = mBanians.isChecked();
            }
            if (!result) {
                result = mUnderwear.isChecked();
            }
            if (!result) {
                result = mTowels_HL.isChecked();
            }
            if (!result) {
                result = mTowels_Turkish.isChecked();
            }
            if (!result) {
                result = mDoor_Curtain.isChecked();
            }
            if (!result) {
                result = mBlankets.isChecked();
            }
            if (!result) {
                result = mPurses.isChecked();
            }
            if (!result) {
                result = mBed_Sheets.isChecked();
            }
            if (!result) {
                result = mChaddars.isChecked();
            }
            if (!result) {
                result = mJeans.isChecked();
            }
            if (!result) {
                result = mCotton_Pants.isChecked();
            }
            if (!result) {
                result = mShirts_Cargo.isChecked();
            }
            if (!result) {
                result = mFormal_Shirts.isChecked();
            }
            if (!result) {
                result = mT_Shirts.isChecked();
            }
            if (!result) {
                result = mBranded_Jeans.isChecked();
            }
            if (!result) {
                result = mBranded_Shirts.isChecked();
            }
            if (!result) {
                result = mKP_Partywear_Suits.isChecked();
            }
            if (!result) {
                result = mBurmadas.isChecked();
            }
            if (!result) {
                result = mShirts_Panamerica_Scapes.isChecked();
            }

            isChecked = !isChecked;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allchecked_items:
                Togle_Selection(isChecked);
                return true;
            case R.id.upload_data:
                if (CheckBox_validation()) {
                    uploadeData();
                } else {
                    Toast.makeText(getActivity(), "Please Select at list one items", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return (super.onOptionsItemSelected(item)
        );
    }

    private void uploadeData() {
        jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(ProjectVariables.BranchId, branchID);
            jsonObject.accumulate(ProjectVariables.UserId, uid);
            jsonObject.accumulate(ProjectVariables.DressMaterial, DressMaterial);
            jsonObject.accumulate(ProjectVariables.HalfSarees, HalfSarees);
            jsonObject.accumulate(ProjectVariables.FreeSize_SS, FreeSize_SS);
            jsonObject.accumulate(ProjectVariables.Kurthies, Kurthies);
            jsonObject.accumulate(ProjectVariables.Tops, Tops);
            jsonObject.accumulate(ProjectVariables.ShortTops, ShortTops);
            jsonObject.accumulate(ProjectVariables.JeansL, JeansL);
            jsonObject.accumulate(ProjectVariables.S_SGirls_32_40, S_SGirls_32_40);
            jsonObject.accumulate(ProjectVariables.Western_32_36, Western_32_36);
            jsonObject.accumulate(ProjectVariables.TopsG_32_40, TopsG_32_40);
            jsonObject.accumulate(ProjectVariables.JeansG_32_40, JeansG_32_40);
            jsonObject.accumulate(ProjectVariables.LeggingG_32_40, LeggingG_32_40);
            jsonObject.accumulate(ProjectVariables.HalfSareesG, HalfSareesG);
            jsonObject.accumulate(ProjectVariables.Frocks_16_30, Frocks_16_30);
            jsonObject.accumulate(ProjectVariables.LeggingG_16_30, LeggingG_16_30);
            jsonObject.accumulate(ProjectVariables.Western_16_30, Western_16_30);
            jsonObject.accumulate(ProjectVariables.JeansG_16_30, JeansG_16_30);
            jsonObject.accumulate(ProjectVariables.TopsG_16_30, TopsG_16_30);
            jsonObject.accumulate(ProjectVariables.F_Nighties, F_Nightie);
            jsonObject.accumulate(ProjectVariables.Nighties_L, Nighties_L);
            jsonObject.accumulate(ProjectVariables.NightSuit, NightSuit);
            jsonObject.accumulate(ProjectVariables.N_PL, N_PL);
            jsonObject.accumulate(ProjectVariables.N_PBoys, N_PBoys);
            jsonObject.accumulate(ProjectVariables.Bra, Bra);
            jsonObject.accumulate(ProjectVariables.Panties, Panties);
            jsonObject.accumulate(ProjectVariables.K_BabyBed, K_BabyBed);
            jsonObject.accumulate(ProjectVariables.K_BabyBlanket, K_BabyBlanket);
            jsonObject.accumulate(ProjectVariables.K_BabyCarryBag, K_BabyCarryBag);
            jsonObject.accumulate(ProjectVariables.K_BabyTowel, K_BabyTowel);
            jsonObject.accumulate(ProjectVariables.K_BabySocks_Glous, K_BabySocks_Glous);
            jsonObject.accumulate(ProjectVariables.Shorts_3, Shorts_3);
            jsonObject.accumulate(ProjectVariables.GFLRBanians, Banians);
            jsonObject.accumulate(ProjectVariables.GFLRUnderwear, Underwear);
            jsonObject.accumulate(ProjectVariables.GFLRTowelsHL, Towels_HL);
            jsonObject.accumulate(ProjectVariables.GFLRTowelsTurkish, Towels_Turkish);
            jsonObject.accumulate(ProjectVariables.GFLRDoorCurtain, Door_Curtain);
            jsonObject.accumulate(ProjectVariables.GFLRBlankets, Blankets);
            jsonObject.accumulate(ProjectVariables.GFLRPurses, Purses);
            jsonObject.accumulate(ProjectVariables.GFLRBedSheets, Bed_Sheets);
            jsonObject.accumulate(ProjectVariables.GFLRChaddars, Chaddars);
            jsonObject.accumulate(ProjectVariables.MGLRTJeans, Jeans);
            jsonObject.accumulate(ProjectVariables.MGLRTCottonPants, Cotton_Pants);
            jsonObject.accumulate(ProjectVariables.MGLRTShirts_Cargo, Shirts_Cargo);
            jsonObject.accumulate(ProjectVariables.MGLRTFormalShirts, Formal_Shirts);
            jsonObject.accumulate(ProjectVariables.MGLRTTShirts, T_Shirts);
            jsonObject.accumulate(ProjectVariables.MGLRTBrandedJeans, Branded_Jeans);
            jsonObject.accumulate(ProjectVariables.MGLRTBrandedShirts, Branded_Shirts);
            jsonObject.accumulate(ProjectVariables.MGLRTKP_Partywear_Suits, KP_Partywear_Suits);
            jsonObject.accumulate(ProjectVariables.MGLRTBurmadas, Burmadas);
            jsonObject.accumulate(ProjectVariables.MGLRTShirts_Panamerica_Scapes, Shirts_Panamerica_Scapes);

            jsonObject.accumulate(ProjectVariables.Role, userroles);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppUtil.isInternetAvailable(getActivity())) {
            try {
                AsynHttpPost post143 = new AsynHttpPost(getActivity(), 1, 148, ProjectVariables.FEEDINGPAPER, listener, jsonObject, "");
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
        if (rType == 148) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String result = jsonObject.getString("Result");
                    if (result.equalsIgnoreCase("inserted successfully")) {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        Togle_Selection(false);
                        Log.e("manager =", result);
                    } else {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        Togle_Selection(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rType == 150) {
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
                        setJSONValues();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rType == 151) {
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
        mTextDressMaterial.setText("");
        mTextHalfSarees.setText("");
        mTextFreeSize_SS.setText("");
        mTextKurthies.setText("");
        mTextTops.setText("");
        mTextShortTops.setText("");
        mTextJeansL.setText("");
        mTextS_SGirls_32_40.setText("");
        mTextWestern_32_36.setText("");
        mTextTopsG_32_40.setText("");
        mTextJeansG_32_40.setText("");
        mTextLeggingG_32_40.setText("");
        mTextHalfSareesG.setText("");
        mTextFrocks_16_30.setText("");
        mTextLeggingG_16_30.setText("");
        mTextWestern_16_30.setText("");
        mTextJeansG_16_30.setText("");
        mTextTopsG_16_30.setText("");
        mTextF_Nightie.setText("");
        mTextNighties_L.setText("");
        mTextNightSuit.setText("");
        mTextN_PL.setText("");
        mTextN_PBoys.setText("");
        mTextBra.setText("");
        mTextPanties.setText("");
        mTextK_BabyBed.setText("");
        mTextK_BabyBlanket.setText("");
        mTextK_BabyCarryBag.setText("");
        mTextK_BabyTowel.setText("");
        mTextK_BabySocks_Glous.setText("");
        mTextShorts_3.setText("");
        mTextBanians.setText("");
        mTextUnderwear.setText("");
        mTextTowels_HL.setText("");
        mTextTowels_Turkish.setText("");
        mTextDoor_Curtain.setText("");
        mTextBlankets.setText("");
        mTextPurses.setText("");
        mTextBed_Sheets.setText("");
        mTextChaddars.setText("");
        mTextJeans.setText("");
        mTextCotton_Pants.setText("");
        mTextShirts_Cargo.setText("");
        mTextFormal_Shirts.setText("");
        mTextT_Shirts.setText("");
        mTextBranded_Jeans.setText("");
        mTextBranded_Shirts.setText("");
        mTextKP_Partywear_Suits.setText("");
        mTextBurmadas.setText("");
        mTextShirts_Panamerica_Scapes.setText("");
    }

    private void getJSONVariables(JSONObject obj) {
        try {
            TextDressMaterial = obj.getString((ProjectVariables.DressMaterial));
            TextHalfSarees = obj.getString((ProjectVariables.HalfSarees));
            TextFreeSize_SS = obj.getString((ProjectVariables.FreeSize_SS));
            TextKurthies = obj.getString((ProjectVariables.Kurthies));
            TextTops = obj.getString((ProjectVariables.Tops));
            TextShortTops = obj.getString((ProjectVariables.ShortTops));
            TextJeansL = obj.getString((ProjectVariables.JeansL));
            TextS_SGirls_32_40 = obj.getString((ProjectVariables.S_SGirls_32_40));
            TextWestern_32_36 = obj.getString((ProjectVariables.Western_32_36));
            TextTopsG_32_40 = obj.getString((ProjectVariables.TopsG_32_40));
            TextJeansG_32_40 = obj.getString((ProjectVariables.JeansG_32_40));
            TextLeggingG_32_40 = obj.getString((ProjectVariables.LeggingG_32_40));
            TextHalfSareesG = obj.getString((ProjectVariables.HalfSareesG));
            TextFrocks_16_30 = obj.getString((ProjectVariables.Frocks_16_30));
            TextLeggingG_16_30 = obj.getString((ProjectVariables.LeggingG_16_30));
            TextWestern_16_30 = obj.getString((ProjectVariables.Western_16_30));
            TextJeansG_16_30 = obj.getString((ProjectVariables.JeansG_16_30));
            TextTopsG_16_30 = obj.getString((ProjectVariables.TopsG_16_30));
            TextF_Nightie = obj.getString((ProjectVariables.F_Nighties));
            TextNighties_L = obj.getString((ProjectVariables.Nighties_L));
            TextNightSuit = obj.getString((ProjectVariables.NightSuit));
            TextN_PL = obj.getString((ProjectVariables.N_PL));
            TextN_PBoys = obj.getString((ProjectVariables.N_PBoys));
            TextBra = obj.getString((ProjectVariables.Bra));
            TextPanties = obj.getString((ProjectVariables.Panties));
            TextK_BabyBed = obj.getString((ProjectVariables.K_BabyBed));
            TextK_BabyBlanket = obj.getString((ProjectVariables.K_BabyBlanket));
            TextK_BabyCarryBag = obj.getString((ProjectVariables.K_BabyCarryBag));
            TextK_BabyTowel = obj.getString((ProjectVariables.K_BabyTowel));
            TextK_BabySocks_Glous = obj.getString((ProjectVariables.K_BabySocks_Glous));
            TextShorts_3 = obj.getString((ProjectVariables.Shorts_3));

            TextBanians = obj.getString((ProjectVariables.GFLRBanians));
            TextUnderwear = obj.getString((ProjectVariables.GFLRUnderwear));
            TextTowels_HL = obj.getString((ProjectVariables.GFLRTowelsHL));
            TextTowels_Turkish = obj.getString((ProjectVariables.GFLRTowelsTurkish));
            TextDoor_Curtain = obj.getString((ProjectVariables.GFLRDoorCurtain));
            TextBlankets = obj.getString((ProjectVariables.GFLRBlankets));
            TextPurses = obj.getString((ProjectVariables.GFLRPurses));
            TextBed_Sheets = obj.getString((ProjectVariables.GFLRBedSheets));
            TextChaddars = obj.getString((ProjectVariables.GFLRChaddars));
            TextJeans = obj.getString((ProjectVariables.MGLRTJeans));
            TextCotton_Pants = obj.getString((ProjectVariables.MGLRTCottonPants));
            TextShirts_Cargo = obj.getString((ProjectVariables.MGLRTShirts_Cargo));
            TextFormal_Shirts = obj.getString((ProjectVariables.MGLRTFormalShirts));
            TextT_Shirts = obj.getString((ProjectVariables.MGLRTTShirts));
            TextBranded_Jeans = obj.getString((ProjectVariables.MGLRTBrandedJeans));
            TextBranded_Shirts = obj.getString((ProjectVariables.MGLRTBrandedShirts));
            TextKP_Partywear_Suits = obj.getString((ProjectVariables.MGLRTKP_Partywear_Suits));
            TextBurmadas = obj.getString((ProjectVariables.MGLRTBurmadas));
            TextShirts_Panamerica_Scapes = obj.getString((ProjectVariables.MGLRTShirts_Panamerica_Scapes));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setJSONValues() {
        try {
            if (TextDressMaterial.equalsIgnoreCase("yes")) {
                mTextDressMaterial.setTextColor(Color.parseColor("#388E3C"));
                mTextDressMaterial.setText(TextDressMaterial);
            } else {
                mTextDressMaterial.setTextColor(Color.parseColor("#D50000"));
                mTextDressMaterial.setText(TextDressMaterial);
            }
            if (TextHalfSarees.equalsIgnoreCase("yes")) {
                mTextHalfSarees.setTextColor(Color.parseColor("#388E3C"));
                mTextHalfSarees.setText(TextHalfSarees);
            } else {
                mTextHalfSarees.setTextColor(Color.parseColor("#D50000"));
                mTextHalfSarees.setText(TextHalfSarees);
            }

            if (TextFreeSize_SS.equalsIgnoreCase("yes")) {
                mTextFreeSize_SS.setTextColor(Color.parseColor("#388E3C"));
                mTextFreeSize_SS.setText(TextFreeSize_SS);
            } else {
                mTextFreeSize_SS.setTextColor(Color.parseColor("#D50000"));
                mTextFreeSize_SS.setText(TextFreeSize_SS);
            }

            if (TextKurthies.equalsIgnoreCase("yes")) {
                mTextKurthies.setTextColor(Color.parseColor("#388E3C"));
                mTextKurthies.setText(TextKurthies);
            } else {
                mTextKurthies.setTextColor(Color.parseColor("#D50000"));
                mTextKurthies.setText(TextKurthies);
            }

            if (TextTops.equalsIgnoreCase("yes")) {
                mTextTops.setTextColor(Color.parseColor("#388E3C"));
                mTextTops.setText(TextTops);
            } else {
                mTextTops.setTextColor(Color.parseColor("#D50000"));
                mTextTops.setText(TextTops);
            }

            if (TextShortTops.equalsIgnoreCase("yes")) {
                mTextShortTops.setTextColor(Color.parseColor("#388E3C"));
                mTextShortTops.setText(TextShortTops);
            } else {
                mTextShortTops.setTextColor(Color.parseColor("#D50000"));
                mTextShortTops.setText(TextShortTops);
            }

            if (TextJeansL.equalsIgnoreCase("yes")) {
                mTextJeansL.setTextColor(Color.parseColor("#388E3C"));
                mTextJeansL.setText(TextJeansL);
            } else {
                mTextJeansL.setTextColor(Color.parseColor("#D50000"));
                mTextJeansL.setText(TextJeansL);
            }

            if (TextS_SGirls_32_40.equalsIgnoreCase("yes")) {
                mTextS_SGirls_32_40.setTextColor(Color.parseColor("#388E3C"));
                mTextS_SGirls_32_40.setText(TextS_SGirls_32_40);
            } else {
                mTextS_SGirls_32_40.setTextColor(Color.parseColor("#D50000"));
                mTextS_SGirls_32_40.setText(TextS_SGirls_32_40);
            }

            if (TextWestern_32_36.equalsIgnoreCase("yes")) {
                mTextWestern_32_36.setTextColor(Color.parseColor("#388E3C"));
                mTextWestern_32_36.setText(TextWestern_32_36);
            } else {
                mTextWestern_32_36.setTextColor(Color.parseColor("#D50000"));
                mTextWestern_32_36.setText(TextWestern_32_36);
            }

            if (TextTopsG_32_40.equalsIgnoreCase("yes")) {
                mTextTopsG_32_40.setTextColor(Color.parseColor("#388E3C"));
                mTextTopsG_32_40.setText(TextTopsG_32_40);
            } else {
                mTextTopsG_32_40.setTextColor(Color.parseColor("#D50000"));
                mTextTopsG_32_40.setText(TextTopsG_32_40);
            }

            if (TextJeansG_32_40.equalsIgnoreCase("yes")) {
                mTextJeansG_32_40.setTextColor(Color.parseColor("#388E3C"));
                mTextJeansG_32_40.setText(TextJeansG_32_40);
            } else {
                mTextJeansG_32_40.setTextColor(Color.parseColor("#D50000"));
                mTextJeansG_32_40.setText(TextJeansG_32_40);
            }

            if (TextLeggingG_32_40.equalsIgnoreCase("yes")) {
                mTextLeggingG_32_40.setTextColor(Color.parseColor("#388E3C"));
                mTextLeggingG_32_40.setText(TextLeggingG_32_40);
            } else {
                mTextLeggingG_32_40.setTextColor(Color.parseColor("#D50000"));
                mTextLeggingG_32_40.setText(TextLeggingG_32_40);
            }

            if (TextHalfSareesG.equalsIgnoreCase("yes")) {
                mTextHalfSareesG.setTextColor(Color.parseColor("#388E3C"));
                mTextHalfSareesG.setText(TextHalfSareesG);
            } else {
                mTextHalfSareesG.setTextColor(Color.parseColor("#D50000"));
                mTextHalfSareesG.setText(TextHalfSareesG);
            }

            if (TextFrocks_16_30.equalsIgnoreCase("yes")) {
                mTextFrocks_16_30.setTextColor(Color.parseColor("#388E3C"));
                mTextFrocks_16_30.setText(TextFrocks_16_30);
            } else {
                mTextFrocks_16_30.setTextColor(Color.parseColor("#D50000"));
                mTextFrocks_16_30.setText(TextFrocks_16_30);
            }

            if (TextLeggingG_16_30.equalsIgnoreCase("yes")) {
                mTextLeggingG_16_30.setTextColor(Color.parseColor("#388E3C"));
                mTextLeggingG_16_30.setText(TextLeggingG_16_30);
            } else {
                mTextLeggingG_16_30.setTextColor(Color.parseColor("#D50000"));
                mTextLeggingG_16_30.setText(TextLeggingG_16_30);
            }

            if (TextWestern_16_30.equalsIgnoreCase("yes")) {
                mTextWestern_16_30.setTextColor(Color.parseColor("#388E3C"));
                mTextWestern_16_30.setText(TextWestern_16_30);
            } else {
                mTextWestern_16_30.setTextColor(Color.parseColor("#D50000"));
                mTextWestern_16_30.setText(TextWestern_16_30);
            }

            if (TextJeansG_16_30.equalsIgnoreCase("yes")) {
                mTextJeansG_16_30.setTextColor(Color.parseColor("#388E3C"));
                mTextJeansG_16_30.setText(TextJeansG_16_30);
            } else {
                mTextJeansG_16_30.setTextColor(Color.parseColor("#D50000"));
                mTextJeansG_16_30.setText(TextJeansG_16_30);
            }

            if (TextTopsG_16_30.equalsIgnoreCase("yes")) {
                mTextTopsG_16_30.setTextColor(Color.parseColor("#388E3C"));
                mTextTopsG_16_30.setText(TextTopsG_16_30);
            } else {
                mTextTopsG_16_30.setTextColor(Color.parseColor("#D50000"));
                mTextTopsG_16_30.setText(TextTopsG_16_30);
            }

            if (TextF_Nightie.equalsIgnoreCase("yes")) {
                mTextF_Nightie.setTextColor(Color.parseColor("#388E3C"));
                mTextF_Nightie.setText(TextF_Nightie);
            } else {
                mTextF_Nightie.setTextColor(Color.parseColor("#D50000"));
                mTextF_Nightie.setText(TextF_Nightie);
            }

            if (TextNighties_L.equalsIgnoreCase("yes")) {
                mTextNighties_L.setTextColor(Color.parseColor("#388E3C"));
                mTextNighties_L.setText(TextNighties_L);
            } else {
                mTextNighties_L.setTextColor(Color.parseColor("#D50000"));
                mTextNighties_L.setText(TextNighties_L);
            }

            if (TextNightSuit.equalsIgnoreCase("yes")) {
                mTextNightSuit.setTextColor(Color.parseColor("#388E3C"));
                mTextNightSuit.setText(TextNightSuit);
            } else {
                mTextNightSuit.setTextColor(Color.parseColor("#D50000"));
                mTextNightSuit.setText(TextNightSuit);
            }

            if (TextN_PL.equalsIgnoreCase("yes")) {
                mTextN_PL.setTextColor(Color.parseColor("#388E3C"));
                mTextN_PL.setText(TextN_PL);
            } else {
                mTextN_PL.setTextColor(Color.parseColor("#D50000"));
                mTextN_PL.setText(TextN_PL);
            }

            if (TextN_PBoys.equalsIgnoreCase("yes")) {
                mTextN_PBoys.setTextColor(Color.parseColor("#388E3C"));
                mTextN_PBoys.setText(TextN_PBoys);
            } else {
                mTextN_PBoys.setTextColor(Color.parseColor("#D50000"));
                mTextN_PBoys.setText(TextN_PBoys);
            }

            if (TextBra.equalsIgnoreCase("yes")) {
                mTextBra.setTextColor(Color.parseColor("#388E3C"));
                mTextBra.setText(TextBra);
            } else {
                mTextBra.setTextColor(Color.parseColor("#D50000"));
                mTextBra.setText(TextBra);
            }

            if (TextPanties.equalsIgnoreCase("yes")) {
                mTextPanties.setTextColor(Color.parseColor("#388E3C"));
                mTextPanties.setText(TextPanties);
            } else {
                mTextPanties.setTextColor(Color.parseColor("#D50000"));
                mTextPanties.setText(TextPanties);
            }

            if (TextK_BabyBed.equalsIgnoreCase("yes")) {
                mTextK_BabyBed.setTextColor(Color.parseColor("#388E3C"));
                mTextK_BabyBed.setText(TextK_BabyBed);
            } else {
                mTextK_BabyBed.setTextColor(Color.parseColor("#D50000"));
                mTextK_BabyBed.setText(TextK_BabyBed);
            }
            if (TextK_BabyBlanket.equalsIgnoreCase("yes")) {
                mTextK_BabyBlanket.setTextColor(Color.parseColor("#388E3C"));
                mTextK_BabyBlanket.setText(TextK_BabyBlanket);
            } else {
                mTextK_BabyBlanket.setTextColor(Color.parseColor("#D50000"));
                mTextK_BabyBlanket.setText(TextK_BabyBlanket);
            }
            if (TextK_BabyCarryBag.equalsIgnoreCase("yes")) {
                mTextK_BabyCarryBag.setTextColor(Color.parseColor("#388E3C"));
                mTextK_BabyCarryBag.setText(TextK_BabyCarryBag);
            } else {
                mTextK_BabyCarryBag.setTextColor(Color.parseColor("#D50000"));
                mTextK_BabyCarryBag.setText(TextK_BabyCarryBag);
            }

            if (TextK_BabyTowel.equalsIgnoreCase("yes")) {
                mTextK_BabyTowel.setTextColor(Color.parseColor("#388E3C"));
                mTextK_BabyTowel.setText(TextK_BabyTowel);
            } else {
                mTextK_BabyTowel.setTextColor(Color.parseColor("#D50000"));
                mTextK_BabyTowel.setText(TextK_BabyTowel);
            }

            if (TextK_BabySocks_Glous.equalsIgnoreCase("yes")) {
                mTextK_BabySocks_Glous.setTextColor(Color.parseColor("#388E3C"));
                mTextK_BabySocks_Glous.setText(TextK_BabySocks_Glous);
            } else {
                mTextK_BabySocks_Glous.setTextColor(Color.parseColor("#D50000"));
                mTextK_BabySocks_Glous.setText(TextK_BabySocks_Glous);
            }

            if (TextShorts_3.equalsIgnoreCase("yes")) {
                mTextShorts_3.setTextColor(Color.parseColor("#388E3C"));
                mTextShorts_3.setText(TextShorts_3);
            } else {
                mTextShorts_3.setTextColor(Color.parseColor("#D50000"));
                mTextShorts_3.setText(TextShorts_3);
            }

            if (TextBanians.equalsIgnoreCase("yes")) {
                mTextBanians.setTextColor(Color.parseColor("#388E3C"));
                mTextBanians.setText(TextBanians);
            } else {
                mTextBanians.setTextColor(Color.parseColor("#D50000"));
                mTextBanians.setText(TextBanians);
            }
            if (TextUnderwear.equalsIgnoreCase("yes")) {
                mTextUnderwear.setTextColor(Color.parseColor("#388E3C"));
                mTextUnderwear.setText(TextUnderwear);
            } else {
                mTextUnderwear.setTextColor(Color.parseColor("#D50000"));
                mTextUnderwear.setText(TextUnderwear);
            }
            if (TextTowels_HL.equalsIgnoreCase("yes")) {
                mTextTowels_HL.setTextColor(Color.parseColor("#388E3C"));
                mTextTowels_HL.setText(TextTowels_HL);
            } else {
                mTextTowels_HL.setTextColor(Color.parseColor("#D50000"));
                mTextTowels_HL.setText(TextTowels_HL);
            }
            if (TextTowels_Turkish.equalsIgnoreCase("yes")) {
                mTextTowels_Turkish.setTextColor(Color.parseColor("#388E3C"));
                mTextTowels_Turkish.setText(TextTowels_Turkish);
            } else {
                mTextTowels_Turkish.setTextColor(Color.parseColor("#D50000"));
                mTextTowels_Turkish.setText(TextTowels_Turkish);
            }
            if (TextDoor_Curtain.equalsIgnoreCase("yes")) {
                mTextDoor_Curtain.setTextColor(Color.parseColor("#388E3C"));
                mTextDoor_Curtain.setText(TextDoor_Curtain);
            } else {
                mTextDoor_Curtain.setTextColor(Color.parseColor("#D50000"));
                mTextDoor_Curtain.setText(TextDoor_Curtain);
            }
            if (TextBlankets.equalsIgnoreCase("yes")) {
                mTextBlankets.setTextColor(Color.parseColor("#388E3C"));
                mTextBlankets.setText(TextBlankets);
            } else {
                mTextBlankets.setTextColor(Color.parseColor("#D50000"));
                mTextBlankets.setText(TextBlankets);
            }
            if (TextPurses.equalsIgnoreCase("yes")) {
                mTextPurses.setTextColor(Color.parseColor("#388E3C"));
                mTextPurses.setText(TextPurses);
            } else {
                mTextPurses.setTextColor(Color.parseColor("#D50000"));
                mTextPurses.setText(TextPurses);
            }
            if (TextBed_Sheets.equalsIgnoreCase("yes")) {
                mTextBed_Sheets.setTextColor(Color.parseColor("#388E3C"));
                mTextBed_Sheets.setText(TextBed_Sheets);
            } else {
                mTextBed_Sheets.setTextColor(Color.parseColor("#D50000"));
                mTextBed_Sheets.setText(TextBed_Sheets);
            }
            if (TextChaddars.equalsIgnoreCase("yes")) {
                mTextChaddars.setTextColor(Color.parseColor("#388E3C"));
                mTextChaddars.setText(TextChaddars);
            } else {
                mTextChaddars.setTextColor(Color.parseColor("#D50000"));
                mTextChaddars.setText(TextChaddars);
            }
            if (TextJeans.equalsIgnoreCase("yes")) {
                mTextJeans.setTextColor(Color.parseColor("#388E3C"));
                mTextJeans.setText(TextJeans);
            } else {
                mTextJeans.setTextColor(Color.parseColor("#D50000"));
                mTextJeans.setText(TextJeans);
            }
            if (TextCotton_Pants.equalsIgnoreCase("yes")) {
                mTextCotton_Pants.setTextColor(Color.parseColor("#388E3C"));
                mTextCotton_Pants.setText(TextCotton_Pants);
            } else {
                mTextCotton_Pants.setTextColor(Color.parseColor("#D50000"));
                mTextCotton_Pants.setText(TextCotton_Pants);
            }
            if (TextShirts_Cargo.equalsIgnoreCase("yes")) {
                mTextShirts_Cargo.setTextColor(Color.parseColor("#388E3C"));
                mTextShirts_Cargo.setText(TextShirts_Cargo);
            } else {
                mTextShirts_Cargo.setTextColor(Color.parseColor("#D50000"));
                mTextShirts_Cargo.setText(TextShirts_Cargo);
            }
            if (TextFormal_Shirts.equalsIgnoreCase("yes")) {
                mTextFormal_Shirts.setTextColor(Color.parseColor("#388E3C"));
                mTextFormal_Shirts.setText(TextFormal_Shirts);
            } else {
                mTextFormal_Shirts.setTextColor(Color.parseColor("#D50000"));
                mTextFormal_Shirts.setText(TextFormal_Shirts);
            }
            if (TextT_Shirts.equalsIgnoreCase("yes")) {
                mTextT_Shirts.setTextColor(Color.parseColor("#388E3C"));
                mTextT_Shirts.setText(TextT_Shirts);
            } else {
                mTextT_Shirts.setTextColor(Color.parseColor("#D50000"));
                mTextT_Shirts.setText(TextT_Shirts);
            }
            if (TextBranded_Jeans.equalsIgnoreCase("yes")) {
                mTextBranded_Jeans.setTextColor(Color.parseColor("#388E3C"));
                mTextBranded_Jeans.setText(TextBranded_Jeans);
            } else {
                mTextBranded_Jeans.setTextColor(Color.parseColor("#D50000"));
                mTextBranded_Jeans.setText(TextBranded_Jeans);
            }
            if (TextBranded_Shirts.equalsIgnoreCase("yes")) {
                mTextBranded_Shirts.setTextColor(Color.parseColor("#388E3C"));
                mTextBranded_Shirts.setText(TextBranded_Shirts);
            } else {
                mTextBranded_Shirts.setTextColor(Color.parseColor("#D50000"));
                mTextBranded_Shirts.setText(TextBranded_Shirts);
            }
            if (TextKP_Partywear_Suits.equalsIgnoreCase("yes")) {
                mTextKP_Partywear_Suits.setTextColor(Color.parseColor("#388E3C"));
                mTextKP_Partywear_Suits.setText(TextKP_Partywear_Suits);
            } else {
                mTextKP_Partywear_Suits.setTextColor(Color.parseColor("#D50000"));
                mTextKP_Partywear_Suits.setText(TextKP_Partywear_Suits);
            }
            if (TextBurmadas.equalsIgnoreCase("yes")) {
                mTextBurmadas.setTextColor(Color.parseColor("#388E3C"));
                mTextBurmadas.setText(TextBurmadas);
            } else {
                mTextBurmadas.setTextColor(Color.parseColor("#D50000"));
                mTextBurmadas.setText(TextBurmadas);
            }
            if (TextShirts_Panamerica_Scapes.equalsIgnoreCase("yes")) {
                mTextShirts_Panamerica_Scapes.setTextColor(Color.parseColor("#388E3C"));
                mTextShirts_Panamerica_Scapes.setText(TextShirts_Panamerica_Scapes);
            } else {
                mTextShirts_Panamerica_Scapes.setTextColor(Color.parseColor("#D50000"));
                mTextShirts_Panamerica_Scapes.setText(TextShirts_Panamerica_Scapes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
