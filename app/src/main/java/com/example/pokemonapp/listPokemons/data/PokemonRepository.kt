package com.example.pokemonapp.listPokemons.data

import com.example.pokemonapp.core.network.PokemonService
import com.example.pokemonapp.core.network.response.PokemonsResponse
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(private val api : PokemonService) {
    //val api = PokemonService()

    suspend fun getPokemons() : List<PokemonModel>{
        val response = api.getPokemons()
        val model : List<PokemonModel> = response.map { response ->

            val urlParts = response.url.split('/')
            val id = urlParts[urlParts.size - 2].toInt()
            val picture = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"

            PokemonModel(
                id = id,
                name = response.name,
                picture = picture,
                color = null
            )
        }

        // Create a new PokemonModel with id = 0
        val modelHeader = PokemonModel(
            id = 0,
            name = "Pokemon",
            picture = "",
            color = null
        )

        val models = listOf(modelHeader) + model

       return models
    }

    /*suspend fun getPokemons() : Int{
        return api.getPokemons()
    }*/
}