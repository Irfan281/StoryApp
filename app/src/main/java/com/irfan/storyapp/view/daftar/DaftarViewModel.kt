package com.irfan.storyapp.view.daftar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.storyapp.data.api.ConfigApi
import com.irfan.storyapp.data.api.DaftarResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarViewModel : ViewModel() {
    private val _responseStatus = MutableLiveData<String>()
    val responseStatus: LiveData<String> = _responseStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun postDaftar(name: String, email: String, password: String): DaftarResponse? {
        _isLoading.value = true
        _state.value = false

        val daftarResponse: DaftarResponse? = null

        val client = ConfigApi.getApiService().postDaftar(name, email, password)
        client.enqueue(object : Callback<DaftarResponse> {
            override fun onResponse(
                call: Call<DaftarResponse>,
                response: Response<DaftarResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _state.value = true
                    _responseStatus.value = response.body()?.message
                } else {
                    val jsonError = response.errorBody()?.string()?.let { JSONObject(it) }
                    _responseStatus.value = jsonError?.getString("message")
                }
            }

            override fun onFailure(call: Call<DaftarResponse>, t: Throwable) {
                _isLoading.value = false
                _responseStatus.value = t.message.toString()
            }
        })

        return daftarResponse
    }
}
