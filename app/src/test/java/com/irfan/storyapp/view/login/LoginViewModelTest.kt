package com.irfan.storyapp.view.login

import com.google.common.truth.Truth.assertThat
import com.irfan.storyapp.DummyData
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @Mock
    private lateinit var loginViewModel: LoginViewModel
    private val dummy = DummyData
    private val dummySuccessResponse = DummyData.generateSuccessDummyLoginResponse()
    private val dummyErrorResponse = DummyData.generateErrorDummyLoginResponse()

    @Test
    fun `when Login Success, Response Should Not Null and Return Error=false`() {
        `when`(loginViewModel.postLogin(dummy.dummyCorrectEmail, dummy.dummyPassword)).thenReturn(
            dummySuccessResponse
        )

        val actualResponse = loginViewModel.postLogin(dummy.dummyCorrectEmail, dummy.dummyPassword)

        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse).isEqualTo(dummySuccessResponse)
    }

    @Test
    fun `when Login Error, Response Should Return Error=true`() {
        `when`(loginViewModel.postLogin(dummy.dummyFalseEmail, dummy.dummyPassword)).thenReturn(
            dummyErrorResponse
        )

        val actualResponse = loginViewModel.postLogin(dummy.dummyFalseEmail, dummy.dummyPassword)

        assertThat(actualResponse).isEqualTo(dummyErrorResponse)
    }
}