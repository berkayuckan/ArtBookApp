package com.berkayuckan.artbookapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtModel::class], version = 1, exportSchema = false)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao() : ArtDao
}