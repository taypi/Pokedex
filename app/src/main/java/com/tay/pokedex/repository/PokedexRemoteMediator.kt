package com.tay.pokedex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tay.pokedex.database.PokedexDb
import com.tay.pokedex.model.Pokemon
import com.tay.pokedex.model.RemoteKey
import com.tay.pokedex.network.PokedexClient
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class PokedexRemoteMediator(
    private val db: PokedexDb,
    private val networkClient: PokedexClient
) : RemoteMediator<Int, Pokemon>() {
    private val pokemonDao = db.pokemonDao()
    private val remoteKeyDao = db.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextKey?.minus(1) ?: 0
                }
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")
                    remoteKey.nextKey ?: return MediatorResult.Success(true)
                }
            }

            val response = networkClient.fetchPokemonList(page = page)
            val lastPage = response.next == null

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.deleteAll()
                    remoteKeyDao.clearRemoteKeys()
                }
                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (lastPage) null else page + 1
                val keys = response.results.map {
                    RemoteKey(it.name, prevKey, nextKey)
                }
                pokemonDao.insertAll(response.results)
                remoteKeyDao.insertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = lastPage)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Pokemon>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                db.withTransaction { db.remoteKeyDao().remoteKeysByPokemonName(name) }
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Pokemon>
    ): RemoteKey? {
        return state.lastItemOrNull()?.let { pokemon ->
            db.withTransaction { db.remoteKeyDao().remoteKeysByPokemonName(pokemon.name) }
        }
    }
}