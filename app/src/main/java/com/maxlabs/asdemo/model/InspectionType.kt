package com.maxlabs.asdemo.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "inspection_type_table")
data class InspectionType(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val access: String,
    val name: String
)