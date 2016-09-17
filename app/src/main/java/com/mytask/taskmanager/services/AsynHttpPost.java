package com.mytask.taskmanager.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.mytask.taskmanager.util.AppUtil;
import com.mytask.taskmanager.util.ProjectVariables;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by GhanaShyam on 7/13/2016.
 */
public class AsynHttpPost extends AsyncTask<Void, Void, String> {
    private ProgressDialog pd;
    private Context _con;
    private int _requestId;
    private int _requestType;
    private String _apiMethod;
    private RestfulListener _listener;
    private JSONObject _obj;
    private String serviceStatus;


    public AsynHttpPost(Context con, int requestId, int requestType, String apiMethod, RestfulListener listener, JSONObject obj, String temp2) {
        _con = con;
        _requestId = requestId;
        _requestType = requestType;
        _apiMethod = apiMethod;
        _listener = listener;
        _obj = obj;
    }


    @Override
    protected String doInBackground(Void... params) {

        String MainUrl = ProjectVariables.BASE_URL + _apiMethod;
        String result = null;
        HttpPost post = null;
        HttpResponse res = null;
        InputStream inputStream = null;
        if (AppUtil.isNetworkAvailable(_con)) {
            try {


                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters,
                        ProjectVariables.APK_CONNECTION_TIMEOUT);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                // int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters,
                        ProjectVariables.APK_WAIT_DATA_TIMEOUT);

                DefaultHttpClient httpClient = new DefaultHttpClient(
                        httpParameters);


                post = new HttpPost(MainUrl);
                String json = "";

                if (_obj != null) {
                    json = _obj.toString();
                    Log.e("Sending json is", "" + json);
                    StringEntity se = new StringEntity(json, HTTP.UTF_8);
                    post.setEntity(se);

                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                }
                    res = httpClient.execute(post);


                // for (int attempt = 0; attempt < 3; attempt++) {

                if (res.getStatusLine().getStatusCode() == 200) {
                    serviceStatus = "1";
                    inputStream = res.getEntity().getContent();

                    if (inputStream != null) {


                        result = convertInputStreamToString(inputStream);
                        pd.dismiss();
                        if (result.length() > 10) {
                            if (_requestId == 1) {


                            }

                            if (_requestId == 2) {


                            }
                        } else {
                            serviceStatus = "0";
                            return "NO DATA FOUND";
                        }
                    } else {
                        pd.dismiss();
                        serviceStatus = "0";
                        return "NO DATA FOUND";
                    }
                } else {
                    pd.dismiss();
                    serviceStatus = "0";
                    return res.getStatusLine().getStatusCode() + " Error";
                }


            } catch (Exception e) {
                e.printStackTrace();
                serviceStatus = "0";
                Log.e("Exception Occured ", e.getMessage().toString());
                pd.dismiss();
                return e.getMessage().toString();
            }

        } else {
            pd.dismiss();
            serviceStatus = "0";
            return "Try again later";

        }


        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        _listener.getData(s, serviceStatus, _requestType);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(_con);
        pd.setMessage("Loading Please Wait....");
        pd.show();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
