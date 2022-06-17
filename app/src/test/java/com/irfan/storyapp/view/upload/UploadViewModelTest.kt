package com.irfan.storyapp.view.upload


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.irfan.storyapp.DummyData
import com.irfan.storyapp.data.datastore.SessionModel
import com.irfan.storyapp.getOrAwaitValue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UploadViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var uploadViewModel: UploadViewModel
    private val dummySuccessResponse = DummyData.generateSuccessDummyUploadResponse()
    private val dummyErrorResponse = DummyData.generateErrorDummyUploadResponse()
    private val multipart = DummyData.generateDummyMultipart()
    private val request = DummyData.generateDummyRequest()
    private val dummyToken = "Bearer Token"
    private val location: Float = 0.0F

    @Test
    fun `when Upload Success, Response Should Not Null and Return Error=false`() {
        `when`(
            uploadViewModel.uploadStory(
                dummyToken,
                multipart,
                request,
                location,
                location
            )
        ).thenReturn(dummySuccessResponse)

        val actualResponse =
            uploadViewModel.uploadStory(dummyToken, multipart, request, location, location)

        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse).isEqualTo(dummySuccessResponse)
    }

    @Test
    fun `when Upload Error, Response Should Return Error=true`() {
        `when`(
            uploadViewModel.uploadStory(
                dummyToken,
                multipart,
                request,
                location,
                location
            )
        ).thenReturn(dummyErrorResponse)

        val actualResponse =
            uploadViewModel.uploadStory(dummyToken, multipart, request, location, location)

        assertThat(actualResponse).isEqualTo(dummyErrorResponse)
    }

    @Test
    fun `when get User Token that not null, should return Token`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = SessionModel(dummyToken)

        `when`(uploadViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = uploadViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(uploadViewModel).getUserToken()
        assertThat(actualToken).isNotNull()
    }

    @Test
    fun `when get User Token that null`() {
        val expectedToken = MutableLiveData<SessionModel>()
        expectedToken.value = null

        `when`(uploadViewModel.getUserToken()).thenReturn(expectedToken)

        val actualToken = uploadViewModel.getUserToken().getOrAwaitValue()

        Mockito.verify(uploadViewModel).getUserToken()
        assertThat(actualToken).isNull()
    }
}