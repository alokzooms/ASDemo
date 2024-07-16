package com.maxlabs.asdemo.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "inspection_table")
data class Inspection(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @Embedded(prefix = "area_") val area: Area,
    @Embedded(prefix = "inspection_Type_") val inspectionType: InspectionType,
    @Embedded(prefix = "survey_") val survey: Survey
)
