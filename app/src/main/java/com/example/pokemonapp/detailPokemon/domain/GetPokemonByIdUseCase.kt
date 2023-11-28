package com.example.pokemonapp.detailPokemon.domain

import com.example.pokemonapp.detailPokemon.data.model.FullPokemonModel
import com.example.pokemonapp.listPokemons.data.PokemonRepository
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import javax.inject.Inject

class GetPokemonByIdUseCase @Inject constructor(private val repository : PokemonRepository) {
    suspend operator fun  invoke(id : Int ) : FullPokemonModel{
        return repository.getPokemonById(id)
    }
}