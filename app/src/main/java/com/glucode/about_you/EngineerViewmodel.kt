package com.glucode.about_you

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData

class EngineerViewmodel : ViewModel() {

    private val _engineers = MutableLiveData(MockData.engineers)
    var engineers: LiveData<List<Engineer>> = _engineers
    var selectedEngineer: Engineer = Engineer()

    fun getEngineerByName(name: String): Engineer =
        _engineers.value?.first { it.name == name } ?: Engineer()

    fun updatedSelectedEngineerProfilePicture(profilePicture: String) {
        selectedEngineer.defaultImageName = profilePicture
    }
}