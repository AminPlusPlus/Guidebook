package com.example.guidebook.core

import android.arch.persistence.room.TypeConverter
import com.example.guidebook.models.Task
import com.google.gson.Gson

object TaskConverter {

    private val gson = Gson()

    @TypeConverter
    fun toObject(string: String?): Task? {
        return if (string == null) null else gson.fromJson(string, Task::class.java)
    }

    @TypeConverter
    fun toString(obj: Task?): String? {
        return if (obj == null) null else gson.toJson(obj)
    }
}
