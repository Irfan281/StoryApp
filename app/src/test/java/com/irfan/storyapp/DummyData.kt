package com.irfan.storyapp

import com.irfan.storyapp.data.api.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DummyData {
    val dummyName = "irfan"
    val dummyCorrectEmail = "irfanstorytest@gmail.com"
    val dummyFalseEmail = "irfanstorytest"
    val dummyPassword = "tes12345"

    fun generateSuccessDummyDaftarResponse(): DaftarResponse {
        return DaftarResponse(
            error = false,
            message = "User Created"
        )
    }

    fun generateErrorDummyDaftarResponse(): DaftarResponse {
        return DaftarResponse(
            error = true,
            message = "Bad Request"
        )
    }

    fun generateSuccessDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            loginResult = LoginResult(
                userId = "user-CFO1DEURej5XHNb_",
                name = "rafatar",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUNGTzFERVVSZWo1WEhOYl8iLCJpYXQiOjE2NTA4MjYxNjF9.2EqQNTvR7biamg17v7TFm8K-i6UZr8OC8ecpCdRc--Y"
            ),
            error = false,
            message = "success"
        )
    }

    fun generateErrorDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            loginResult = null,
            error = true,
            message = "Invalid Password"
        )
    }

    fun generateSuccessDummyUploadResponse(): UploadResponse {
        return UploadResponse(
            error = false,
            message = "success"
        )
    }

    fun generateErrorDummyUploadResponse(): UploadResponse {
        return UploadResponse(
            error = true,
            message = "description is not allowed to be empty"
        )
    }

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                id = i.toString(),
                name = "name $i",
                description = "desc $i",
                photoUrl = "url $i",
                createdAt = "date $i",
                lat = i.toDouble(),
                lon = i.toDouble()
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyGetStoryResponse(): GetResponse {
        return GetResponse(
            listStory = generateDummyStoryResponse(),
            error = false,
            message = "success"
        )
    }

    fun generateDummyMultipart(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }

    fun generateDummyRequest(): RequestBody {
        val dummyText = "text"
        return dummyText.toRequestBody()
    }
}