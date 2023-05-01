package edu.towson.cos435.thompson.photoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageDetailViewModel : ViewModel() {
    private val _image = MutableLiveData<Image>()
    val image: LiveData<Image> = _image

    fun setImage(image: Image) {
        _image.value = image
    }
}
