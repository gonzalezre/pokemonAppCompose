package com.example.pokemonapp.listPokemons.domain

import androidx.lifecycle.LiveData
import com.example.pokemonapp.listPokemons.data.PokemonRepository
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import javax.inject.Inject


class GetPokemonsUseCase @Inject constructor(private val repository: PokemonRepository){
    //private val repository = PokemonRepository()

    suspend operator fun invoke(limit: Int) : List<PokemonModel> {
        return repository.getPokemons(limit)
    }

    /*suspend operator fun invoke() : Int {
        return repository.getPokemons()
    }*/
}