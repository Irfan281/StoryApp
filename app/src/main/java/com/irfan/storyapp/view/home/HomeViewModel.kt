package com.irfan.storyapp.view.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.irfan.storyapp.data.StoryRepository
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.data.datastore.SessionModel
import com.irfan.storyapp.data.datastore.SessionPreferences
import com.irfan.storyapp.di.Injection

class HomeViewModel(
    private val pref: SessionPreferences,
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun getUserToken(): LiveData<SessionModel> = pref.getToken().asLiveData()

    fun getStories(tkn: String): LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStories(tkn).cachedIn(viewModelScope)
}

internal class HomeViewModelFactory(
    private val pref: SessionPreferences,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(pref, Injection.setRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}