package ru.sinitsyndev.rs_shcool_final_task

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG
import ru.sinitsyndev.rs_shcool_final_task.utils.USE_NIGHT_THEME_DEFAULT

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        //applicationContext.appComponent.inject(this)

        super.onCreate(savedInstanceState)

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        prefs.registerOnSharedPreferenceChangeListener(this)

        if (savedInstanceState == null) {
            onSharedPreferenceChanged(prefs ,getString(R.string.use_night_theme))
        }

        setContentView(R.layout.activity_main)
    }


    override fun onDestroy() {
        super.onDestroy()
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        prefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key === getString(R.string.use_night_theme)) {
            val prefValue = sharedPreferences?.getBoolean(key, USE_NIGHT_THEME_DEFAULT)
            //Log.d(LOG_TAG, "!!!!!!!!!!!!!!!------$key value = $prefValue")
            if (prefValue == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}