package com.irfan.storyapp.view.home

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
import com.irfan.storyapp.data.datastore.SessionModel
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
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    @Mock
    private lateinit var homeViewModel: HomeViewModel

    private val dummyToken = "Bearer Token"

    @Test
    fun `when get User Token that not null, should return Token`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = SessionModel(dummyToken)

        `when`(homeViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = homeViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(homeViewModel).getUserToken()
        assertThat(actualToken).isNotNull()
    }

    @Test
    fun `when get User Token that null`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = null

        `when`(homeViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = homeViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(homeViewModel).getUserToken()
        assertThat(actualToken).isNull()
    }

    @Test
    fun `when Get Story Should Not Null`() = runTest {
        val dummyStory = DummyData.generateDummyStoryResponse()
        val data = PagedTestDataSources.snapshot(dummyStory)
        val story = MutableLiveData<PagingData<ListStoryItem>>()
        story.value = data

        `when`(homeViewModel.getStories(dummyToken)).thenReturn(story)

        val actualStory = homeViewModel.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = coroutineRule.Dispatcher,
            workerDispatcher = coroutineRule.Dispatcher
        )
        differ.submitData(actualStory)

        advanceUntilIdle()

        Mockito.verify(homeViewModel).getStories(dummyToken)
        assertThat(differ.snapshot()).isNotNull()
        assertThat(dummyStory.size).isEqualTo(differ.snapshot().size)
        assertThat(dummyStory[0].id).isEqualTo(differ.snapshot()[0]?.id)
    }
}