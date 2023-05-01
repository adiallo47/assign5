package edu.towson.cos435.thompson.photoapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val service = Retrofit.Builder()
        .baseUrl("https://picsum.photos/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ImageService::class.java)

    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = _images

    init {
        viewModelScope.launch {
            try {
                val response = service.getImages()
                val images = response.map { imageResponse ->
                    Image(
                        imageResponse.id,
                        imageResponse.author,
                        imageResponse.width,
                        imageResponse.height
                    )
                }
                _images.value = images
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching images", e)
            }
        }
    }
}

