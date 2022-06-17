package com.irfan.storyapp.view.setting

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SettingViewModelTest {
    @Mock
    private lateinit var settingViewModel: SettingViewModel

    @Test
    fun `run and check logout`() = runTest {
        settingViewModel.logout()
        launch {
            Mockito.verify(settingViewModel).logout()
        }
    }

}