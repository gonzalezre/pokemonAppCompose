package com.example.pokemonapp.detailPokemon.data.model

import androidx.compose.ui.graphics.Color

data class FullPokemonModel(val abilities: List<AbilityModel>,
                            val baseExperience: Long,
                            val forms: List<SpeciesModel>,
                            val gameIndices: List<GameIndexModel>,
                            val height: Long,
                            val heldItems: List<Any?>,
                            val id: Long,
                            val isDefault: Boolean,
                            val locationAreaEncounters: String,
                            val moves: List<MoveModel>,
                            val name: String,
                            val order: Long,
                            val pastAbilities: List<Any?>,
                            val pastTypes: List<Any?>,
                            val species: SpeciesModel,
                            val sprites: SpritesModel,
                            val stats: List<StatModel>,
                            val types: List<TypeModel>,
                            val weight: Long,
                            var color : Color?)
