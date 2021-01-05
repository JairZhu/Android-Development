package com.example.grandwordremember;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private EditTextPreference editTextPreference;
    private ListPreference listPreference;
    private CheckBoxPreference checkBoxPreference;

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        addPreferencesFromResource(R.xml.preferences);
        editTextPreference = (EditTextPreference) findPreference("edittext_key");
        listPreference = (ListPreference) findPreference("list_key");
        checkBoxPreference = (CheckBoxPreference) findPreference("checkbox_key");
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        listPreference.setSummary(sharedPreferences.getString("list_key", ""));
        editTextPreference.setSummary(sharedPreferences.getString("edittext_key", ""));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("edittext_key"))
            editTextPreference.setSummary(sharedPreferences.getString(key, "30"));
        else if (key.equals("list_key"))
            listPreference.setSummary(sharedPreferences.getString(key, ""));
    }
}
