package com.maxlabs.asdemo.data.api

import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.model.InspectionModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InspectionApiService {

    @POST("/api/register")
    suspend fun registerUser(
        @Body
        authenticationModel: AuthenticationModel
    ): Response<Void>

    @POST("/api/login")
    suspend fun loginUser(
        @Body
        authenticationModel: AuthenticationModel
    ): Response<Void>

    @GET("/api/inspections/start")
    suspend fun getInspection(): Response<InspectionModel>

    @POST("/api/inspections/submit")
    suspend fun submitInspect(@Body request : InspectionModel) : Response<Void>
}