package com.yasser.movie_app_task.Model

import android.os.Parcelable
import androidx.room.*
import com.yasser.bosta_task.Utils.Interfaces.ModelBase
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryWithMoviesModel(
    @Embedded
    var  categoryModel: CategoryModel,
    @Relation(
        parentColumn = "id",
        entity = MovieModel::class,
        entityColumn = "id",
        associateBy = Junction(
            value = CategoryMoviePair::class,
            parentColumn = "categoryId",
            entityColumn = "movieId"
        )
    )
    var moviesList: List<MovieModel>,
): Parcelable
