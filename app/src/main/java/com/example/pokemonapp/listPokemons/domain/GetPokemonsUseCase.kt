package com.example.pokemonapp.listPokemons.domain

import android.util.Log
import com.example.pokemonapp.core.network.response.PokemonsResponse
import com.example.pokemonapp.listPokemons.data.PokemonRepository
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import javax.inject.Inject


class GetPokemonsUseCase @Inject constructor(private val repository: PokemonRepository){
    //private val repository = PokemonRepository()

    suspend operator fun invoke() : List<PokemonModel> {
        return repository.getPokemons()
    }

    /*suspend operator fun invoke() : Int {
        return repository.getPokemons()
    }*/
}