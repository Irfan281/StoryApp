package com.irfan.storyapp.view.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.irfan.storyapp.DummyData
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.data.datastore.SessionModel
import com.irfan.storyapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class StoryMapViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mapViewModel: StoryMapViewModel

    private val dummyToken = "Bearer Token"

    @Test
    fun `when get User Token that not null, should return Token`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = SessionModel(dummyToken)

        `when`(mapViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = mapViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(mapViewModel).getUserToken()
        assertThat(actualToken).isNotNull()
    }

    @Test
    fun `when get User Token that null`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = null

        `when`(mapViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = mapViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(mapViewModel).getUserToken()
        assertThat(actualToken).isNull()
    }

    @Test
    fun `when Get Story with location Should Not Null`() = runTest {
        val dummyStory = DummyData.generateDummyStoryResponse()
        val expectedStory = MutableLiveData<List<ListStoryItem>>()
        expectedStory.value = dummyStory

        val dummyToken = "Bearer Token"
        `when`(mapViewModel.getMapStory(dummyToken)).thenReturn(expectedStory)

        val actualStory = mapViewModel.getMapStory(dummyToken).getOrAwaitValue()

        Mockito.verify(mapViewModel).getMapStory(dummyToken)
        assertThat(actualStory).isNotNull()
        assertThat(dummyStory.size).isEqualTo(actualStory.size)
        assertThat(dummyStory[0].id).isEqualTo(actualStory[0].id)
    }


}