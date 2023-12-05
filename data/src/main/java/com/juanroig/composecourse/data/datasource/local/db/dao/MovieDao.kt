package com.juanroig.composecourse.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.juanroig.composecourse.data.datasource.local.db.model.MovieEntity

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM MovieEntity ORDER BY voteAverage DESC LIMIT 10")
    suspend fun getTopTenMovies(): List<MovieEntity>

}