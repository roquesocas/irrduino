package com.joefernandez.irrduino.android.remote;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.util.Log;

public class Settings extends PreferenceActivity {

	private static final String TAG = "Settings";
	
	public static final String DEFAULT_CONTROLLER_HOST = "192.168.1.110";
	public static final String DEFAULT_CONTROLLER_PORT = "80";
	
	public static final String SETTINGS_CATEGORY_CONTROLLER = "controller_settings_category";
	public static final String CONTROLLER_HOST_NAME = "controller_host_name";
	public static final String CONTROLLER_HOST_PORT = "controller_host_port";
	
	public static final String SETTINGS_CATEGORY_ABOUT = "about_settings_category";
	public static final String ABOUT_VERSION = "about_version";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
        
        // Load and display version settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        PreferenceCategory controller = (PreferenceCategory) findPreference(SETTINGS_CATEGORY_CONTROLLER);
        if (controller != null){
            Preference controllerHostName = controller.findPreference(CONTROLLER_HOST_NAME);
            if (controllerHostName != null && prefs.contains(CONTROLLER_HOST_NAME)){
            	controllerHostName.setSummary(prefs.getString(
            			CONTROLLER_HOST_NAME, DEFAULT_CONTROLLER_HOST));
            }
            Preference controllerHostPort = controller.findPreference(CONTROLLER_HOST_PORT);
            if (controllerHostPort != null&& prefs.contains(CONTROLLER_HOST_PORT)){
            	controllerHostPort.setSummary(prefs.getString(
            			CONTROLLER_HOST_PORT, DEFAULT_CONTROLLER_PORT));
            }
        }
        
        PreferenceCategory about = (PreferenceCategory) findPreference(SETTINGS_CATEGORY_ABOUT);
        if (about != null){
            Preference version = about.findPreference(ABOUT_VERSION);
            if (version != null){
                version.setSummary(getVersionName());
            }
        }
    }
    
    private String getVersionName() {
        String versionName = "";
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            Log.d(TAG, "Error retrieving app version info: " + e.getMessage());
            return versionName;
        }
        return versionName;
    }
    
}