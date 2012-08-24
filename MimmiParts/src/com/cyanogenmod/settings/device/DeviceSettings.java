package com.cyanogenmod.settings.device;

/**
 * Author: Madbulls@xda
 */
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class DeviceSettings extends PreferenceActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.mimmiparts);
                  
    }

}
