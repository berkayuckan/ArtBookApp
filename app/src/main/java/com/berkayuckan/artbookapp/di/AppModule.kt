package com.berkayuckan.artbookapp.di

import android.content.Context
import androidx.room.Room
import com.berkayuckan.artbookapp.R
import com.berkayuckan.artbookapp.api.RetrofitAPI
import com.berkayuckan.artbookapp.repo.ArtRepository
import com.berkayuckan.artbookapp.repo.ArtRepositoryInterface
import com.berkayuckan.artbookapp.roomdb.ArtDao
import com.berkayuckan.artbookapp.roomdb.ArtDatabase
import com.berkayuckan.artbookapp.util.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectRoomDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        context,
        ArtDatabase::class.java,
        "ArtBookDB"
    ).build()

    @Provides
    @Singleton
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Provides
    @Singleton
    fun injectRetrofitAPI() : RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Provides
    @Singleton
    fun injectNormalRepo(dao: ArtDao, api: RetrofitAPI) = ArtRepository(dao, api) as ArtRepositoryInterface


    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

}