package com.maxlabs.asdemo.com.maxlabs.asdemo.data.api

import com.google.common.truth.Truth.assertThat
import com.maxlabs.asdemo.data.api.InspectionApiService
import com.maxlabs.asdemo.model.AuthenticationModel
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InspectionApiServiceTest {

    private var server = MockWebServer()
    private lateinit var service:  InspectionApiService

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()


        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(InspectionApiService::class.java)
    }

    /*private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }*/

    @Test
    fun login_user_sentRequest_receivedExpected() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
        server.enqueue(mockResponse)
        val request = server.takeRequest()

            val response = runBlocking {
                service.loginUser(AuthenticationModel("a@g.com", "aaaaaaa"))
            }

            // Assert
            assertThat(response.code()).isEqualTo(200)
            assertThat(request.path).isEqualTo("/api/login")
    }

    @Test
    fun login_user_sentRequest_receivedUnExpected() {
        val mockResponse = MockResponse()
            .setResponseCode(400)
        server.enqueue(mockResponse)
        val request = server.takeRequest()

        val response = runBlocking {
            service.loginUser(AuthenticationModel("", ""))
        }

        // Assert
        assertThat(response.code()).isEqualTo(400)
    }

    @Test
    fun testLoginUserServerError() {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(500)
        server.enqueue(mockResponse)

        // Act
        val response = runBlocking {
            service.loginUser(AuthenticationModel("username", "password"))
        }

        // Assert
        assertThat(response.code()).isEqualTo(500)
    }

    @Test
    fun testGetInspectionSuccess() {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)

            .setBody(getBodyData())
        server.enqueue(mockResponse)

        // Act
        val response = runBlocking {
            service.getInspection()
        }

        // Assert
        assertThat(response.code()).isEqualTo(200)
        val inspectionModel = response.body()
        assertThat(inspectionModel).isNotNull()
        //assertEquals(1, inspection?.id)
        assertThat(inspectionModel?.inspection?.area?.name).isEqualTo("Emergency ICU")
    }

    private fun getBodyData(): String {
        return """
                {
                    "id": 1,
                    "area": {"id": 1, "name": "Emergency ICU"},
                    "inspectionType": {"id": 1, "access": "write", "name": "Clinical"},
                    "survey": {
                        "id": 1,
                        "categories": [
                            {
                                "id": 1,
                                "name": "Drugs",
                                "questions": [
                                    {
                                        "id": 1,
                                        "name": "Is the drugs trolley locked?",
                                        "selectedAnswerChoiceId": 0,
                                        "answerChoices": [
                                            {"id": 1, "name": "Yes", "score": 1.0},
                                            {"id": 2, "name": "No", "score": 0.0},
                                            {"id": -1, "name": "N/A", "score": 0.0}
                                        ]
                                    }
                                ]
                            }
                        ]
                    }
                }
                """.trimIndent()
    }

    @Test
    fun testGetInspectionServerError() {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(500)
        server.enqueue(mockResponse)

        // Act
        val response = runBlocking {
            service.getInspection()
        }

        // Assert
        assertThat(response.code()).isEqualTo(500)
    }





    @After
    fun tearDown() {
        server.shutdown()
    }
}