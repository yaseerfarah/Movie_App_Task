package com.yasser.movie_app_task.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasser.bosta_task.Utils.Interfaces.ModelBase

@Entity(primaryKeys = ["categoryId","movieId"])
data class CategoryMoviePair(
     val categoryId: Int,
    val movieId: Int,
)
