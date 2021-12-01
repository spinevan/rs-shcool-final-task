package ru.sinitsyndev.rs_shcool_final_task

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import ru.sinitsyndev.rs_shcool_final_task.utils.USE_NIGHT_THEME_DEFAULT
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        prefs.registerOnSharedPreferenceChangeListener(this)

        if (savedInstanceState == null) {
            onSharedPreferenceChanged(prefs, getString(R.string.use_night_theme))
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
            if (prefValue == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
