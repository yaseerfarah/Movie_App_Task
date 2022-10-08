package com.yasser.movie_app_task.Data.Local

import android.content.Context
import com.yasser.movie_app_task.Model.CategoryModel
import com.yasser.movie_app_task.Model.CategoryMoviePair
import com.yasser.movie_app_task.Model.CategoryWithMoviesModel
import com.yasser.movie_app_task.Model.MovieModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


class RoomImplement(val context: Context,val categoryWithMovieDaoRoom: CategoryWithMovieDaoRoom) {

    suspend fun clearAllData(){
        coroutineScope {
            listOf(
            async { categoryWithMovieDaoRoom.deleteAllCategories() },
            async { categoryWithMovieDaoRoom.deleteAllMovies() },
            async { categoryWithMovieDaoRoom.deleteAllRelation() }
                ).awaitAll()
        }
    }


    suspend fun saveAllData(listOfCategoryWithMoviesModel: List<CategoryWithMoviesModel>){
            listOfCategoryWithMoviesModel.map {
                categoryWithMovieDaoRoom.insertCategory(it.categoryModel)
                saveAllMoviesWithCategoryId(it.moviesList,it.categoryModel.id)
            }

    }


    suspend fun getAllCategoriesWithMovies():List<CategoryWithMoviesModel>{
        return categoryWithMovieDaoRoom.getAllCategoriesWithMovies()
    }

    suspend fun getAllCategories():List<CategoryModel>{
        return categoryWithMovieDaoRoom.getAllCategories()
    }

    suspend fun getAllMovies():List<MovieModel>{
        return categoryWithMovieDaoRoom.getAllMovies()
    }



   private suspend fun saveAllMoviesWithCategoryId(listOfMovieModels:List<MovieModel>,categoryId:Int){

       coroutineScope {
           listOfMovieModels.map {
               async { categoryWithMovieDaoRoom.insertMovie(it) }
               async { categoryWithMovieDaoRoom.insertCategoryMoviePair(CategoryMoviePair(categoryId = categoryId,movieId = it.id)) }
           }.awaitAll()
       }

   }


    private suspend fun saveAllCategories(listOfCategoryModel:List<CategoryModel>){

        coroutineScope {
            listOfCategoryModel.map {
                async { categoryWithMovieDaoRoom.insertCategory(it) }
            }.awaitAll()
        }

    }

}