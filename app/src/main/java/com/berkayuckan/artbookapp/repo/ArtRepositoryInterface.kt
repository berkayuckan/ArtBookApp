package com.berkayuckan.artbookapp.repo

import androidx.lifecycle.LiveData
import com.berkayuckan.artbookapp.model.ImageResponse
import com.berkayuckan.artbookapp.roomdb.ArtModel
import com.berkayuckan.artbookapp.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art : ArtModel)

    suspend fun deleteArt(art: ArtModel)

    fun getArt() : LiveData<List<ArtModel>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}