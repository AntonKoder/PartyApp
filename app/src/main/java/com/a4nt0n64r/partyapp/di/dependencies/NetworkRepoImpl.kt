package com.a4nt0n64r.partyapp.di.dependencies

import android.util.Log
import com.a4nt0n64r.partyapp.models.network.ApiResponse
import com.a4nt0n64r.partyapp.models.toPartyUI
import com.a4nt0n64r.partyapp.repository.network.ApiService
import com.a4nt0n64r.partyapp.repository.network.Callback
import com.a4nt0n64r.partyapp.repository.network.NetworkRepository
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepoImpl @Inject constructor(private val apiService: ApiService) : NetworkRepository {

    override fun getParty(callBack: Callback) {
        val call = apiService.getParty()
        call.enqueue(object : retrofit2.Callback<ApiResponse> {

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val party: ApiResponse = response.body()!!
                    callBack.onSuccess(party.toPartyUI())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                callBack.onError("NETWORK ERROR!")
                Log.d("TAG_call", "$call")
                Log.d("TAG_t", "$t")
            }
        })
    }
}
