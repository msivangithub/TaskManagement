package com.task.taskmanager.fragmentssss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.taskmanager.R;
import com.task.taskmanager.util.PreferenceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by GhanaShyam on 8/23/2016.
 */
public class SettingsFragments extends Fragment {

    public static SettingsFragments newInstance() {

        Bundle args = new Bundle();

        SettingsFragments fragment = new SettingsFragments();
        fragment.setArguments(args);
        return fragment;
    }

    TextView user, Mail, phone, branchname, city;
    ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragments, container, false);
        getActivity().setTitle("Setting");
        user = (TextView) v.findViewById(R.id.txt_UserName);
        Mail = (TextView) v.findViewById(R.id.txt_Mail);
        image = (ImageView) v.findViewById(R.id.profiles_imageView);
        phone = (TextView) v.findViewById(R.id.phoneNo);
        branchname = (TextView) v.findViewById(R.id.branchname);
        city = (TextView) v.findViewById(R.id.city);

        ProfileImageFromURL loadImage = new ProfileImageFromURL();
        loadImage.execute();

        user.setText(PreferenceUtil.getInstance().getString(getActivity(), "FirstName", "User"));
        Mail.setText(PreferenceUtil.getInstance().getString(getActivity(), "MailID", "user@mail.com"));
        phone.setText(PreferenceUtil.getInstance().getString(getActivity(), "PhoneNo", "phoneno"));
        branchname.setText(PreferenceUtil.getInstance().getString(getActivity(), "BranchName", "branch"));
        city.setText(PreferenceUtil.getInstance().getString(getActivity(), "City", "city"));

        return v;
    }

    public class ProfileImageFromURL extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            //http://makeindiakart.com/taskfiles/Image_1290.jpg
            try {
                String MainUrl = "http://makeindiakart.com/taskfiles/";
                URL url = new URL(MainUrl + PreferenceUtil.getInstance().getString(getActivity(), "ProfileImage", "Image_5756.jpg"));
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                return bitMap;

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            image.setImageBitmap(result);
        }
    }
}
