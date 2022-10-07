package com.yasser.movie_app_task.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasser.bosta_task.Utils.Interfaces.ModelBase

@Entity(tableName = "Movies")
data class MovieModel(
    @PrimaryKey
    override val id: Int,
    val original_title: String,
    val title: String,
    val overview: String,
    val vote_average: String,
    val adult: String,
    val poster_path: String,
    val backdrop_path: String,
):ModelBase
