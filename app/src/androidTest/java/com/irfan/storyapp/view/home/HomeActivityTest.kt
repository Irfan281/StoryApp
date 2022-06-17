package com.irfan.storyapp.view.home

import androidx.paging.ExperimentalPagingApi
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.irfan.storyapp.JsonReader
import com.irfan.storyapp.R
import com.irfan.storyapp.data.api.ConfigApi.Companion.BASE_URL
import com.irfan.storyapp.utils.EspressoIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalPagingApi
@MediumTest
class HomeActivityTest{

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun getStory_Success_Notempty(){
        ActivityScenario.launch(HomeActivity::class.java)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonReader.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_stories)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_upload)).check(matches(isDisplayed()))
        onView(withText("Aditasha")).check(matches(isDisplayed()))
    }

    @Test
    fun getStory_Success_Empty(){
        ActivityScenario.launch(HomeActivity::class.java)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonReader.readStringFromFile("empty_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.img_empty)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_empty)).check(matches(isDisplayed()))
    }

    @Test
    fun getStory_Error(){
        ActivityScenario.launch(HomeActivity::class.java)

        val mockResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.img_empty)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_empty)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}