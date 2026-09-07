package com.office.taskmanager.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.office.taskmanager.util.ProjectVariables;

import java.io.InputStream;

/**
 * Created by vbsystem on 1/28/2017.
 */

public class ImageUploadTask extends AsyncTask<Void, Void, String> {

    private InputStream inputStream;
    private String str;
    private ProgressDialog pdForVideoUpload;
    private Context context;

    public ImageUploadTask(Context context, InputStream stream, String s) {
        this.inputStream = stream;
        this.context = context;
        this.str = s;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            easyFTP ftp = new easyFTP();
            ftp.connect(ProjectVariables.FTP_HOST, ProjectVariables.FTP_USER, ProjectVariables.FTP_PASS);
            boolean status = false;
            // myaccountsonline.co.in\OfficeTaskManager\DBImages
            status = ftp.setWorkingDirectory(ProjectVariables.IMAGE_FOLD);
            //status = ftp.setWorkingDirectory("/makeindiakart.com/taskfiles");
            //InputStream targetStream = getResources().openRawResource(+R.drawable.ic_launcher);
            InputStream targetStream = inputStream;
            ftp.uploadFile(targetStream, str);
            Log.e("Status", status + "");
            pdForVideoUpload.dismiss();
            return new String("Upload Successful");
        } catch (Exception e) {
            pdForVideoUpload.dismiss();
            String t = "Failure : " + e.getLocalizedMessage();
            return t;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdForVideoUpload = new ProgressDialog(context);
        pdForVideoUpload.show();
        pdForVideoUpload.setMessage("Uploading......");
        pdForVideoUpload.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPostExecute(String s) {
        if (pdForVideoUpload.isShowing()) {
            pdForVideoUpload.cancel();
        }
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
