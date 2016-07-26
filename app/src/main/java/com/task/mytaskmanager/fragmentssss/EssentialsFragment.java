package com.task.mytaskmanager.fragmentssss;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.task.mytaskmanager.Databases.PostsDatabaseHelper;
import com.task.mytaskmanager.Pojo.Post;
import com.task.mytaskmanager.Pojo.TaskBranches;
import com.task.mytaskmanager.Pojo.User;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.services.addbutton;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class EssentialsFragment extends Fragment implements RestfulListener {
    private ArrayList<TaskBranches> branches = new ArrayList<>();
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_IMAGE = 200;
    ImageView image;
    String imageURI = "";
    String ImageName = "";
    int branchpos = 0;
    private String directory;
    String selectedImage;
    TextView click, camera, sdCard;
    private TextView emailAndMobile, mfirstName, mlastName;
    String email, firstname, secondname;
    private Spinner mSpinnerCompanyId, mSpinnerCounter;
    String companyID = "";
    RestfulListener listener;
    JSONObject jsonObject;
    Button mSubmit;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    Button mGallery, mCamera;
    private Spinner essentialBranches;
    EditText edt_password, edt_mobile;

    public static EssentialsFragment newInstance() {

        Bundle args = new Bundle();

        EssentialsFragment fragment = new EssentialsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.essentials_activity, container, false);
        getActivity().setTitle("Add User");
        setHasOptionsMenu(true);
        edt_mobile = (EditText) v.findViewById(R.id.Mobile);
        image = (ImageView) v.findViewById(R.id.display_image);
        emailAndMobile = (TextView) v.findViewById(R.id.emailAndMobile);
        mfirstName = (TextView) v.findViewById(R.id.firstName);
        mlastName = (TextView) v.findViewById(R.id.lastName);
        essentialBranches = (Spinner) v.findViewById(R.id.txt_exxentinalBranches);
        mSubmit = (Button) v.findViewById(R.id.submit);
        listener = this;
        edt_password = (EditText) v.findViewById(R.id.add_password);

        AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 002, ProjectVariables.BRANCHES, listener, null, "");
        post.execute();
        essentialBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branches = AppUtil.getBranchesInfo();
                branchpos = position;
                companyID = branches.get(position).getBranchId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.mode_takepic);
                d.setTitle("Select Image...");
                d.show();


                mGallery = (Button) d.findViewById(R.id.gallery);
                mCamera = (Button) d.findViewById(R.id.camera);

                mGallery.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), PICK_IMAGE);
                    }
                });

                mCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        ImageName = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                        File f = new File(Environment.getExternalStorageDirectory(), ImageName);
                        Uri u = Uri.fromFile(f);
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        startActivityForResult(intent, CAMERA_IMAGE);

                    }
                });


            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailAndMobile.getText().toString();
                firstname = mfirstName.getText().toString();
                secondname = mlastName.getText().toString();
                String t1 = emailAndMobile.getText().toString();
                String t2 = mfirstName.getText().toString();
                String t3 = mlastName.getText().toString();
                String t4 = edt_mobile.getText().toString();
                String t5 = edt_password.getText().toString();
                String t6 = branches.get(branchpos).getBranchId();
                String t7 = imageURI;
                if (!(t1.isEmpty() && t1.toString().equalsIgnoreCase("")) && !(t2.isEmpty() && t2.equalsIgnoreCase("")) &&
                        !(t3.isEmpty() && t3.equalsIgnoreCase("")) && !(t4.isEmpty() && t4.equalsIgnoreCase(""))
                        && !(t5.isEmpty() && t5.equalsIgnoreCase("")) && !(t6.isEmpty() && t6.equalsIgnoreCase(""))
                        && !(t7.isEmpty() && t7.equalsIgnoreCase(""))) {
                    if (AppUtil.isNetworkAvailable(getActivity())) {
                        //Here we can call listener for calling webservice
                        JSONObject obj = new JSONObject();
                        try {
                            obj.accumulate("MailID", emailAndMobile.getText().toString());
                            obj.accumulate("FirstName", mfirstName.getText().toString());
                            obj.accumulate("LastName", mlastName.getText().toString());
                            obj.accumulate("Compname", companyID);
                            obj.accumulate("Image", imageURI);
                            obj.accumulate("PhoneNo", edt_mobile.getText().toString());
                            obj.accumulate("Password", edt_password.getText().toString());
                            obj.accumulate("IMEID", "1254788");
                            obj.accumulate("Macid", "456789");
                            obj.accumulate("android", "98445");
                            obj.accumulate("UserRole", "3");
                            obj.accumulate("BranchName", "myaccounts");
                            obj.accumulate("AppName", "TaskManager");
                        } catch (Exception e) {

                        }
                        AsynHttpPost post = new AsynHttpPost(getActivity(), 2, 001, ProjectVariables.USER_PROFILE, listener, obj, "");
                        post.execute();


                    } else {
                        Toast.makeText(getActivity(), ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_LONG).show();
                }
            }
        });


        return v;
    }

    public String getStringImage(Bitmap bmp) {
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
                imageURI = selectedimg.toString();
                User user = new User();
                user.userName = "user1";
                user.profilePictureUrl = imageURI;
                Post post1 = new Post();
                post1.user = user;
                post1.text = requestCode + "";

                PostsDatabaseHelper databaseHelper = PostsDatabaseHelper.getInstance(getActivity());
                databaseHelper.addPost(post1);

                try {
                    image.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedimg));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error Occred for ", e.getMessage().toString());
                }

            } else if (requestCode == CAMERA_IMAGE) {
                imageURI = ImageName;

                image.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imageURI)));
                User user = new User();
                user.userName = "user1";
                user.profilePictureUrl = imageURI;
                Post post1 = new Post();
                post1.user = user;
                post1.text = requestCode + "";

                PostsDatabaseHelper databaseHelper = PostsDatabaseHelper.getInstance(getActivity());
                databaseHelper.addPost(post1);


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
    public void getData(String s, String status, int type) {

        if (type == 001) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    emailAndMobile.setText("");
                    mfirstName.setText("");
                    mlastName.setText("");
                    companyID = "";
                    imageURI = "";
                    edt_mobile.setText("");
                    edt_password.setText("");
                    image.setImageDrawable(getActivity().getDrawable(R.drawable.imge_placeholder));
                    ProjectVariables.MAILID = emailAndMobile.getText().toString();
                    ProjectVariables.FIRSTNAME = mfirstName.getText().toString();
                    ProjectVariables.LASTNAME = mlastName.getText().toString();

                    JSONArray Response = new JSONArray(s);
                    JSONObject loginResponse = Response.getJSONObject(0);

                    String result = loginResponse.getString("Result");
                    if (result.equalsIgnoreCase("inserted successfully")) {

                        Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), "User not created!", Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), " " + s, Toast.LENGTH_LONG).show();
            }
        } else if (type == 002) {
            try {
                JSONArray array = new JSONArray(s);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = array.getJSONObject(i);


                    TaskBranches branch = new TaskBranches();

                    branch.setBranchName(obj.getString(ProjectVariables.BNAME));

                    branch.setBranchId(obj.getString(ProjectVariables.BRANCHID));


                    branches.add(branch);
                }

                AppUtil.setBranchesInfo(branches);
                String[] us = new String[branches.size()];
                for (int i = 0; i < branches.size(); i++) {
                    us[i] = branches.get(i).getBranchName();
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                essentialBranches.setAdapter(spinnerArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


}