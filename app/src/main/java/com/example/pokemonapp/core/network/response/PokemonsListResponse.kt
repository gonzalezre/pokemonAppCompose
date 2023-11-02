package com.example.pokemonapp.core.network.response

import com.google.gson.annotations.SerializedName

data class PokemonsListResponse(
        @SerializedName("count") val count : Int,
        @SerializedName("next")  val next : String,
        @SerializedName("previous") val previous : String,
        @SerializedName("results") val results : MutableList<PokemonsResponse>
    )

/*data class PokemonsListResponse(
        @SerializedName("count") val count : Int,
        @SerializedName("next")  val next : String,
        @SerializedName("previous") val previous : String)*/