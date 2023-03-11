package com.omkarcodes.hackathonstarter.data.network

import com.omkarcodes.hackathonstarter.data.model.login.LoginResponse
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainApi {

    @POST("/user/userlogin")
    suspend fun login(
        @Body
        body: RequestBody
    ) : Response<LoginResponse>

}