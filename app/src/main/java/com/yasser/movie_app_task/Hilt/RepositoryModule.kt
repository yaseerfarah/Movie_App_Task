package com.yasser.movie_app_task.Hilt

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.yasser.movie_app_task.Constants
import com.yasser.movie_app_task.Data.Local.MainRoomDatabase
import com.yasser.movie_app_task.Data.Local.RoomImplement
import com.yasser.movie_app_task.Data.Local.SharedPrefsHelper
import com.yasser.movie_app_task.Data.Remote.ApiEndpoints
import com.yasser.movie_app_task.Data.Remote.NetworkConnectionInterceptor
import com.yasser.movie_app_task.Data.Remote.RetrofitImplement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
 class RepositoryModule {

    @Provides
    @Singleton
    fun apiEndpoints(retrofit: Retrofit): ApiEndpoints {
        return retrofit.create(ApiEndpoints::class.java)
    }


    @Provides
    fun okHttp(networkConnectionInterceptor: NetworkConnectionInterceptor):OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(networkConnectionInterceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES).build()

    }

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun roomDatabase(@ApplicationContext context: Context):MainRoomDatabase{
       return Room.databaseBuilder(
            context,
            MainRoomDatabase::class.java, Constants.DB_Name
        ).build()
    }


    @Provides
    @Singleton
    fun sharedPrefs(@ApplicationContext context: Context):SharedPrefsHelper{
        return SharedPrefsHelper(context)
    }


    @Provides
    @Singleton
    fun roomImplementation(@ApplicationContext context: Context,roomDatabase: MainRoomDatabase):RoomImplement{
        return RoomImplement(context,roomDatabase.categoryWithMovieDao())
    }


    @Provides
    @Singleton
    fun retrofitImplementation(@ApplicationContext context: Context,retrofit: ApiEndpoints):RetrofitImplement{
        return RetrofitImplement(context,retrofit)
    }









}