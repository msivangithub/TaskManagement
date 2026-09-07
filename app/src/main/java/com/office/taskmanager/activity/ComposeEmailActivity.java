package com.office.taskmanager.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.office.taskmanager.R;

public class ComposeEmailActivity extends AppCompatActivity {

    public static final String EMAIL = "name";
    EditText edtTo;
    EditText edtSubject;
    EditText edtComposeEmail;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTo = (EditText) findViewById(R.id.edtTo);
        edtSubject = (EditText) findViewById(R.id.edtSubject);
        edtComposeEmail = (EditText) findViewById(R.id.edtComposeEmail);
        email = getIntent().getStringExtra(EMAIL);
        edtTo.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.email_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_send1:
                composeEmail();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void composeEmail() {
        String[] addresses = {email.toString()};
        String subject = edtSubject.getText().toString();
        String emailCompose = edtComposeEmail.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));   // only Email apps will handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailCompose);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
