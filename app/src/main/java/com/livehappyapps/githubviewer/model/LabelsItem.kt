package com.livehappyapps.githubviewer.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class LabelsItem(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "color")
    @field:SerializedName("color")
    val color: String? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String? = null,

    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String? = null
)