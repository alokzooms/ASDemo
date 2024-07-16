package com.maxlabs.asdemo.data.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.maxlabs.asdemo.data.db.InspectionDB
import com.maxlabs.asdemo.data.db.dao.InspectionDao
import com.maxlabs.asdemo.model.Area
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.InspectionType
import com.maxlabs.asdemo.model.Survey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class InspectionDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: InspectionDB
    private lateinit var dao: InspectionDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            InspectionDB::class.java
        ).allowMainThreadQueries().build()
        dao = database.inspectionDao()
    }


    @Test
    fun insertInspection_success() = runBlocking {
        // Arrange
        val inspection = Inspection(
            id = 1,
            area = Area(1,"Emergency ICU"),
            inspectionType = InspectionType(1,"write","Clinical"),
            survey = Survey(1, emptyList())  )
        
        // Act
        dao.insert(inspection)

        // Assert
        val retrievedInspection = dao.getAllInspections()
        assertNotNull(retrievedInspection)
        assertEquals("Emergency ICU", retrievedInspection?.area?.name)
        assertEquals("Clinical", retrievedInspection?.inspectionType?.name)
    }

    @After
    fun tearDown() {
        database.close()
    }

}
