package com.task.mytaskmanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.task.mytaskmanager.R;

import java.util.ArrayList;

public class TaskDetails extends AppCompatActivity implements View.OnClickListener{
    ArrayList<Tasks> mytasks;
    Spinner status;
    TextView statusdisplay;
     Tasks tasks;



    String [] employee=new String[]{"MANJUNATH","PRAKASH","VENKATESH","KRISHNA","VENUGOPAL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        mytasks=TaskList();


        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        Spinner employeename=(Spinner)findViewById(R.id.id_employeenames);
        status=(Spinner)findViewById(R.id.id_status);


        TasksAdapter statusAdapter=new TasksAdapter(this,android.R.layout.simple_spinner_item,mytasks);
        status.setAdapter(statusAdapter);



        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,employee);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeename.setAdapter(adapter);
        employeename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TaskDetails.this,employee[position],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public ArrayList<Tasks> TaskList(){
        ArrayList<Tasks> taskses=new ArrayList<Tasks>();
        taskses.add(new Tasks("All"));
        taskses.add(new Tasks("Completed"));
        taskses.add(new Tasks("Pending"));
        taskses.add(new Tasks("Progress"));
        return taskses;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_show:
                break;
            case R.id.id_edit:
                break;
            case R.id.id_remind:
                break;
            case R.id.id_delete:
                break;
            case R.id.id_smsalert:
                break;
            case R.id.id_score:
                break;
            case R.id.id_status:


                break;
        }
    }
}
