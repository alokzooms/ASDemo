package com.maxlabs.asdemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maxlabs.asdemo.model.InspectionType

@Dao
interface InspectionTypeDao {
    @Insert
    suspend fun insert(inspectionType: InspectionType)

    @Query("SELECT * FROM inspection_type_table")
    suspend fun getAllInspectionTypes(): List<InspectionType>
}
