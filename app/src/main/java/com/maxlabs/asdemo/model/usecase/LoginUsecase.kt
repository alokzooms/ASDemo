package com.maxlabs.asdemo.model.usecase

import com.maxlabs.asdemo.data.Repository
import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.util.Resource

class LoginUsecase(private val repository: Repository) {

    suspend fun execute(authenticationModel: AuthenticationModel): Resource<String> {
        return repository.login(authenticationModel)
    }
}