package com.example.aneckdoter.di

import com.example.aneckdoter.network.JokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

class AppModule {
}

@Module
@InstallIn (SingletonComponent::class)
object NetworkModule{

    @Provides
    @Singleton
    fun provideJokeApi(): JokeApi {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://baneks.ru/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return retrofit.create(JokeApi::class.java)
    }
}