package com.irfan.storyapp.di

import com.irfan.storyapp.data.StoryRepository
import com.irfan.storyapp.data.api.ConfigApi

object Injection {
    fun setRepository(): StoryRepository {
        val serviceApi = ConfigApi.getApiService()
        return StoryRepository(serviceApi)
    }
}