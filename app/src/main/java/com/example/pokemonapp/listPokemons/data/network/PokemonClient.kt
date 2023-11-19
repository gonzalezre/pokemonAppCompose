package com.example.pokemonapp.listPokemons.data.network

import androidx.lifecycle.LiveData
import com.example.pokemonapp.core.network.response.PokemonsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonClient {
//    @GET("pokemon?limit=40")
//    suspend fun getPokemons(): Response<PokemonsListResponse>

    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int): Response<PokemonsListResponse>

    @GET("pokemonById")
    suspend fun getPokemonById(@Query("id") id: Int) : Response<PokemonsListResponse>
}