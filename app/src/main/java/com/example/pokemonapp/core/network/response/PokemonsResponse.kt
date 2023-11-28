package com.example.pokemonapp.core.network.response

import com.google.gson.annotations.SerializedName

data class PokemonsResponse(@SerializedName("name") val name : String, @SerializedName("url") val url: String)
