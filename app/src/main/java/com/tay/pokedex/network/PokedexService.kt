package com.tay.pokedex.network

import com.tay.pokedex.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokedexService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 30,
        @Query("offset") offset: Int = 0
    ): PokemonResponse
}