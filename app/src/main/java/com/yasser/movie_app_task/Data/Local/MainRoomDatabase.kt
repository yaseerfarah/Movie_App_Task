package com.yasser.movie_app_task.Data.Local

import androidx.room.Database
import com.yasser.movie_app_task.Model.CategoryModel
import com.yasser.movie_app_task.Model.CategoryMoviePair
import com.yasser.movie_app_task.Model.MovieModel

@Database(entities = [CategoryModel::class,CategoryMoviePair::class,MovieModel::class],version = 1)
abstract class MainRoomDatabase {
    abstract fun categoryWithMovieDao():CategoryWithMovieDaoRoom
}