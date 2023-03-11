package com.omkarcodes.hackathonstarter.data.network

import com.omkarcodes.hackathonstarter.data.body.SmartSearchBody
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UspApi {

    @POST("/usp1")
    suspend fun getSmartSearch(
        @Body
        body: RequestBody
    ) : Response<List<SearchResult>>

}