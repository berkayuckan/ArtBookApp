package com.berkayuckan.artbookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkayuckan.artbookapp.model.ImageResponse
import com.berkayuckan.artbookapp.repo.ArtRepositoryInterface
import com.berkayuckan.artbookapp.roomdb.ArtModel
import com.berkayuckan.artbookapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository : ArtRepositoryInterface
) : ViewModel() {

    // Art Fragment

    val artList = repository.getArt()

    // Image API Fragment

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
        get() = selectedImage

    // Art Details Fragment

    private var insertArtMsg = MutableLiveData<Resource<ArtModel>>()
    val insertArtMessage : LiveData<Resource<ArtModel>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<ArtModel>>()
    }

    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteArt(artModel: ArtModel) = viewModelScope.launch{
        repository.deleteArt(artModel)
    }

    fun insertArt(artModel: ArtModel) = viewModelScope.launch {
        repository.insertArt(artModel)
    }

    fun makeArt(name: String, artistName: String, year: String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name, artist, year",null))
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e: Exception){
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        val art = ArtModel(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString : String){
        if (searchString.isEmpty()){
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }



}