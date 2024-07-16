package com.maxlabs.asdemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.maxlabs.asdemo.model.Inspection

@Dao
interface InspectionDao {
    @Insert
    suspend fun insert(inspection: Inspection)

    @Query("SELECT * FROM inspection_table")
    suspend fun getAllInspections(): Inspection

    @Query("DELETE FROM inspection_table")
    suspend fun clearAllData(): Int

    @Update
    suspend fun updateInspection(inspection: Inspection)
}
