package com.yasser.movie_app_task.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.yasser.movie_app_task.Data.Local.RoomConverter.RoomTypeConverter
import com.yasser.movie_app_task.Model.CategoryModel
import com.yasser.movie_app_task.Model.CategoryMoviePair
import com.yasser.movie_app_task.Model.MovieModel

@Database(entities = [CategoryModel::class,CategoryMoviePair::class,MovieModel::class],version = 1)
@TypeConverters(RoomTypeConverter::class)
abstract class MainRoomDatabase: RoomDatabase() {
    abstract fun categoryWithMovieDao():CategoryWithMovieDaoRoom
}