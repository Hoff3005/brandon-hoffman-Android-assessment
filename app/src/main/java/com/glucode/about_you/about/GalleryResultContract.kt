package com.glucode.about_you.about

import android.net.Uri
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GalleryResultContract(registry: ActivityResultRegistry) {
    private val contractUriResult: MutableLiveData<Uri> = MutableLiveData(null)

    companion object {
        private const val REGISTRY_KEY = "Image Picker"
    }

    private val getPermission =
        registry.register(REGISTRY_KEY, ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                contractUriResult.value = it
            }
        }

    fun getImageFromGallery(): LiveData<Uri> {
        getPermission.launch("image/*")
        return contractUriResult
    }
}