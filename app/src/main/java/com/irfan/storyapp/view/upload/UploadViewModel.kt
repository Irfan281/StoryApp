package com.irfan.storyapp.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.storyapp.data.api.ConfigApi
import com.irfan.storyapp.data.api.UploadResponse
import com.irfan.storyapp.data.datastore.SessionModel
import com.irfan.storyapp.data.datastore.SessionPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadViewModel(private val pref: SessionPreferences) : ViewModel() {
    private val _responseStatus = MutableLiveData<String>()
    val responseStatus: LiveData<String> = _responseStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun getUserToken(): LiveData<SessionModel> {
        return pref.getToken().asLiveData()
    }

    fun uploadStory(
        token: String,
        image: MultipartBody.Part,
        desc: RequestBody,
        lat: Float? = null,
        lon: Float? = null
    ): UploadResponse? {
        _isLoading.value = true
        _state.value = false

        val uploadResponse: UploadResponse? = null

        val client = ConfigApi.getApiService().postStories("Bearer $token", image, desc, lat, lon)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _state.value = true

                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _responseStatus.value = responseBody.message
                    }
                } else {
                    val jsonError = response.errorBody()?.string()?.let { JSONObject(it) }
                    _responseStatus.value = jsonError?.getString("message")
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                _isLoading.value = false
                _responseStatus.value = t.message
            }
        })
        return uploadResponse
    }
}