package com.maxlabs.asdemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maxlabs.asdemo.model.Area

@Dao
interface AreaDao {
    @Insert
    suspend fun insert(area: Area)

    @Query("SELECT * FROM area_table")
    suspend fun getAllAreas(): List<Area>
}
