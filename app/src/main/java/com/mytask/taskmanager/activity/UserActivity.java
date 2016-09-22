package com.mytask.taskmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytask.taskmanager.Adaptes.AllUsersAdapter;
import com.mytask.taskmanager.R;
import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;

public class UserActivity extends AppCompatActivity {
    TextView textView;
    String name = "";
    String image = "";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.profileImage);
        textView = (TextView) findViewById(R.id.email);
        name = getIntent().getStringExtra(NAME);
        image = getIntent().getStringExtra(IMAGE);
        textView.setText(name);

        Picasso.with(this)
                .load("http://makeindiakart.com/taskfiles/" + image)
                .placeholder(R.drawable.imge_placeholder)   // optional
                .error(R.drawable.imge_placeholder)      // optional
                .resize(300, 300)
                .into(imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 65) {
                textView.setText(data.getStringExtra(AllUsersAdapter.NAME));
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
