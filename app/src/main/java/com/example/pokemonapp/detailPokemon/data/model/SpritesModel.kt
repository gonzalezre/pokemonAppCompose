package com.example.pokemonapp.detailPokemon.data.model

import com.google.gson.annotations.SerializedName

data class SpritesModel(@SerializedName("back_default") val backDefault: String,
                        @SerializedName("back_female") val backFemale: Any? = null,
                        @SerializedName("back_shiny") val backShiny: String,
                        @SerializedName("back_shiny_female") val backShinyFemale: Any? = null,
                        @SerializedName("front_default") val frontDefault: String,
                        @SerializedName("front_female") val frontFemale: Any? = null,
                        @SerializedName("front_shiny") val frontShiny: String,
                        @SerializedName("front_shiny_female") val frontShinyFemale: Any? = null,
                        @SerializedName("other") val other: OtherModel? = null,
                        @SerializedName("versions") val versions: VersionsModel? = null,
                        @SerializedName("animated") val animated: SpritesModel? = null)
