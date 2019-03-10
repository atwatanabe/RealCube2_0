package com.example.anthony.realcube2_0;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends Activity
{
    private String[] settingsList = {"Cube Orienation Sensitivity", "test 1", "test 2"};

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_settings_listview, settingsList);
        ListView listView = (ListView) findViewById(R.id.settingsList);
        listView.setAdapter(adapter);
    }
}
