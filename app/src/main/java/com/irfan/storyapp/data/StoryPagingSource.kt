package com.irfan.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.data.api.ServiceApi
import com.irfan.storyapp.utils.wrapEspressoIdlingResource

class StoryPagingSource(private val apiService: ServiceApi, private val token: String) :
    PagingSource<Int, ListStoryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        wrapEspressoIdlingResource {
            return try {
                val position = params.key ?: INITIAL_PAGE_INDEX
                val responseData = apiService.getStories(token, position, params.loadSize, 0)
                LoadResult.Page(
                    data = responseData.listStory,
                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                    nextKey = if (responseData.listStory.isNullOrEmpty()) null else position + 1
                )
            } catch (exception: Exception) {
                return LoadResult.Error(exception)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}