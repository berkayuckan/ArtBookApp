package com.berkayuckan.artbookapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.berkayuckan.artbookapp.roomdb.ArtModel
import com.berkayuckan.artbookapp.model.ImageResponse
import com.berkayuckan.artbookapp.util.Resource

class FakeArtRepository : ArtRepositoryInterface {

    private val arts = mutableListOf<ArtModel>()
    private val artsLiveData = MutableLiveData<List<ArtModel>>(arts)

    override suspend fun insertArt(art: ArtModel) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: ArtModel) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<ArtModel>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}