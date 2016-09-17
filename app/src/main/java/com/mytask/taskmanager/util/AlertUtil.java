package com.mytask.taskmanager.util;

/**
 * Created by NEWSYSTEM1 on 5/27/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import com.mytask.taskmanager.R;


/**
 * Created by prasani on 3/24/2016.
 */
public class AlertUtil {
    public static void displayExitDialog(final Activity context) {

        // TODO Auto-generated method stub
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(context.getResources().getString(R.string.app_name));

        // set dialog message
        alertDialog.setMessage("Are You Sure Want To Exit?");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        context.finish();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).setIcon(R.drawable.my_taskmanager96);
        AlertDialog alert = alertDialog.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);

        nbutton.setTextColor(context.getResources().getColor(
                R.color.black));
        nbutton.setTextSize(15);

        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        pbutton.setTextColor(context.getResources().getColor(
                R.color.black));
        pbutton.setTextSize(15);

    }
}
