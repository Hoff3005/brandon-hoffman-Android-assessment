package com.glucode.about_you

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.glucode.about_you.engineers.EngineerRepository
import com.glucode.about_you.engineers.EngineerViewmodel
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.engineers.models.QuickStatsEnum
import com.glucode.about_you.mockdata.MockData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class EngineerViewmodelTest {

    @Mock
    private lateinit var engineerRepository: EngineerRepository

    private lateinit var engineerViewmodel: EngineerViewmodel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)//Used because dispatcher main is not available in unit tests https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/

        runTest { //this was being done on each test so moved it here.
            `when`(engineerRepository.fetchEngineers()).thenReturn(getMockResponse())
            engineerViewmodel = EngineerViewmodel(engineerRepository)
            engineerViewmodel.loadEngineers()
        }
    }

    private fun getMockResponse() = MockData.engineers

    @Test
    fun `successfully load data engineers from repository`() { // without live data extension function
        val observer = Observer<List<Engineer>> {}
        try {
            engineerViewmodel.engineers.observeForever(observer)
            engineerViewmodel.loadEngineers()
            val value = engineerViewmodel.engineers.value
            assertTrue(value!!.isNotEmpty())

        } finally {
            engineerViewmodel.engineers.removeObserver(observer)
        }
    }

    @Test
    fun `successfully load data engineers from repository alternative`() { //with live data extension
        val result = engineerViewmodel.engineers.getOrAwaitValue()
        assertTrue(result.isNotEmpty())
        assertEquals(6, result.count())
    }

    @Test
    fun `successfully find engineer from engineers list`() {
        val name = "Reenen"
        val engineer = engineerViewmodel.getEngineerByName(name)
        assertEquals(name, engineer.name)
    }

    @Test
    fun `successfully sort engineer list by bugs`() {
        val stat = QuickStatsEnum.BUGS
        engineerViewmodel.sortEngineersByStat(stat)

        val sortedEngineers = engineerViewmodel.engineers.getOrAwaitValue()

        for (i in 0..<sortedEngineers.count()) {
            if (i < sortedEngineers.count() - 1) {
                val isNextEngineerIsGreaterThanPrevious =
                    sortedEngineers[i].quickStats.bugs <= sortedEngineers[i + 1].quickStats.bugs//Proves Ascending order

                assertTrue(isNextEngineerIsGreaterThanPrevious)
            }
        }
    }

    @Test
    fun `successfully sort engineer list by coffee`() {
        val stat = QuickStatsEnum.COFFEES
        engineerViewmodel.sortEngineersByStat(stat)

        val sortedEngineers = engineerViewmodel.engineers.getOrAwaitValue()

        for (i in 0..<sortedEngineers.count()) {
            if (i < sortedEngineers.count() - 1) {
                val isNextEngineerIsGreaterThanPrevious =
                    sortedEngineers[i].quickStats.coffees <= sortedEngineers[i + 1].quickStats.coffees//Proves Ascending order

                assertTrue(isNextEngineerIsGreaterThanPrevious)
            }
        }
    }

    @Test
    fun `successfully sort engineer list by years`() {
        val stat = QuickStatsEnum.YEARS
        engineerViewmodel.sortEngineersByStat(stat)

        val sortedEngineers = engineerViewmodel.engineers.getOrAwaitValue()

        for (i in 0..<sortedEngineers.count()) {
            if (i < sortedEngineers.count() - 1) {
                val isNextEngineerIsGreaterThanPrevious =
                    sortedEngineers[i].quickStats.years <= sortedEngineers[i + 1].quickStats.years//Proves Ascending order

                assertTrue(isNextEngineerIsGreaterThanPrevious)
            }
        }
    }

    @Test
    fun `successfully set selected engineer`() {
        val selectedEngineer = engineerViewmodel.engineers.getOrAwaitValue().first()
        engineerViewmodel.setSelectedEngineer(selectedEngineer)
        assertEquals(engineerViewmodel.getSelectedEngineer().name, selectedEngineer.name)
    }

    @Test
    fun `successfully set selected engineer profile picture`() {
        val selectedEngineer = engineerViewmodel.engineers.getOrAwaitValue().first()
        val profilePictureUri = "test"

        engineerViewmodel.setSelectedEngineer(selectedEngineer)
        engineerViewmodel.updatedSelectedEngineerProfilePicture(profilePictureUri)

        assertEquals(engineerViewmodel.getSelectedEngineer().defaultImageName, profilePictureUri)
        assertEquals(
            engineerViewmodel.engineers.getOrAwaitValue().first().defaultImageName,
            profilePictureUri
        )
    }
}