package com.office.taskmanager.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by vbsystem on 11/28/2016.
 */

public class ProgressDialogsUtils {

    public static ProgressDialog showProgressDialog(Context context){
        ProgressDialog mDialog = new ProgressDialog(context);
        mDialog.setTitle("Please wait......");
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        mDialog.show();
        return mDialog;
    }

}
