package com.tay.pokedex.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tay.pokedex.model.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Pokemon>)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()

    @Query("SELECT * FROM pokemon")
    fun fetchPagingSource(): PagingSource<Int, Pokemon>
}