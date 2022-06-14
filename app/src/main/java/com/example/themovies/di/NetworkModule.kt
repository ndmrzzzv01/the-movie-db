package com.example.themovies.di

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.MovieApi
import com.example.themovies.api.PeopleApi
import com.example.themovies.api.TvApi
import com.example.themovies.network.TheMovieDBInterceptor
import com.example.themovies.screens.people.PeopleContract
import com.example.themovies.screens.people.PeoplePresenterImpl
import com.example.themovies.screens.people.PeopleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(TheMovieDBInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun provideConfigurationApi(retrofit: Retrofit): ConfigurationApi {
        return retrofit.create(ConfigurationApi::class.java)
    }

    @Provides
    fun provideTvApi(retrofit: Retrofit): TvApi {
        return retrofit.create(TvApi::class.java)
    }

    @Provides
    fun providePeopleApi(retrofit: Retrofit): PeopleApi {
        return retrofit.create(PeopleApi::class.java)
    }

    @Provides
    fun providePeoplePresenter(peopleView: PeopleContract.PeopleView): PeoplePresenterImpl {
        return PeoplePresenterImpl(peopleView)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ModulePresenter {

    @Binds
    abstract fun bindPeoplePresenter(peoplePresenterImpl: PeoplePresenterImpl): PeopleContract.PeoplePresenter

}

