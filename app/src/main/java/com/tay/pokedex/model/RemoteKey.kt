package com.tay.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKey(
    @PrimaryKey val pokemonName: String,
    val prevKey: Int?,
    val nextKey: Int?
)
