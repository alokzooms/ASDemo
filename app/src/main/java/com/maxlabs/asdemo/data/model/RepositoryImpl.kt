package com.maxlabs.asdemo.data.model

import android.util.Log
import com.maxlabs.asdemo.data.Repository
import com.maxlabs.asdemo.data.model.local.LocalDataSource
import com.maxlabs.asdemo.data.model.remote.RemoteDataSource
import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.InspectionModel
import com.maxlabs.asdemo.util.Resource
import retrofit2.Response

class RepositoryImpl(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) : Repository{

    override suspend fun login(authenticationModel: AuthenticationModel): Resource<String> {
        val response = remoteDataSource.login(authenticationModel)
        return try {
            when (response.code()) {
                200 -> Resource.Success(response.code(),"Logged In Successfully")
                400 -> Resource.Error(response.code(),"email or password fields are missing")
                401 -> Resource.Error(response.code(),"Register first to login")
                else -> Resource.Error(response.code(),"Something went wrong, error code: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(response.code(),"An error occurred during the request: ${e.message}", null)
        }
    }

    override suspend fun getInspectionFromAPI(): Resource<Inspection> {
        val response = remoteDataSource.getInspection()
        localDataSource.saveInspection(response.body()!!.inspection)
        return responseToResource(response)
    }

    override suspend fun updateSelectedAnswerChoice(questionId: Int, selectedAnswerChoiceId: Int) {
        localDataSource.updateSelectedAnswerChoice(questionId, selectedAnswerChoiceId)
    }

    override suspend fun getInspectionFromDB(): Resource<Inspection> {
        val dataFromDB = localDataSource.getInspection()
        Log.d("RepositoryImpl", "dataFromDB: ${dataFromDB.toString()}")
        if (dataFromDB.survey.categories.size>0) {
            return Resource.Success(200, dataFromDB)
        } else {
            return Resource.Error(400, "Some issue happened")
        }
    }

    override suspend fun submitInspection(inspection: Inspection): Resource<String> {
        val response = remoteDataSource.submitInspect(InspectionModel(inspection))
        return try {
            when (response.code()) {
                200 -> Resource.Success(response.code(),"Inspection Submitted Successfully")
                500 -> Resource.Error(response.code(),"Something went wrong, error code: ${response.code()}")
                else -> Resource.Error(response.code(),"Something went wrong, error code: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(response.code(),"An error occurred during the request: ${e.message}", null)
        }
    }

    override suspend fun clearAllData(){
        val updatedRows = localDataSource.clearAllData()
        if (updatedRows>0) {
            Log.d("RepositoryImpl", " All data is cleared successfully: $updatedRows")
        } else {
            Log.d("RepositoryImpl", "Some issue happened: $updatedRows")
        }
    }


    private fun responseToResource(response:Response<InspectionModel>):Resource<Inspection>{
        Log.d("RepositoryImpl", "responseToResource: ${response.code()}")
        Log.d("RepositoryImpl", "responseToResource: ${response.body()}")
        Log.d("RepositoryImpl", "responseToResource: ${response.message()}")
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(response.code(), result.inspection)
            }
        }
        return Resource.Error(response.code(), response.message())
    }


}
