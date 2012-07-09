package com.cyanogenmod.MimmiParts;

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
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;


public class MimmiKeyboard extends PreferenceActivity implements OnPreferenceChangeListener
{
    private static final String KEYBOARD_LAYOUT = "keyboard_layout";
    private static final String KEYBOARD_KEYPRINT = "keyboard_keyprint";
    
    private static final String KEYBOARD_MODE = "keyboard_mode";
    private static final String KEYBOARD_MODE_PERSIST_PROP = "persist.sys.keyboardmode";
    private static final String KEYBOARD_MODE_DEFAULT = "user_als";

    private ListPreference mKeyboardLayout;
    private ListPreference mKeyboardKeyprint;
    private ListPreference mKeyboardMode;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.keyboard);
        PreferenceScreen prefSet = getPreferenceScreen();

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
        AssetsManagment assetManager = new AssetsManagment(MimmiKeyboard.this);
        assetManager.unzipAssets();
        String filesDir = getFilesDir().getAbsolutePath();
        String keychar1 = filesDir + "/"+layout+".bin";
        String keylayout1 = filesDir + "/"+layout+".kl";
        //ask for root permissions
        if (ShellInterface.isSuAvailable()){
            ShellInterface.runCommand("mount -o rw,remount /system");
            StringBuilder commandsList = new StringBuilder();

            commandsList.append("cp " +keychar1+ " /system/usr/keychars/mimmi_keypad.kcm.bin ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/mimmi_keypad.kcm.bin ; ");
            commandsList.append("chmod 644 /system/usr/keychars/mimmi_keypad.kcm.bin ; ");

            commandsList.append("cp "+keychar1+" /system/usr/keychars/qwerty.kcm.bin ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/qwerty.kcm.bin ; ");
            commandsList.append("chmod 664 /system/usr/keychars/qwerty.kcm.bin ; ");

            commandsList.append("cp "+keylayout1+" /system/usr/keylayout/mimmi_keypad.kl ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/mimmi_keypad.kl ; ");
            commandsList.append("chmod 664 /system/usr/keychars/mimmi_keypad.kl ; ");

            commandsList.append("cp "+keylayout1+" /system/usr/keylayout/qwerty.kl ; ");
            commandsList.append("chown 0:0 /system/usr/keychars/qwerty.kl ; ");
            commandsList.append("chmod 664 /system/usr/keychars/qwerty.kl ; ");

            commandsList.append("sync ; ");
            commandsList.append("reboot ; ");
            
            try {
                ShellInterface.runSuCommand(MimmiKeyboard.this,commandsList.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void setNewKeyboardKeyprint(String keyprint){
        AssetsManagment assetManager = new AssetsManagment(MimmiKeyboard.this);
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
                ShellInterface.runSuCommand(MimmiKeyboard.this,commandsList.toString());
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
