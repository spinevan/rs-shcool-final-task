package ru.sinitsyndev.rs_shcool_final_task.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.sinitsyndev.rs_shcool_final_task.R

class SettingsFragment: PreferenceFragmentCompat()  {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings, rootKey)
    }
}