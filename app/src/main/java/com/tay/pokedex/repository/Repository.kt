package com.tay.pokedex.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tay.pokedex.database.PokedexDb
import com.tay.pokedex.network.PokedexClient

class Repository(context: Context) {
    private val pokedexClient = PokedexClient()
    private val pokedexDb = PokedexDb.getInstance(context)

    @OptIn(ExperimentalPagingApi::class)
    fun pokemonFlow() = Pager(
        config = PagingConfig(PokedexClient.PAGING_SIZE),
        remoteMediator = PokedexRemoteMediator(pokedexDb, pokedexClient)
    ) {
        pokedexDb.pokemonDao().fetchPagingSource()
    }.flow
}