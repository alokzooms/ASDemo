package com.maxlabs.asdemo.data.model.remote

import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.model.InspectionModel
import retrofit2.Response

interface RemoteDataSource {
    suspend fun login(authenticationModel: AuthenticationModel): Response<Void>
    suspend fun getInspection(): Response<InspectionModel>
    suspend fun submitInspect(inspectionModel: InspectionModel): Response<Void>
}