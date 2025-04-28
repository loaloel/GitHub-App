package com.aloel.dev.github.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.aloel.dev.github.core.models.BaseList
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.core.networks.Resource
import com.aloel.dev.github.core.networks.Services
import com.aloel.dev.github.features.home.HomeRepository
import com.aloel.dev.github.features.home.HomeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Alul on 28/04/2025.
 * Senior Android Developer
 */

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var homeRepository: HomeRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var mockWebServer: MockWebServer
    private lateinit var services: Services

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        services = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Mock server URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Services::class.java)

        homeRepository = HomeRepository(services)
        viewModel = HomeViewModel(homeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Dispatcher to Default
        mockWebServer.shutdown()
    }

    @Test
    fun `when Search success then state is Success`() = runTest {
        // Given
        val fakeUsers = BaseList(arrayListOf(User(id = 1, name = "Fakhruddin")))
        val jsonResponse = Gson().toJson(fakeUsers)
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // When
        viewModel.doSearch("fakh")

        // Then
        withContext(Dispatchers.Default) {
            viewModel.search.test {
                assert(awaitItem() is Resource.Loading)
                assert(awaitItem() is Resource.Success)
            }
        }
    }

    @Test
    fun `when Search failed then state is Error`() = runTest {
        // Given
        mockWebServer.enqueue(MockResponse().setBody("").setResponseCode(400))

        // When
        viewModel.doSearch("fakh")

        // Then
        withContext(Dispatchers.Default) {
            viewModel.search.test {
                assert(awaitItem() is Resource.Loading)
                assert(awaitItem() is Resource.Error)
            }
        }
    }
}