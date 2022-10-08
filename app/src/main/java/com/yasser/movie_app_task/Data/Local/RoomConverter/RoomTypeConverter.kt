package com.yasser.movie_app_task.Data.Local.RoomConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class RoomTypeConverter {

    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(string: String):List<Int>{
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(string, listType)
    }


}