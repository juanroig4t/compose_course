package com.juanroig.composecourse.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.juanroig.composecourse.data.datasource.local.db.dao.MovieDao
import com.juanroig.composecourse.data.datasource.local.db.model.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2),
//    ],
    exportSchema = true
)
@TypeConverters(
    ListIntConverter::class
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}