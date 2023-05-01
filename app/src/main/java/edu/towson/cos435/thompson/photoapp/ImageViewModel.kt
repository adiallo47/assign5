package edu.towson.cos435.thompson.photoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ImageViewModel : ViewModel() {

    private val _images = MutableLiveData<List<String>>()
    val images: LiveData<List<String>> = _images

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    val url = "https://picsum.photos/v2/list?page=2&limit=20"
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    val response = client.newCall(request).execute()
                    response.body()?.string()
                } catch (e: IOException) {
                    null
                }
            }

            if (response != null) {
                val images = parseImagesJson(response)
                _images.postValue(images)
            }
        }
    }

    private fun parseImagesJson(json: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<Image>>() {}.type
        val images = gson.fromJson<List<Image>>(json, type)
        return images.map { image ->
            "https://picsum.photos/id/${image.id}/400/400.jpg"
        }
    }

    data class Image(
        val id: Int,
        val author: String,
        val width: Int,
        val height: Int,
        val url: String,
        val download_url: String
    )
}
