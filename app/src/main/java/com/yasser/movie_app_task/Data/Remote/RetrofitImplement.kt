package com.yasser.movie_app_task.Data.Remote

import android.content.Context
import com.google.gson.Gson
import com.yasser.movie_app_task.Constants
import com.yasser.movie_app_task.Model.*
import com.yasser.movie_app_task.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

class RetrofitImplement(val context: Context,val apiEndpoints: ApiEndpoints) {

    suspend fun getAllCategories():List<CategoryModel>{

        var categoryList:MutableList<CategoryModel> = mutableListOf()
           val response= apiEndpoints.getAllCategories(Constants.ApiKey)
            if (response.isSuccessful){
                val categoryResponse=Gson().fromJson(response.body()!!.asJsonObject.toString(),CategoryResponse::class.java)
               categoryList.addAll( categoryResponse.genres)
            }else{
                val message = if (response.body()?.has("status_message") == true){
                    response.body()!!.get("status_message") as String
                }else{
                    context.getString(R.string.try_again)
                }
                Throwable(message)
            }
        return categoryList
    }



    suspend fun getMoviesByCategory(categoryModel: CategoryModel):CategoryWithMoviesModel?{

        var categoryWithMoviesModel:CategoryWithMoviesModel?=null
        val response= apiEndpoints.getAllMoviesByCategoryId(Constants.ApiKey,categoryId = categoryModel.id.toString())

        if (response.isSuccessful){
            val movieListResponse=Gson().fromJson(response.body()!!.asJsonObject.toString(),MovieListResponse::class.java)
            categoryWithMoviesModel= CategoryWithMoviesModel(categoryModel,movieListResponse.results)
        }else{
            val message = if (response.body()?.has("status_message") == true){
                response.body()!!.get("status_message") as String
            }else{
                context.getString(R.string.try_again)
            }
            Throwable(message)
        }
        return categoryWithMoviesModel

    }




}