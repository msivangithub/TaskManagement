package com.task.mytaskmanager.fragmentssss;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.task.mytaskmanager.Databases.PostsDatabaseHelper;
import com.task.mytaskmanager.Pojo.Post;
import com.task.mytaskmanager.Pojo.TaskBranches;
import com.task.mytaskmanager.Pojo.User;
import com.task.mytaskmanager.Pojo.UserRoles;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class EssentialsFragment extends Fragment implements RestfulListener {
    private ArrayList<TaskBranches> branches = new ArrayList<>();
    private ArrayList<UserRoles> userRolesArrayList = new ArrayList<>();
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_IMAGE = 200;
    ImageView image;
    String imageURI = "";
    String ImageName = "";
    int branchpos = 0;
    int userpos = 0;

    private String directory;
    String selectedImage;
    TextView click, camera, sdCard;
    private TextView emailAndMobile, mfirstName, mlastName;
    String email, firstname, secondname;
    private Spinner mSpinnerCompanyId, mSpinnerCounter;
    String companyID = "";
    String userRolesID = "";
    RestfulListener listener;
    Button mSubmit;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    LinearLayout mGallery, mCamera;
    private Spinner essentialBranches, mUserRoles;
    EditText edt_password, edt_mobile;
    JSONArray jsonArray;
    JSONObject jsonObject;

    static final String FTP_HOST = "myaccountsretail.com";
    ProgressDialog pd;
    /*  FTP USERNAME*/
    static final String FTP_USER = "myRetail";
    /*FTP PASSWORD*/
    static final String FTP_PASS = "vKsj30!9";


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
        initPermissions();
        edt_mobile = (EditText) v.findViewById(R.id.Mobile);
        image = (ImageView) v.findViewById(R.id.display_image);
        emailAndMobile = (TextView) v.findViewById(R.id.emailAndMobile);
        mfirstName = (TextView) v.findViewById(R.id.firstName);
        mlastName = (TextView) v.findViewById(R.id.lastName);
        essentialBranches = (Spinner) v.findViewById(R.id.txt_exxentinalBranches);
        mSubmit = (Button) v.findViewById(R.id.submit);
        mUserRoles = (Spinner) v.findViewById(R.id.user_roles);
        listener = this;
        pd = new ProgressDialog(getActivity());
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
        final AsynHttpPost user = new AsynHttpPost(getActivity(), 2, 003, ProjectVariables.USER_ROLES, listener, null, "");
        user.execute();
        mUserRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                userRolesArrayList = AppUtil.getUserRolesInfo();
                userpos = position;
                userRolesID = userRolesArrayList.get(position).getRoId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.mode_takepic);
                d.setTitle("Select Image.....!");
                d.show();

                mGallery = (LinearLayout) d.findViewById(R.id.gallery);
                mCamera = (LinearLayout) d.findViewById(R.id.camera);
                Button CAncel = (Button) d.findViewById(R.id.CAncel);
                CAncel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                //            for now dont use magallery ok dont press gallery.
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
                String t8 = userRolesArrayList.get(userpos).getRoId();
                if (!(t1.isEmpty() && t1.toString().equalsIgnoreCase("")) && !(t2.isEmpty() && t2.equalsIgnoreCase("")) &&
                        !(t3.isEmpty() && t3.equalsIgnoreCase("")) && !(t4.isEmpty() && t4.equalsIgnoreCase(""))
                        && !(t5.isEmpty() && t5.equalsIgnoreCase("")) && !(t6.isEmpty() && t6.equalsIgnoreCase(""))
                        && !(t7.isEmpty() && t7.equalsIgnoreCase(""))
                        && !(t8.isEmpty() && t8.equalsIgnoreCase(""))) {

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
                            obj.accumulate("UserRole", userRolesID);
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
                    imageURI = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                    UploadTask task = new UploadTask(null, imageURI, selectedimg);
                    task.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error Occred for ", e.getMessage().toString());
                }

            } else if (requestCode == CAMERA_IMAGE) {
                imageURI = ImageName;
                //image.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imageURI)));
                //here we can add ftp call    i will open camera yes onslsy camer only camera.....ok
                UploadTask task = new UploadTask(new File(Environment.getExternalStorageDirectory(), imageURI), imageURI,null);
                task.execute();
                image.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
                pd.show();
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

    private class UploadTask extends AsyncTask<Void, Void, String> {
        File f;
        String Image;
        Uri fileUri;

        public UploadTask(File file, String imageName, Uri u) {
            f = file;
            fileUri = u;
            Image = imageName;
        }

        @Override
        protected String doInBackground(Void... voids) {
            //uploadFile(f);
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect(FTP_HOST, FTP_USER, FTP_PASS);
                boolean status = false;
                status = ftp.setWorkingDirectory("/makeindiakart.com/taskfiles");
                //InputStream targetStream = getResources().openRawResource(+R.drawable.ic_launcher);
                if (fileUri == null) {
                    InputStream targetStream = new FileInputStream(f);
                    ftp.uploadFile(targetStream, Image);
                } else {
                    InputStream stream = getActivity().getContentResolver().openInputStream(fileUri);
                    ftp.uploadFile(stream, Image);
                }
                Log.e("Status", status + "");
                pd.dismiss();
                return new String("Upload Successful");
            } catch (Exception e) {
                pd.dismiss();
                String t = "Failure : " + e.getLocalizedMessage();
                return t;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
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
                    userRolesID = "";
                    edt_mobile.setText("");
                    edt_password.setText("");
                    image.setImageDrawable(getActivity().getDrawable(R.drawable.imge_placeholder));
                    ProjectVariables.MAILID = emailAndMobile.getText().toString();
                    ProjectVariables.FIRSTNAME = mfirstName.getText().toString();
                    ProjectVariables.LASTNAME = mlastName.getText().toString();
                    ProjectVariables.ImAGE = imageURI.toString();

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
                essentialBranches.setAdapter(spinnerArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type == 003) {
            try {
                jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    UserRoles roles = new UserRoles();
                    roles.setRoleName(jsonObject.getString(ProjectVariables.ROLENAME));
                    roles.setRoId(jsonObject.getString(ProjectVariables.ROID));

                    userRolesArrayList.add(roles);

                }
                AppUtil.setUserRolesInfo(userRolesArrayList);
                String[] user = new String[userRolesArrayList.size()];
                for (int i = 0; i < userRolesArrayList.size(); i++) {
                    user[i] = userRolesArrayList.get(i).getRoleName();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, user);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mUserRoles.setAdapter(arrayAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}