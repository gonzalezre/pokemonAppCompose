package com.example.pokemonapp.core.network

import com.example.pokemonapp.core.network.response.PokemonsListResponse
import com.example.pokemonapp.core.network.response.PokemonsResponse
import com.example.pokemonapp.listPokemons.data.network.PokemonClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class PokemonService @Inject constructor(private val pokemonClient: PokemonClient) {
    //val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPokemons() : List<PokemonsResponse>{
        return withContext(Dispatchers.IO){
           val response = pokemonClient.getPokemons()
            response.body()!!.results
        }
    }
    /*suspend fun getPokemons() : Int {
        try {
            return withContext(Dispatchers.IO){
                val response = pokemonClient.getPokemons()
                response.body()!!.count
            }
        }
        catch (e: Exception){
            e.toString()
        }
        return 0
    }*/
}