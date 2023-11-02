package com.example.pokemonapp.listPokemons.data.network

import com.example.pokemonapp.core.network.response.PokemonsListResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonClient {
    @GET("pokemon?limit=40")
    suspend fun getPokemons(): Response<PokemonsListResponse>
}