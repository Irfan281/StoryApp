package com.irfan.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.irfan.storyapp.data.api.GetResponse
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.data.api.ServiceApi

class StoryRepository(private val serviceApi: ServiceApi) {
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(serviceApi, "Bearer $token")
            }
        ).liveData
    }

    suspend fun getStoryMap(token: String): GetResponse =
        serviceApi.getStories("Bearer $token", 1, 30, 1)
}