package com.office.taskmanager.fragmentssss;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.adeel.library.easyFTP;
import com.office.taskmanager.Adaptes.MultitaskAdapter;
import com.office.taskmanager.Databases.PostsDatabaseHelper;
import com.office.taskmanager.Pojo.Post;
import com.office.taskmanager.Pojo.TaskUser;
import com.office.taskmanager.Pojo.User;
import com.office.taskmanager.R;
import com.office.taskmanager.activity.RecordAudioActivity;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.ImageUploadTask;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.services.addbutton;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.DatePickerFragment;
import com.office.taskmanager.util.MultiTask;
import com.office.taskmanager.util.OnDateSetCompleted;
import com.office.taskmanager.util.PreferenceUtil;
import com.office.taskmanager.util.ProjectVariables;
import com.office.taskmanager.util.TimePickerFragment;
import com.office.taskmanager.util.onTimeSetCompleted;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.office.taskmanager.util.DateUtil.getRandomNumberInRange;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment1 extends Fragment implements RestfulListener {
    private TextView mTextViewFromDate, mTextViewToDate, mTextViewFromTime, mTextViewToTime, mAdd;
    private ImageView mImageButtonFrom, mImageButtonTo, mfromTimeImage, mToTimeImage, mSelectedImage;
    private EditText TaskHeader;
    private int month, day, year;
    private int seconds, minutes, hour;
    private String status;
    private static String fromDate, toDate;
    static addbutton _ab;
    Spinner users_Spinner, repeat_task;
    ArrayList<TaskUser> usersList = new ArrayList<>();
    EditText edt_task;
    ArrayList<AppUtil> appUtils = new ArrayList<>();
    MultitaskAdapter multitaskAdapter;
    RecyclerView recyclerView;
    String selectUser, taskToId;
    AppUtil appUtil;
    private RadioButton mnone, low, Medium, High, VeryHigh;
    private RadioGroup radioGroup;
    private String priority = "";
    private String taskfromid = "";
    private String taskHead = "";
    private String taskDesc = "";
    private String startfrom = "";
    private String startto = "";
    private String actstart = "";
    private String actend = "";
    private String expstart = "";
    private String expend = "";
    Button recordVideo;
    LinearLayout mImage, mVideo, recordAudio;
    String imageURI = "";
    String audioURI = "";
    /* 103.231.101.101
     myaccoun
     ctrls@123*/
    static final String FTP_HOST = "103.231.101.101";
    static final String FTP_USER = "myaccoun";
    static final String FTP_PASS = "ctrls@123";
    private static final int PICK_IMAGE = 100;
    /* static final String FTP_HOST = "myaccountsretail.com";
     static final String FTP_USER = "myRetail";
     static final String FTP_PASS = "vKsj30!9";*/
    String android_id;
    int repeatID;
    String repeatedValue = "0";
    View view;
    private Context context;
    private String ImageName = "";
    ProgressDialog pdForVideoUpload;
    private VideoView videoPreview;
    private boolean bVideoIsBeingTouched = false;
    private Handler mHandler = new Handler();
    public static TaskFragment1 newInstance(addbutton addbutton) {

        Bundle args = new Bundle();
        _ab = addbutton;
        TaskFragment1 fragment1 = new TaskFragment1();
        fragment1.setArguments(args);
        Log.e("Values", AppUtil.getActStartDate());
        Log.e("Values", AppUtil.getActEndDate());
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_fragment1, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setHasOptionsMenu(true);
        context = getActivity();
        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        appUtils = new ArrayList<>();
        intialVariables();
        edt_task.addTextChangedListener(watcher);
        TaskHeader.addTextChangedListener(watcher1);
        Log.e("Values", AppUtil.getActStartDate());
        Log.e("Values", AppUtil.getActEndDate());
        getBranchWiseUsers();
        dateFormat();
        timeFormat();
        return view;

    }

    private void getBranchWiseUsers() {
        try {
            AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 0, ProjectVariables.USERS + PreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user") + "," + PreferenceUtil.getInstance().getString(getActivity(), "Compname", "companyname"), this, null, "");
            post.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void intialVariables() {
        recordVideo = (Button) view.findViewById(R.id.record);
        TaskHeader = (EditText) view.findViewById(R.id.taskHeader);
        users_Spinner = (Spinner) view.findViewById(R.id.TaskUsers);
        repeat_task = (Spinner) view.findViewById(R.id.repeat_task);
        /*Date format*/
        mTextViewFromDate = (TextView) view.findViewById(R.id.fromDate);
        mTextViewToDate = (TextView) view.findViewById(R.id.toDate);
        mImageButtonFrom = (ImageView) view.findViewById(R.id.fromDateImage);
        mImageButtonTo = (ImageView) view.findViewById(R.id.toDateImage);
        /*Time Format*/
        mTextViewFromTime = (TextView) view.findViewById(R.id.fromTime);
        mTextViewToTime = (TextView) view.findViewById(R.id.toTime);
        mToTimeImage = (ImageView) view.findViewById(R.id.toTimeImage);
        mfromTimeImage = (ImageView) view.findViewById(R.id.fromTimeImage);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mAdd = (TextView) view.findViewById(R.id.add);
        edt_task = (EditText) view.findViewById(R.id.taskDes);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        mnone = (RadioButton) view.findViewById(R.id.none);
        low = (RadioButton) view.findViewById(R.id.low);
        Medium = (RadioButton) view.findViewById(R.id.Medium);
        High = (RadioButton) view.findViewById(R.id.High);
        VeryHigh = (RadioButton) view.findViewById(R.id.veryHigh);
        mSelectedImage = (ImageView) view.findViewById(R.id.SelectedImage);
        videoPreview = (VideoView) view.findViewById(R.id.videoPreview);
        setOnClickListenear();
    }


    private void setOnClickListenear() {

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multipleTaskStore();
            }
        });

        recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.image_video);
                d.setTitle("Select video or Image.....!");
                d.show();
                mImage = (LinearLayout) d.findViewById(R.id.getImage);
                mVideo = (LinearLayout) d.findViewById(R.id.getVideo);
                Button CAncel = (Button) d.findViewById(R.id.CAncel);
                recordAudio = (LinearLayout) d.findViewById(R.id.getAudio);
                CAncel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                                /*Click the AleartDialog video popsition*/
                recordAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        startActivity(new Intent(getActivity(), RecordAudioActivity.class));

                    }
                });
                mVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);
                        // takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath()+"videocapture_example.mp4");
                        startActivityForResult(takeVideoIntent, 001);
                    }
                });
                                 /*Click the AleartDialog image popsition*/
                mImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        ImageName = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                        File f = new File(Environment.getExternalStorageDirectory(), ImageName);
                        Uri u = Uri.fromFile(f);
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 0);
                        intent.putExtra("aspectY", 0);
                        intent.putExtra("outputX", 200);
                        intent.putExtra("outputY", 150);
                        startActivityForResult(intent, 003);
                    }
                });
            }
        });

        repeat_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                repeatID = repeat_task.getSelectedItemPosition();
                if (repeatID == 0) {
                    repeatedValue = "0";
                } else if (repeatID == 1) {
                    repeatedValue = "1";
                } else if (repeatID == 2) {
                    repeatedValue = "7";
                } else if (repeatID == 3) {
                    repeatedValue = "30";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        users_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectUser = users_Spinner.getSelectedItem().toString();
                if (usersList.size() != 0)
                    taskfromid = PreferenceUtil.getInstance().getString(getActivity(), "Uid", "c001");
                //AppUtil.setTaskFromId(PreferenceUtil.getInstance().getString(getActivity(), "Uid", "c001"));
                taskToId = usersList.get(position).getUid();
                //   AppUtil.setTaskToId(taskToId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.none) {
                    priority = "none";

                    // AppUtil.setPriority(priority);
                } else if (checkedId == R.id.low) {
                    priority = "l";
                    // AppUtil.setPriority(priority);
                } else if (checkedId == R.id.Medium) {
                    priority = "m";
                    // AppUtil.setPriority(priority);
                } else if (checkedId == R.id.High) {
                    priority = "h";
                } else if (checkedId == R.id.veryHigh) {
                    priority = "v";
                    // AppUtil.setPriority(priority);
                }
            }
        });


    }

    public long GetGuid() {
        long s = System.currentTimeMillis();
        return s;
    }

    private void multipleTaskStore() {

        if (startto.equalsIgnoreCase("") && startto.length() == 0 ||
                TaskHeader.length() == 0 &&
                        edt_task.length() == 0 &&
                        priority.length() == 0 &&
                        startfrom.equalsIgnoreCase("") && startfrom.length() == 0 ||
                actend.equalsIgnoreCase("") && actend.length() == 0 ||
                actstart.equalsIgnoreCase("") && actstart.length() == 0 ||
                expend.equalsIgnoreCase("") && expend.length() == 0 ||
                expstart.equalsIgnoreCase("") && expstart.length() == 0 ||
                priority.equalsIgnoreCase("") && priority.length() == 0 ||
                taskDesc.equalsIgnoreCase("") && taskDesc.length() == 0 ||
                taskfromid.equalsIgnoreCase("") && taskfromid.length() == 0 ||
                taskToId.equalsIgnoreCase("") && taskToId.length() == 0 ||
                taskHead.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter all details ", Toast.LENGTH_LONG).show();
            return;
        }


        long uniqId = GetGuid();
        ArrayList<MultiTask> tasks = new ArrayList<>();

        MultiTask task = new MultiTask();
        task.setUniqid(uniqId + "");
        task.setSelectUser(selectUser);
        task.setStartToTime(startto);
        task.setStartFromTime(startfrom);
        task.setActEndDate(actend);
        task.setActStartDate(actstart);
        task.setExpEndDate(expend);
        task.setExpStartDate(expstart);
        task.setImeId(android_id);
        task.setPriority(priority);
        task.setTaskDes(taskDesc);
        task.setTaskFromId(taskfromid);
        task.setTaskToId(taskToId);
        task.setTaskHeading(taskHead);
        task.setRepeatedDays(repeatedValue);
        task.setTaskStatus("New");

        tasks.add(task);

        ArrayList<MultiTask> previousTasks = AppUtil.getMultiTasks();
        if (previousTasks.size() != 0) {
            for (int p = 0; p < previousTasks.size(); p++) {
                MultiTask mt = previousTasks.get(p);
                tasks.add(mt);
            }
        }

        AppUtil.setMultiTasks(tasks);
        Toast.makeText(getActivity(), "Task Added in next Tab", Toast.LENGTH_LONG).show();

        TaskHeader.setText("");
        edt_task.setText("");


        StringBuilder sb = new StringBuilder();
        for (MultiTask str : tasks) {
            sb.append(str).append(";"); //separating contents using semi colon
        }
        String strfromArrayList = sb.toString();

        FragmentTransaction transection = getFragmentManager().beginTransaction();
        TaskFragment3 mfragment = new TaskFragment3();
        Bundle bundle = new Bundle();
        bundle.putString("appUtils", strfromArrayList);

        mfragment.setArguments(bundle); //data being send to SecondFragment
        transection.replace(R.id.main_fragment, mfragment);
        transection.commit();


    }


    @Override
    public void onResume() {
        super.onResume();
        _ab.addVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //AppUtil.setTaskDes(s.toString());
            taskDesc = s.toString();
        }
    };
    TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            taskHead = s.toString();
            // AppUtil.setTaskHeading(s.toString());
        }
    };


    private void timeFormat() {
        SimpleDateFormat simpleDateFormat;
        final Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        hour = calander.get(Calendar.HOUR_OF_DAY);
        minutes = calander.get(Calendar.MINUTE);
        String time = simpleDateFormat.format(calander.getTime());
        mTextViewFromTime.setText(time);
        mTextViewToTime.setText(time);
        Log.e("Current Time:=", time);

        mfromTimeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = TimePickerFragment.newTimePickerFragment(hour, minutes, status, mTextViewFromTime);
                newFragment.setOnTimeSetCompleted(new onTimeSetCompleted() {
                    @Override
                    public void onTimeSetCompleted(int hour, int minute, String aMpM) {
                        TaskFragment1.this.hour = hour;
                        TaskFragment1.this.minutes = minute;
                        TaskFragment1.this.status = status;
                        //  AppUtil.setStartFromTime(hour + ":" + minute + aMpM);
                        startfrom = hour + ":" + minute + aMpM;
                        mTextViewFromTime.setText(hour + ":" + minute + aMpM);
                        Log.e("From C*urrent Time :=", hour + ":" + minute + aMpM);

                    }
                });
                newFragment.show(TaskFragment1.this.getFragmentManager(), "1");
                mTextViewFromTime.setTextColor(Color.parseColor("#558B2F"));

            }
        });
        mToTimeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = TimePickerFragment.newTimePickerFragment(hour, minutes, status, mTextViewToTime);
                newFragment.setOnTimeSetCompleted(new onTimeSetCompleted() {
                    @Override
                    public void onTimeSetCompleted(int hour, int minute, String aMpM) {
                        TaskFragment1.this.hour = hour;
                        TaskFragment1.this.minutes = minute;
                        TaskFragment1.this.status = status;
                        //AppUtil.setStartToTime(hour + ":" + minute + aMpM);
                        startto = hour + ":" + minute + aMpM;
                        mTextViewToTime.setText(hour + ":" + minute + aMpM);
                        Log.e("To Current Time:=", hour + ":" + minute + aMpM);
                    }
                });
                newFragment.show(TaskFragment1.this.getFragmentManager(), "1");
                mTextViewToTime.setTextColor(Color.parseColor("#558B2F"));
            }
        });
    }


    private void dateFormat() {

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String currentdate = ss.format(date);

        mTextViewFromDate.setText(currentdate);
        mTextViewToDate.setText(currentdate);
        mImageButtonFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mTextViewFromDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        TaskFragment1.this.year = year;
                        TaskFragment1.this.month = month;
                        TaskFragment1.this.day = day;
//                        AppUtil.setActStartDate(day + "-" + month + "-" + year);
//                        AppUtil.setExpStartDate(day + "-" + month + "-" + year);

                        actstart = (day + "-" + month + "-" + year);
                        expstart = (day + "-" + month + "-" + year);
                    }
                });

                toDatePickerFragment.show(TaskFragment1.this.getFragmentManager(), "1");
                mTextViewFromDate.setTextColor(Color.parseColor("#558B2F"));
            }
        });
        mImageButtonTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mTextViewToDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        TaskFragment1.this.year = year;
                        TaskFragment1.this.month = month;
                        TaskFragment1.this.day = day;
//                        AppUtil.setActEndDate(day + "-" + month + "-" + year);
//                        AppUtil.setExpEndDate(day + "-" + month + "-" + year);
                        actend = (day + "-" + month + "-" + year);
                        expend = (day + "-" + month + "-" + year);


                    }
                });

                toDatePickerFragment.show(TaskFragment1.this.getFragmentManager(), "1");
                mTextViewToDate.setTextColor(Color.parseColor("#558B2F"));
            }
        });

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE && null != data) {
                try {
                    final Uri selectedimg = data.getData();
                    imageURI = selectedimg.toString();
                    User user = new User();
                    user.userName = "user1";
                    user.profilePictureUrl = imageURI;
                    Post post1 = new Post();
                    post1.user = user;
                    post1.text = requestCode + "";
                    PostsDatabaseHelper databaseHelper = PostsDatabaseHelper.getInstance(getActivity());
                    databaseHelper.addPost(post1);
                    ProgressDialog pd = new ProgressDialog(getActivity());
                    pd.setMessage("Uploading......");
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedimg,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageURI = cursor.getString(columnIndex);
                    cursor.close();
                    // image.setImageBitmap(decodeSampledBitmapFromUri(getActivity(), selectedimg, 300, 300));
                    mSelectedImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedimg));
                    imageURI = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                    Log.e("ImageName", imageURI.toString());
                    UploadTasks u = null;
                    try {
                       /* u = new UploadTasks(getActivity().getContentResolver().openInputStream(selectedimg), selectedimg);
                        u.execute();*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error Occred for ", e.getMessage().toString());
                }
            }
            if (requestCode == 001) {
                Uri selectedimg = data.getData();
                String origanImage = getPath(selectedimg);
                String[] imageArray = origanImage.split("/");
                int length = imageArray.length;
                mSelectedImage.setVisibility(View.VISIBLE);
                videoPreview.setVisibility(View.VISIBLE);
                videoPreview.setVideoPath(origanImage);
                videoPreview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        videoPreview.start();
                        return false;
                    }
                });
                String convertedImage = imageArray[length - 1];
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("video", convertedImage);
                curentEdit.commit();
                Toast.makeText(getActivity(), "Video Recorded " + convertedImage, Toast.LENGTH_LONG).show();
                // ImageUploadTask uploadTask = null;
                try {
                    ImageUploadTask uploadTask = new ImageUploadTask(context, getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    uploadTask.execute();
                    //u = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    //u.execute();
                } catch (FileNotFoundException e) {

                }
            } else if (requestCode == 003) {
                imageURI = ImageName;
                try {
                    UploadTasks task = new UploadTasks(new File(Environment.getExternalStorageDirectory(), imageURI), imageURI, null);
                    task.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                videoPreview.setVisibility(View.VISIBLE);
                mSelectedImage.setVisibility(View.VISIBLE);
                mSelectedImage.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("Image", imageURI);
                curentEdit.commit();

                User user = new User();
                user.userName = "user1";
                user.profilePictureUrl = imageURI;
                Post post1 = new Post();
                post1.user = user;
                post1.text = requestCode + "";

                PostsDatabaseHelper databaseHelper = PostsDatabaseHelper.getInstance(getActivity());
                databaseHelper.addPost(post1);



               /* Uri selectedimg = data.getData();
                imageURI = getPath(selectedimg);
                User user = new User();
                user.userName = "user1";
                user.profilePictureUrl = imageURI;
                Post post1 = new Post();
                post1.user = user;
                post1.text = requestCode + "";
                PostsDatabaseHelper databaseHelper = PostsDatabaseHelper.getInstance(getActivity());
                databaseHelper.addPost(post1);
                mSelectedImage.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
                String[] imageArray = imageURI.split("/");
                int length = imageArray.length;
                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("Image", convertedImage);
                curentEdit.commit();
                UploadTask u = null;*/
                try {
                   /* ImageUploadTask uploadTask = new ImageUploadTask(context, getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    uploadTask.execute();*/
                  /*  u = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    u.execute();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == 92) {

                Uri selectedimg = data.getData();
                audioURI = getPath(selectedimg);
                String[] imageArray = audioURI.split("/");

                int length = imageArray.length;
                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("Audio", convertedImage);
                curentEdit.commit();
                //UploadTask u = null;
                try {
                    ImageUploadTask uploadTask = new ImageUploadTask(context, getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    uploadTask.execute();
                    //u = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    //u.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    @Override
    public void getData(String s, String status, int type, String temp) {
        usersList.clear();
        usersList = new ArrayList<>();
        try {
            usersList = new ArrayList<>();
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                TaskUser user = new TaskUser();
                user.setFirstName(obj.getString(ProjectVariables.FNAME));
                user.setUid(obj.getString(ProjectVariables.UID));
                usersList.add(user);
            }
            String[] us = new String[usersList.size()];
            for (int i = 0; i < usersList.size(); i++) {
                us[i] = usersList.get(i).getFirstName();
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            users_Spinner.setAdapter(spinnerArrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class UploadTasks extends AsyncTask<Void, Void, String> {
        File f;
        String Image;
        Uri fileUri;

        public UploadTasks(File file, String imageName, Uri u) {
            f = file;
            fileUri = u;
            Image = imageName;
        }

        @Override
        protected String doInBackground(Void... voids) {
            //uploadFile(f);
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect(ProjectVariables.FTP_HOST, ProjectVariables.FTP_USER, ProjectVariables.FTP_PASS);
                boolean status = false;
                //status = ftp.setWorkingDirectory("/makeindiakart.com/taskfiles");
                status = ftp.setWorkingDirectory(ProjectVariables.IMAGE_FOLD);
                //InputStream targetStream = getResources().openRawResource(+R.drawable.ic_launcher);
                if (fileUri == null) {
                    InputStream targetStream = new FileInputStream(f);
                    ftp.uploadFile(targetStream, Image);
                } else {
                    InputStream stream = getActivity().getContentResolver().openInputStream(fileUri);
                    ftp.uploadFile(stream, Image);
                }
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
            pdForVideoUpload = new ProgressDialog(getActivity());
            pdForVideoUpload.setMessage("Uploading......");
            pdForVideoUpload.setCanceledOnTouchOutside(false);
            pdForVideoUpload.show();
        }

        @Override
        protected void onPostExecute(String s) {
            pdForVideoUpload.dismiss();
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }
}
