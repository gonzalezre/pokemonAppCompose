package com.example.pokemonapp.core.network.response

import com.example.pokemonapp.detailPokemon.data.model.AbilityModel
import com.example.pokemonapp.detailPokemon.data.model.GameIndexModel
import com.example.pokemonapp.detailPokemon.data.model.MoveModel
import com.example.pokemonapp.detailPokemon.data.model.SpeciesModel
import com.example.pokemonapp.detailPokemon.data.model.SpritesModel
import com.example.pokemonapp.detailPokemon.data.model.StatModel
import com.example.pokemonapp.detailPokemon.data.model.TypeModel
import com.google.gson.annotations.SerializedName

data class FullPokemonResponse(
    @SerializedName("abilities") val abilities: List<AbilityModel>,
    @SerializedName("baseExperience")  val baseExperience: Long,
    @SerializedName("forms") val forms: List<SpeciesModel>,
    @SerializedName("gameIndices")  val gameIndices: List<GameIndexModel>,
    @SerializedName("height") val height: Long,
    @SerializedName("heldItems")  val heldItems: List<Any?>,
    @SerializedName("id") val id: Long,
    @SerializedName("isDefault")  val isDefault: Boolean,
    @SerializedName("locationAreaEncounters") val locationAreaEncounters: String,
    @SerializedName("moves")  val moves: List<MoveModel>,
    @SerializedName("name") val name: String,
    @SerializedName("order")  val order: Long,
    @SerializedName("pastAbilities") val pastAbilities: List<Any?>,
    @SerializedName("pastTypes")  val pastTypes: List<Any?>,
    @SerializedName("species") val species: SpeciesModel,
    @SerializedName("sprites")  val sprites: SpritesModel,
    @SerializedName("stats")val stats: List<StatModel>,
    @SerializedName("types") val types: List<TypeModel>,
    @SerializedName("weight") val weight: Long
)
