package com.example.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.repo.githubapi.GithubApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class GithubApiTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: GithubApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getTrendingRepos() {
        runBlocking {
            enqueueResponse("trending-repo-response.json")
            val resultResponse = service.getTrendingRepos(sortByData).body()

            /*check request type and it's end point*/
            val request = mockWebServer.takeRequest()
            assertThat(request.path, `is`("/repositories"))

            /* assert response */
            assertNotNull(resultResponse)
            assertThat(resultResponse!!.size, `is`(25))

            val trendingRepo = resultResponse[0]

            assertThat(trendingRepo.username, `is`("frenck"))
            assertThat(trendingRepo.name, `is`("Franck Nijhof"))
            assertThat(trendingRepo.type, `is`("user"))
            assertThat(trendingRepo.url,
                `is`("https://github.com/frenck")
            )
            assertThat(trendingRepo.avatar,
                `is`("https://avatars2.githubusercontent.com/u/195327")
            )
            assertThat(trendingRepo.repo.name, `is`("awesome-home-assistant"))
            assertThat(trendingRepo.repo.description, `is`("A curated list of amazingly awesome Home Assistant resources."))
            assertThat(trendingRepo.repo.url, `is`("https://github.com/frenck/awesome-home-assistant"))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
            source.readString(Charsets.UTF_8))
        )
    }
}