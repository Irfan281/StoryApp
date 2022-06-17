package com.irfan.storyapp.view.map

import android.content.Context
import androidx.lifecycle.*
import com.irfan.storyapp.data.StoryRepository
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.data.datastore.SessionModel
import com.irfan.storyapp.data.datastore.SessionPreferences
import com.irfan.storyapp.di.Injection
import kotlinx.coroutines.launch

class StoryMapViewModel(
    private val pref: SessionPreferences,
    private val storyRepository: StoryRepository
) : ViewModel() {
    private val _mapStory = MutableLiveData<List<ListStoryItem>>()
    var mapStory: LiveData<List<ListStoryItem>> = _mapStory

    fun getUserToken(): LiveData<SessionModel> = pref.getToken().asLiveData()

    fun getMapStory(tkn: String): LiveData<List<ListStoryItem>> {
        viewModelScope.launch {
            _mapStory.postValue(storyRepository.getStoryMap(tkn).listStory)
        }
        return _mapStory
    }
}

class StoryMapViewModelFactory(private val pref: SessionPreferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryMapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryMapViewModel(pref, Injection.setRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}