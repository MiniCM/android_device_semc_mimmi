package com.cyanogenmod.settings.device;

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


public class DeviceSettings extends PreferenceActivity implements OnPreferenceChangeListener
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
    
    private static final String KEYBOARD_LAYOUT = "keyboard_layout";
    private static final String KEYBOARD_KEYPRINT = "keyboard_keyprint";
    
    private static final String KEYBOARD_MODE = "keyboard_mode";
    private static final String KEYBOARD_MODE_PERSIST_PROP = "persist.sys.keyboardmode";
    private static final String KEYBOARD_MODE_DEFAULT = "user_als";
    
    private ListPreference mBrightnessMode;
    private ListPreference mBrightnessRateUp;
    private ListPreference mBrightnessRateDown;
    private ListPreference mBrightnessAvgTime;
    private ListPreference mKeyboardLayout;
    private ListPreference mKeyboardKeyprint;
    private ListPreference mKeyboardMode;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.mimmiparts);
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
        
        
            mKeyboardLayout = (ListPreference) prefSet.findPreference(KEYBOARD_LAYOUT);
            mKeyboardLayout.setOnPreferenceChangeListener(this);
        
            mKeyboardKeyprint = (ListPreference) prefSet.findPreference(KEYBOARD_KEYPRINT);
            mKeyboardKeyprint.setOnPreferenceChangeListener(this);
        
            mKeyboardMode = (ListPreference) prefSet.findPreference(KEYBOARD_MODE);
            mKeyboardMode.setValue(SystemProperties.get(KEYBOARD_MODE_PERSIST_PROP,
        		SystemProperties.get(KEYBOARD_MODE_PERSIST_PROP, KEYBOARD_MODE_DEFAULT)));
            mKeyboardMode.setOnPreferenceChangeListener(this);
                    
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
        
        if (preference == mKeyboardLayout){
            final String newLayout = (String) newValue;
                    
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.warning);
            builder.setMessage(R.string.caution_keyboard);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    setNewKeyboardLayout(newLayout);
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            builder.create();

            builder.show();
            return true;
        }
        
        if (preference == mKeyboardKeyprint){
            final String newLayout = (String) newValue;
                    
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.warning);
            builder.setMessage(R.string.caution_keyboard);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    setNewKeyboardKeyprint(newLayout);
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            builder.create();

            builder.show();
            return true;
        }
        
        if (preference == mKeyboardMode){
        	if (newValue != null) {
        		SystemProperties.set(KEYBOARD_MODE_PERSIST_PROP, (String)newValue);
        		writeOneLine("/sys/devices/platform/msm_pmic_misc_led.0/control::mode", (String)newValue);
        		return true;
        	}
        }

        return false;
    }
    
    private void setNewKeyboardLayout(String layout){
        // access app assets, and assign the correct file absolute paths
        AssetsManagment assetManager = new AssetsManagment(DeviceSettings.this);
        assetManager.unzipAssets();
        String filesDir = getFilesDir().getAbsolutePath();
        String keychar1 = filesDir + "/"+layout+".kcm";
        String keylayout1 = filesDir + "/"+layout+".kl";
        String id1 = filesDir + "/mimmi_keypad.idc";
        //ask for root permissions
        if (ShellInterface.isSuAvailable()){
            ShellInterface.runCommand("mount -o rw,remount /system");
            StringBuilder commandsList = new StringBuilder();

            commandsList.append("cp " +keychar1+ " /system/usr/keychars/mimmi_keypad.kcm ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/mimmi_keypad.kcm ; ");
            commandsList.append("chmod 644 /system/usr/keychars/mimmi_keypad.kcm ; ");

            commandsList.append("cp "+keychar1+" /system/usr/keychars/qwerty.kcm ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/qwerty.kcm ; ");
            commandsList.append("chmod 664 /system/usr/keychars/qwerty.kcm ; ");

            commandsList.append("cp "+keylayout1+" /system/usr/keylayout/mimmi_keypad.kl ; ");
            commandsList.append("chown 0:0 /system/usr/keylayout/mimmi_keypad.kl ; ");
            commandsList.append("chmod 664 /system/usr/keylayout/mimmi_keypad.kl ; ");

            commandsList.append("cp "+keylayout1+" /system/usr/keylayout/qwerty.kl ; ");
            commandsList.append("chown 0:0 /system/usr/keylayout/qwerty.kl ; ");
            commandsList.append("chmod 664 /system/usr/keylayout/qwerty.kl ; ");
            
            commandsList.append("cp "+id1+" /system/usr/idc/mimmi_keypad.idc ; ");
            commandsList.append("chown 0:0 /system/usr/idc/mimmi_keypad.idc ; ");
            commandsList.append("chmod 664 /system/usr/idc/mimmi_keypad.idc ; ");

            commandsList.append("sync ; ");
            commandsList.append("reboot ; ");
            
            try {
                ShellInterface.runSuCommand(DeviceSettings.this,commandsList.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void setNewKeyboardKeyprint(String keyprint){
        AssetsManagment assetManager = new AssetsManagment(DeviceSettings.this);
        assetManager.unzipAssets();
        String filesDir = getFilesDir().getAbsolutePath();
        String keyprint1 = filesDir + "/"+keyprint+".xml";
        if (ShellInterface.isSuAvailable()){
            ShellInterface.runCommand("mount -o rw,remount /system");
            StringBuilder commandsList = new StringBuilder();
           
            commandsList.append("cp "+keyprint1+" /system/usr/keyboard-config/keyprint.xml ; ");
            commandsList.append("chown 0:0 /system/usr/keyboard-config/keyprint.xml ; ");
            commandsList.append("chmod 664 /system/usr/keyboard-config/keyprint.xml ; ");

            commandsList.append("sync ; ");

            try{
                ShellInterface.runSuCommand(DeviceSettings.this,commandsList.toString());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
