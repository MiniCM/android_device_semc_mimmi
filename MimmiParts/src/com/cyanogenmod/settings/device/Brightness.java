package com.cyanogenmod.settings.device;

/**
 * Author: Madbulls@xda
 */
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;


public class Brightness extends PreferenceActivity implements OnPreferenceChangeListener
{
        
    private static final String BRIGHTNESS_MODE = "brightness_mode";
    private static final String BRIGHTNESS_MODE_PERSIST_PROP = "persist.sys.brightnessmode";
    private static final String BRIGHTNESS_MODE_DEFAULT = "i2c_pwm_als";
    
    private static final String BRIGHTNESS_RATE_UP = "brightness_rate_up";
    private static final String BRIGHTNESS_RATE_UP_PERSIST_PROP = "persist.sys.brightnessrateup";
    private static final String BRIGHTNESS_RATE_UP_DEFAULT = "32768";
    
    private static final String BRIGHTNESS_RATE_DOWN = "brightness_rate_down";
    private static final String BRIGHTNESS_RATE_DOWN_PERSIST_PROP = "persist.sys.brightnessratedown";
    private static final String BRIGHTNESS_RATE_DOWN_DEFAULT = "32768";
    
    private static final String BRIGHTNESS_AVG_TIME = "brightness_avg_time";
    private static final String BRIGHTNESS_AVG_TIME_PERSIST_PROP = "persist.sys.brightnessavgtime";
    private static final String BRIGHTNESS_AVG_TIME_DEFAULT = "512";

    private ListPreference mBrightnessMode;
    private ListPreference mBrightnessRateUp;
    private ListPreference mBrightnessRateDown;
    private ListPreference mBrightnessAvgTime;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.brightness);
        PreferenceScreen prefSet = getPreferenceScreen();
               
        mBrightnessMode = (ListPreference) prefSet.findPreference(BRIGHTNESS_MODE);
        mBrightnessMode.setValue(SystemProperties.get(BRIGHTNESS_MODE_PERSIST_PROP,
        		SystemProperties.get(BRIGHTNESS_MODE_PERSIST_PROP, BRIGHTNESS_MODE_DEFAULT)));
        mBrightnessMode.setOnPreferenceChangeListener(this);
        
        mBrightnessRateUp = (ListPreference) prefSet.findPreference(BRIGHTNESS_RATE_UP);
        mBrightnessRateUp.setValue(SystemProperties.get(BRIGHTNESS_RATE_UP_PERSIST_PROP,
        		SystemProperties.get(BRIGHTNESS_RATE_UP_PERSIST_PROP, BRIGHTNESS_RATE_UP_DEFAULT)));
        mBrightnessRateUp.setOnPreferenceChangeListener(this);
        
        mBrightnessRateDown = (ListPreference) prefSet.findPreference(BRIGHTNESS_RATE_DOWN);
        mBrightnessRateDown.setValue(SystemProperties.get(BRIGHTNESS_RATE_DOWN_PERSIST_PROP,
        		SystemProperties.get(BRIGHTNESS_RATE_DOWN_PERSIST_PROP, BRIGHTNESS_RATE_DOWN_DEFAULT)));
        mBrightnessRateDown.setOnPreferenceChangeListener(this);
        
        mBrightnessAvgTime = (ListPreference) prefSet.findPreference(BRIGHTNESS_AVG_TIME);
        mBrightnessAvgTime.setValue(SystemProperties.get(BRIGHTNESS_AVG_TIME_PERSIST_PROP,
        		SystemProperties.get(BRIGHTNESS_AVG_TIME_PERSIST_PROP, BRIGHTNESS_AVG_TIME_DEFAULT)));
        mBrightnessAvgTime.setOnPreferenceChangeListener(this);
          
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
    	                 
         if (preference == mBrightnessMode){
        	if (newValue != null) {
        		SystemProperties.set(BRIGHTNESS_MODE_PERSIST_PROP, (String)newValue);
        		writeOneLine("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/mode", (String)newValue);
        		return true;
        	}
        }
        
        if (preference == mBrightnessRateUp){
        	if (newValue != null) {
        		SystemProperties.set(BRIGHTNESS_RATE_UP_PERSIST_PROP, (String)newValue);
        		writeOneLine("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/br::rate::up", (String)newValue);
        		return true;
        	}
        }
        
        if (preference == mBrightnessRateDown){
        	if (newValue != null) {
        		SystemProperties.set(BRIGHTNESS_RATE_DOWN_PERSIST_PROP, (String)newValue);
        		writeOneLine("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/br::rate::down", (String)newValue);
        		return true;
        	}
        }
        
        if (preference == mBrightnessAvgTime){
        	if (newValue != null) {
        		SystemProperties.set(BRIGHTNESS_AVG_TIME_PERSIST_PROP, (String)newValue);
        		writeOneLine("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/als::avg-t", (String)newValue);
        		return true;
        	}
        }
        
        return false;
    }
    
    public static boolean writeOneLine(String fname, String value) {
    	try {
    		FileWriter fw = new FileWriter(fname);
            try {
                fw.write(value);
            } finally {
                fw.close();
            }
        } catch (IOException e) {
            String Error = "Error writing to " + fname + ". Exception: ";
            Log.e(Constants.LOG_TAG, Error, e);
            return false;
        }
        return true;
    }
    
}
