package com.juanroig.composecourse.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.juanroig.composecourse.data.datasource.local.db.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert
    suspend fun insertMovieList(movieList: List<MovieEntity>)

    @Transaction
    @Query("SELECT * FROM MovieEntity ORDER BY voteAverage DESC LIMIT 10")
    fun getTopTenMovies(): Flow<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM MovieEntity ORDER BY popularity DESC")
    fun getPopularMovies(): Flow<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Query("UPDATE MovieEntity SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)
}
