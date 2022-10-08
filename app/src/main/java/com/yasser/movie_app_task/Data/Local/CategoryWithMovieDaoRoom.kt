package com.yasser.movie_app_task.Data.Local

import androidx.room.*
import com.yasser.movie_app_task.Model.CategoryModel
import com.yasser.movie_app_task.Model.CategoryMoviePair
import com.yasser.movie_app_task.Model.CategoryWithMoviesModel
import com.yasser.movie_app_task.Model.MovieModel


@Dao
interface CategoryWithMovieDaoRoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryModel: CategoryModel)

    @Query("SELECT * FROM Categories")
    suspend fun getAllCategories():List<CategoryModel>


    @Query("DELETE  FROM Categories")
    suspend fun deleteAllCategories()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieModel: MovieModel)

    @Query("SELECT * FROM Movies")
    suspend fun getAllMovies():List<MovieModel>


    @Query("DELETE  FROM Movies")
    suspend fun deleteAllMovies()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategoryMoviePair(join: CategoryMoviePair)


    @Transaction
    @Query("SELECT * FROM Categories")
    fun getAllCategoriesWithMovies(): List<CategoryWithMoviesModel>


    @Query("DELETE  FROM categorymoviepair")
    suspend fun deleteAllRelation()




}