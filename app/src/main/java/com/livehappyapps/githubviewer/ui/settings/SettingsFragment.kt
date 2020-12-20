package com.livehappyapps.githubviewer.ui.settings

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.SettingsKey
import com.livehappyapps.githubviewer.databinding.FragmentPreferencesBinding


class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var binding: FragmentPreferencesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPreferencesBinding.bind(view)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

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

}