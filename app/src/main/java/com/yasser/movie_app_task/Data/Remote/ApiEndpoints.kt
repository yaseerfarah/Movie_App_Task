package com.yasser.movie_app_task.Data.Remote

import com.google.gson.JsonObject
import com.yasser.movie_app_task.Model.CategoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoints {


    @GET("genre/movie/list")
    suspend fun getAllCategories(@Query("api_key")apiKey:String): Response<JsonObject>


    @GET("discover/movie")
    suspend fun getAllMoviesByCategoryId(@Query("api_key")apiKey:String,@Query("with_genres")categoryId:String): Response<JsonObject>





}