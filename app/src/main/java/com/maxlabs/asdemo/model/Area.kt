package com.maxlabs.asdemo.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "area_table")
data class Area(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String
)