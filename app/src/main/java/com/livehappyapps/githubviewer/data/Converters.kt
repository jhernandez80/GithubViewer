package com.livehappyapps.githubviewer.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.livehappyapps.githubviewer.model.LabelsItem
import com.livehappyapps.githubviewer.model.Owner
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromString(value: String?): List<LabelsItem>? {
        val listType: Type = object : TypeToken<List<LabelsItem>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<LabelsItem>?): String? {
        return Gson().toJson(list)
    }
}