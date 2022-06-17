package com.irfan.storyapp.view.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth
import com.irfan.storyapp.data.datastore.SessionModel
import com.irfan.storyapp.getOrAwaitValue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var splashViewModel: SplashViewModel
    private val dummyToken = "Bearer Token"

    @Test
    fun `when get User Token that not null, should return Token`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = SessionModel(dummyToken)

        Mockito.`when`(splashViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = splashViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(splashViewModel).getUserToken()
        Truth.assertThat(actualToken).isNotNull()
    }

    @Test
    fun `when get User Token that null`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = null

        Mockito.`when`(splashViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = splashViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(splashViewModel).getUserToken()
        Truth.assertThat(actualToken).isNull()
    }
}