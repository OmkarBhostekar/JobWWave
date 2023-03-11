package com.omkarcodes.hackathonstarter.data.network

import com.omkarcodes.hackathonstarter.data.model.JobRef
import com.omkarcodes.hackathonstarter.data.model.Mentor
import com.omkarcodes.hackathonstarter.data.model.login.LoginResponse
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {

    @POST("/user/userlogin")
    suspend fun login(
        @Body
        body: RequestBody
    ) : Response<LoginResponse>
    @POST("/user/referral")
    suspend fun sendRefMsg(
        @Body
        body: RequestBody
    ) : Response<Any>
    @GET("/user/refemployee")
    suspend fun referral(
    ) : Response<List<JobRef>>
    @GET("/user/framemessage")
    suspend fun refMsg(
        @Query("id")
        id: String
    ) : Response<String>
    @GET("/user/getmentors")
    suspend fun getMentors() : Response<List<Mentor>>

}