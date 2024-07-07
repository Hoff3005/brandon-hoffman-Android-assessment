package com.glucode.about_you.engineers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.engineers.models.QuickStatsEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EngineerViewmodel @Inject constructor(private val repository: EngineerRepository) :
    ViewModel() {

    private val _engineers = MutableLiveData<List<Engineer>>()
    val engineers: LiveData<List<Engineer>> get() = _engineers
    private var selectedEngineer: Engineer = Engineer()

    fun loadEngineers() {
        if (!_engineers.isInitialized) {
            viewModelScope.launch {
                _engineers.postValue(repository.fetchEngineers())
            }
        }
    }

    fun getEngineerByName(name: String): Engineer =
        _engineers.value?.first { it.name == name } ?: Engineer()

    fun updatedSelectedEngineerProfilePicture(profilePicture: String) {
        selectedEngineer.defaultImageName = profilePicture
    }

    fun sortEngineersByStat(quickStatsEnum: QuickStatsEnum) {
        val sortedList = _engineers.value?.toMutableList()?.sortedBy {
            when (quickStatsEnum) {
                QuickStatsEnum.YEARS -> it.quickStats.years
                QuickStatsEnum.COFFEES -> it.quickStats.coffees
                QuickStatsEnum.BUGS -> it.quickStats.bugs
            }
        }
        _engineers.postValue(sortedList ?: _engineers.value)
    }

    fun setSelectedEngineer(engineer: Engineer) {
        selectedEngineer = engineer
    }

    fun getSelectedEngineer(): Engineer = selectedEngineer
}