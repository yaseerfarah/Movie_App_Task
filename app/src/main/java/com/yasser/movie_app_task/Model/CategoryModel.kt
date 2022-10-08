package com.yasser.movie_app_task.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasser.bosta_task.Utils.Interfaces.ModelBase
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Categories")
data class CategoryModel(
    @PrimaryKey
    override val id: Int,
    val name: String,
):ModelBase, Parcelable
