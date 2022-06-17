package com.irfan.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.irfan.storyapp.CoroutineRule
import com.irfan.storyapp.DummyData
import com.irfan.storyapp.PagedTestDataSources
import com.irfan.storyapp.PagerUtils.noopListUpdateCallback
import com.irfan.storyapp.adapter.StoryAdapter
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private val dummyToken = "Bearer Token"

    @Test
    fun `when Get Story Should Not Null`() = runTest {
        val dummyStory = DummyData.generateDummyStoryResponse()
        val data = PagedTestDataSources.snapshot(dummyStory)
        val story = MutableLiveData<PagingData<ListStoryItem>>()
        story.value = data

        `when`(storyRepository.getStories(dummyToken)).thenReturn(story)

        val actualStory = storyRepository.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = coroutineRule.Dispatcher,
            workerDispatcher = coroutineRule.Dispatcher
        )
        differ.submitData(actualStory)

        advanceUntilIdle()

        Mockito.verify(storyRepository).getStories(dummyToken)
        assertThat(differ.snapshot()).isNotNull()
        assertThat(dummyStory.size).isEqualTo(differ.snapshot().size)
        assertThat(dummyStory[0].id).isEqualTo(differ.snapshot()[0]?.id)
    }

    @Test
    fun `when Get Story with Location Success Should Not Null`() = runTest {
        val dummyResponse = DummyData.generateDummyGetStoryResponse()

        `when`(storyRepository.getStoryMap(dummyToken)).thenReturn(dummyResponse)

        val actualResponse = storyRepository.getStoryMap(dummyToken)

        Mockito.verify(storyRepository).getStoryMap(dummyToken)
        assertThat(actualResponse).isNotNull()
    }
}

