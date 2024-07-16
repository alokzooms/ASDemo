package com.maxlabs.asdemo.data.model.remote

import android.util.Log
import com.maxlabs.asdemo.data.api.InspectionApiService
import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.model.InspectionModel
import retrofit2.Response

class RemoteDataSourceImpl(private val inspectionApiService: InspectionApiService): RemoteDataSource {
    override suspend fun login(authenticationModel: AuthenticationModel): Response<Void> {

        val loginResponse = inspectionApiService.loginUser(authenticationModel)
        if(loginResponse.code() == 401 ){
            val response = inspectionApiService.registerUser(authenticationModel)
            Log.d("RemoteDataSourceImpl", "User is registered successfully: ${response.code()} ")
            return inspectionApiService.loginUser(authenticationModel)
        } else return loginResponse
    }

    override suspend fun getInspection(): Response<InspectionModel> {
        return inspectionApiService.getInspection()
    }

    override suspend fun submitInspect(inspectionModel: InspectionModel): Response<Void> {
        return inspectionApiService.submitInspect(inspectionModel)
    }


}