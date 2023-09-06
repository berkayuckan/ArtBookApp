package com.berkayuckan.artbookapp.repo

import androidx.lifecycle.LiveData
import com.berkayuckan.artbookapp.api.RetrofitAPI
import com.berkayuckan.artbookapp.roomdb.model.ImageResponse
import com.berkayuckan.artbookapp.roomdb.ArtDao
import com.berkayuckan.artbookapp.roomdb.ArtModel
import com.berkayuckan.artbookapp.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
    ) : ArtRepositoryInterface {
    override suspend fun insertArt(art: ArtModel) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: ArtModel) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<ArtModel>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }

        }catch (e: Exception){
            Resource.error("No data!",null)
        }
    }
}