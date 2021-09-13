package com.a4nt0n64r.partyapp.di.modules

import com.a4nt0n64r.partyapp.BuildConfig
import com.a4nt0n64r.partyapp.di.dependencies.NetworkRepoImpl
import com.a4nt0n64r.partyapp.repository.network.ApiService
import com.a4nt0n64r.partyapp.repository.network.NetworkRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideInterceptor(): MockInterceptor {
//        val interceptor = HttpLoggingInterceptor()
        val interceptor = MockInterceptor()

//        interceptor.level =
//            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    fun provideDefaultOkhttpClient(interceptor: MockInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.setLenient().create()
    }

    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideNetworkRepositoryImpl(apiService: ApiService): NetworkRepository {
        return NetworkRepoImpl(apiService)
    }
}

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = getListOfReposBeingStarredJson

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            // just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }
}

const val getListOfReposBeingStarredJson = """
{
    "pictureUrl":"https://www.pulainfo.hr/wp/wp-content/uploads/2019/05/party-1.jpg",
    "partyName":"Вечеринка",
    "partyStarter":"Andrey",
    "pictureOfPartyStarter":"https://media.istockphoto.com/photos/portrait-of-a-taiwanese-man-picture-id1149504274?k=20&m=1149504274&s=612x612&w=0&h=3yLf9OAkYZ0hB_ezday1W-xpn_o_yjMCo7hX8rBwf4o=",
    "invites":[
        {
            "picture":"https://github.com/AntonKoder/PartyApp/blob/master/app/src/main/res/drawable/hayley.jpg?raw=true",
            "name":"Hayley"
        },
        { 
            "picture":"https://github.com/AntonKoder/PartyApp/blob/master/app/src/main/res/drawable-v24/man.jpeg?raw=true",
            "name":"Artem"
        }
    ]
}
"""
