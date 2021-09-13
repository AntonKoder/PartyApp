package com.a4nt0n64r.partyapp.repository.network

import com.a4nt0n64r.partyapp.models.network.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("test")
    fun getParty(): Call<ApiResponse>
}
