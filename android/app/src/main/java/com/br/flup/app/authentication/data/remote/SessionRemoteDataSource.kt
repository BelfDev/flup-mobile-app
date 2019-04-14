package com.br.flup.app.authentication.data.remote

import com.belfortdev.hurbchallenge.core.extension.mapError
import com.belfortdev.hurbchallenge.core.extension.mapNetworkErrors
import com.belfortdev.hurbchallenge.core.extension.mapToDomain
import com.br.flup.app.authentication.data.SessionDataContract
import com.br.flup.app.authentication.model.SignInForm
import com.br.flup.app.authentication.model.SignInResult
import com.br.flup.app.core.data.remote.APIFactory
import com.br.flup.app.core.model.*
import io.reactivex.Single

class SessionRemoteDataSource : SessionDataContract.Remote {

    private val flupAPIService = APIFactory.flupAPIService

    companion object {
        private const val MAX_RETRIES = 4
    }

    override fun signInEvent(signInForm: SignInForm): Single<SignInResult> =
        flupAPIService
            .signInEvent(signInForm.asEventData())
            .mapError<SignInFailedResponse, SignInResponse>()
            .mapNetworkErrors()
            .mapToDomain()

    override fun signInEmployee(signInForm: SignInForm): Single<SignInResult> =
        flupAPIService
            .signInEmployee(signInForm.asEmployeeData())
            .mapError<SignInFailedResponse, SignInResponse>()
            .mapNetworkErrors()
            .mapToDomain()
}
