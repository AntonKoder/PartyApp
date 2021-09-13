package com.a4nt0n64r.partyapp.di.components

import com.a4nt0n64r.partyapp.di.modules.MockInterceptor
import com.a4nt0n64r.partyapp.di.modules.NetworkModule
import com.a4nt0n64r.partyapp.repository.network.ApiService
import com.a4nt0n64r.partyapp.repository.network.NetworkRepository
import com.a4nt0n64r.partyapp.ui.PartyViewModel
import com.google.gson.Gson
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    fun provideInterceptor(): MockInterceptor

    fun provideOkHttpClient(): OkHttpClient

    fun provideGson(): Gson

    fun provideRetrofit(): Retrofit

    fun provideApiService(): ApiService

    fun provideNetworkRepository(): NetworkRepository

    fun inject(viewModel: PartyViewModel)
}
