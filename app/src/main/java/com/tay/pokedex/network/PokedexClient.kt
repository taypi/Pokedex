package com.tay.pokedex.network

import com.tay.pokedex.model.PokemonResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PokedexClient {
    private val pokedexService: PokedexService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PokedexService::class.java)
    }

    suspend fun fetchPokemonList(
        page: Int
    ): PokemonResponse =
        pokedexService.fetchPokemonList(
            limit = PAGING_SIZE,
            offset = page * PAGING_SIZE
        )

    companion object {
        const val PAGING_SIZE = 30
    }
}