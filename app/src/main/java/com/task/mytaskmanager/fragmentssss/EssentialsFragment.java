package com.task.mytaskmanager.fragmentssss;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class EssentialsFragment extends Fragment implements RestfulListener {

    private static final int PICK_IMAGE = 100;
    ImageView image;
    String selectedImage;
    TextView click, camera, sdCard;
    private TextView emailAndMobile, mfirstName, mlastName;
    String email, firstname, secondname;
    private Spinner mSpinnerCompanyId, mSpinnerCounter;
    String companyID;
    RestfulListener listener;
    JSONObject jsonObject;
    Button mSubmit;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    public static EssentialsFragment newInstance() {

        Bundle args = new Bundle();

        EssentialsFragment fragment = new EssentialsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.essentials_activity, container, false);
        getActivity().setTitle("Add User");
        image = (ImageView) v.findViewById(R.id.display_image);
        click = (TextView) v.findViewById(R.id.click);
        emailAndMobile = (TextView) v.findViewById(R.id.emailAndMobile);
        mfirstName = (TextView) v.findViewById(R.id.firstName);
        mlastName = (TextView) v.findViewById(R.id.lastName);
        mSpinnerCompanyId = (Spinner) v.findViewById(R.id.companyName);
        mSubmit = (Button) v.findViewById(R.id.submit);
        listener = this;
        mSpinnerCompanyId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                companyID = mSpinnerCompanyId.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"), PICK_IMAGE);

            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailAndMobile.getText().toString();
                firstname = mfirstName.getText().toString();
                secondname = mlastName.getText().toString();

                if (AppUtil.isNetworkAvailable(getActivity())) {
                    //Here we can call listener for calling webservice
                    JSONObject obj = new JSONObject();
                    try {
                        obj.accumulate("MailID", emailAndMobile.getText().toString());
                        obj.accumulate("FirstName", mfirstName.getText().toString());
                        obj.accumulate("LastName", mlastName.getText().toString());
                        obj.accumulate("Compname", companyID.toString());
                        obj.accumulate("Image" ,selectedImage.toString());
                        obj.accumulate("IMEID", "1254788");
                    } catch (Exception e) {

                    }
                    AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 0, ProjectVariables.USER_PROFILE, listener, obj, "");
                    post.execute();


                } else {
                    Toast.makeText(getActivity(), ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
                }
            }
        });


        return v;
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedimg = data.getData();
                try {
                    image.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedimg));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error Occred for ",e.getMessage().toString());
                }

            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:

            default:
                break;
        }

        return false;
    }

    @Override
    public void getData(String s, String status) {
        if (status.equalsIgnoreCase("1")) {
            try {
                ProjectVariables.MAILID = emailAndMobile.getText().toString();
                ProjectVariables.FIRSTNAME = mfirstName.getText().toString();
                ProjectVariables.LASTNAME = mlastName.getText().toString();

                JSONArray Response = new JSONArray(s);
                JSONObject loginResponse = Response.getJSONObject(0);
                String result = "";

                if (result.equalsIgnoreCase("inserted successfully")) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                }


            } catch (Exception e) {
                Toast.makeText(getContext(), s.toString(), Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), " " + s, Toast.LENGTH_LONG).show();
        }

    }
}