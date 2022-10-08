package com.yasser.movie_app_task.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasser.bosta_task.Utils.Interfaces.ModelBase
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val backdrop_path: String?,
    val popularity: String,
    val genre_ids: List<Int>,
):ModelBase,Parcelable
