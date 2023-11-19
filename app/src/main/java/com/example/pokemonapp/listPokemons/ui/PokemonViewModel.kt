package com.example.pokemonapp.listPokemons.ui

import com.example.pokemonapp.listPokemons.data.model.PokemonModel

interface PokemonViewModel {
    fun selectPokemon(pokemon: PokemonModel)
}