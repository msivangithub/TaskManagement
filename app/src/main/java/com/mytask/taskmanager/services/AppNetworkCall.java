package com.mytask.taskmanager.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.mytask.taskmanager.util.AlertDialogManager;
import com.mytask.taskmanager.util.ProjectVariables;

import org.apache.http.NameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEWSYSTEM1 on 4/30/2016.
 */
public class AppNetworkCall extends AsyncTask<Void, Void, Void> {

    private ProgressDialog pDialog;
    private String url;
    private Activity activity;
    private JSONParser jsonParser;
    private JSONArray jsonArray;
    Context context;
    AlertDialogManager dialogManager = new AlertDialogManager();
    public AppNetworkCall(Activity activity, String url) {
        this.url = url;
        this.activity = activity;

    }

    @Override
    protected void onPreExecute() {

        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait.........");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... params) {

        List<NameValuePair> postparam = new ArrayList<NameValuePair>();
        jsonParser = new JSONParser();
        String json = jsonParser.makeHttpRequest(url, "POST", postparam);


        try {
            jsonArray = new JSONArray(json);

        } catch (Exception e) {
            onFailure(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (pDialog != null) {
            pDialog = null;
        }
        if (jsonArray == null || jsonArray.length() == 0) {

            dialogManager.showNoDataFoundDialog(activity,"No data found........ !","Please Choose Proper Details",false);
          /*  final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setIcon(R.drawable.error);
            builder.setTitle("No data found........");
            builder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();*/
        }
        if (jsonArray != null) {
            onComplete(jsonArray);
        } else {


            onFailure("Invalid data found, check with adminstrator.");
        }
    }


    public void onComplete(final JSONArray array) {
    }

    public void onFailure(String errorMsg) {

    }
}

