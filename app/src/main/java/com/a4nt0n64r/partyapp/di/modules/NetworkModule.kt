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
                    ResponseBody.create(
                        "application/json".toMediaTypeOrNull(),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

}

const val getListOfReposBeingStarredJson = """
{
    "pictureUrl":"PICTURE_URL",
    "partyName":"PARTY NAME",
    "partyStarter":"Andrey",
    "pictureOfPartyStarter":"PICTURE_URL",
    "invites":[
        {
            "picture":"PICTURE1",
            "name":"NAME1"
        },
        { 
            "picture":"PICTURE2",
            "name":"NAME2"
        },
        { 
            "picture":"PICTURE3",
            "name":"NAME3"
        },
        { 
            "picture":"PICTURE4",
            "name":"NAME4"
        },
        { 
            "picture":"PICTURE5",
            "name":"NAME5"
        },
        { 
            "picture":"PICTURE6",
            "name":"NAME6"
        }
    ]
}
"""

//

