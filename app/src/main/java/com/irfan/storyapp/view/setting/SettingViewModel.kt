package com.irfan.storyapp.view.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.storyapp.data.datastore.SessionPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SessionPreferences) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}