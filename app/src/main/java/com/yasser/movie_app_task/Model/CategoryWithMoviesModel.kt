package com.yasser.movie_app_task.Model

import androidx.room.*
import com.yasser.bosta_task.Utils.Interfaces.ModelBase


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
)
