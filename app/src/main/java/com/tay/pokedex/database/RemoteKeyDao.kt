package com.tay.pokedex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tay.pokedex.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM RemoteKey WHERE pokemonName = :name")
    fun remoteKeysByPokemonName(name: String): RemoteKey?

    @Query("DELETE FROM RemoteKey")
    fun clearRemoteKeys()
}