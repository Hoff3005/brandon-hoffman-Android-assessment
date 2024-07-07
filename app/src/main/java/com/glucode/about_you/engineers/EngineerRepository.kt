package com.glucode.about_you.engineers

import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData
import kotlinx.coroutines.delay
import javax.inject.Inject

class EngineerRepository @Inject constructor() {

    suspend fun fetchEngineers(): List<Engineer> {
        delay(500)//Not necessary but wanted to mimic a service response and show coroutines. Repo also allows for static data to be replaced with service in the future.
        return MockData.engineers
    }
}