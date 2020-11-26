package com.livehappyapps.githubviewer.ui.settings

import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.SettingsKey


class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val orgPreference: EditTextPreference? = findPreference(SettingsKey.ORGANIZATION)
        orgPreference?.summaryProvider =
            Preference.SummaryProvider<EditTextPreference> { preference ->
                val text = preference.text
                if (TextUtils.isEmpty(text)) {
                    getString(R.string.github_org_to_display)
                } else {
                    getString(R.string.current_org_set_as_x, text)
                }
            }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}