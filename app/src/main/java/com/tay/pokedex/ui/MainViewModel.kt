package com.tay.pokedex.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.tay.pokedex.model.Pokemon
import com.tay.pokedex.repository.Repository
import kotlinx.coroutines.flow.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    private val pokemonFlow = repository.pokemonFlow()

    fun fetchPokemons(): Flow<PagingData<Pokemon>> = pokemonFlow
}
