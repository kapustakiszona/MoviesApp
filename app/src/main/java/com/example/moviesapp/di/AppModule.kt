package com.example.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.localdb.database.FilmsDatabase
import com.example.moviesapp.data.network.Constants
import com.example.moviesapp.data.network.FilmsApi
import com.example.moviesapp.data.network.FilmsService
import com.example.moviesapp.repository.FilmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(app: Application): FilmsDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = FilmsDatabase::class.java,
            name = "movies_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun providesBaseRequestUrl() = Constants.BASE_REQUEST_URL

    @Singleton
    @Provides
    fun providesRetrofit(BASE_REQUEST_URL: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_REQUEST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesFilmService(retrofit: Retrofit): FilmsService =
        retrofit.create(FilmsService::class.java)

    @Singleton
    @Provides
    fun providesFilmsApi(filmsService: FilmsService) = FilmsApi(filmsService)

    @Singleton
    @Provides
    fun providesFilmRepository(db: FilmsDatabase, filmsApi: FilmsApi) = FilmRepository(
        localDataSource = db, remoteDataSource = filmsApi
    )
}