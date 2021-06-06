package com.tay.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tay.pokedex.model.Pokemon
import com.tay.pokedex.model.RemoteKey

@Database(entities = [Pokemon::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class PokedexDb : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        @Volatile private var instance: PokedexDb? = null

        fun getInstance(context: Context): PokedexDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PokedexDb {
            return Room.databaseBuilder(
                context,
                PokedexDb::class.java,
                "pokedex-db"
            ).build()
        }
    }
}