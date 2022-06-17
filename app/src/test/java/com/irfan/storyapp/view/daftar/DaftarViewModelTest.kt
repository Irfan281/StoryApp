package com.irfan.storyapp.view.daftar

import com.google.common.truth.Truth.assertThat
import com.irfan.storyapp.DummyData
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DaftarViewModelTest {
    @Mock
    private lateinit var daftarViewModel: DaftarViewModel
    private val dummy = DummyData
    private val dummySuccessResponse = DummyData.generateSuccessDummyDaftarResponse()
    private val dummyErrorResponse = DummyData.generateErrorDummyDaftarResponse()

    @Test
    fun `when Register Success, Response Should Not Null and Return Error=false`() {
        `when`(
            daftarViewModel.postDaftar(
                dummy.dummyName,
                dummy.dummyCorrectEmail,
                dummy.dummyPassword
            )
        ).thenReturn(dummySuccessResponse)

        val actualResponse = daftarViewModel.postDaftar(
            dummy.dummyName,
            dummy.dummyCorrectEmail,
            dummy.dummyPassword
        )

        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse).isEqualTo(dummySuccessResponse)
    }

    @Test
    fun `when Register Error, Response Should Return Error=true`() {
        `when`(
            daftarViewModel.postDaftar(
                dummy.dummyName,
                dummy.dummyFalseEmail,
                dummy.dummyPassword
            )
        ).thenReturn(dummyErrorResponse)

        val actualResponse =
            daftarViewModel.postDaftar(dummy.dummyName, dummy.dummyFalseEmail, dummy.dummyPassword)

        assertThat(actualResponse).isEqualTo(dummyErrorResponse)
    }
}