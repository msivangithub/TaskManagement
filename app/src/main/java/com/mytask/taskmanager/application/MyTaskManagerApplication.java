package com.mytask.taskmanager.application;

import android.app.Application;
import android.content.Context;


import com.crashlytics.android.Crashlytics;
import com.mytask.taskmanager.BuildConfig;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.acra.ACRAReportSender;
import com.mytask.taskmanager.util.PropertyReader;

import io.fabric.sdk.android.Fabric;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import java.util.Properties;

/**
 * Created by NEWSYSTEM1 on 5/30/2016.
 */
@ReportsCrashes(
		formKey = ""
)
public class MyTaskManagerApplication extends Application {

	private static final String LOG_TAG = MyTaskManagerApplication.class.getSimpleName();
	private static Context context;
	private static Properties properties;

	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());
		setupACRA();
	}

	public static Context getContext() {
		return context;
	}

	/**
	 * Setup ACRA.
	 *
	 * @return void.
	 */
	private void setupACRA() {

		if (!BuildConfig.DEBUG) {
			ACRA.init(this);
			// instantiate the report sender with the email credentials.
			// these will be used to send the crash report
			PropertyReader propertyReader = new PropertyReader(this);
			properties = propertyReader.getMyProperties(R.raw.acra_credentials);
			ACRAReportSender reportSender = new ACRAReportSender(properties.get("AUTH_USER").toString(),
					properties.get("AUTH_PASSWORD").toString());
			// register it with ACRA
			ACRA.getErrorReporter().setReportSender(reportSender);
		}

	}

	/**
	 * Get Properties for User Credentials For ACRA.
	 *
	 * @return
	 */
	public static Properties getProperties() {
		return properties;
	}
}
