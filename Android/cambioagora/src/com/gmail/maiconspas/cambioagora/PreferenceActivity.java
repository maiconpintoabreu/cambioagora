package com.gmail.maiconspas.cambioagora;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class PreferenceActivity extends SherlockPreferenceActivity {
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        Preference key_currenty = findPreference("key_currenty");
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());  
		String listPreference = sp.getString("key_currenty", null);
		key_currenty.setTitle(listPreference);
        key_currenty.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setTitle(newValue.toString());
				return true;
			}
		});
    }

}
