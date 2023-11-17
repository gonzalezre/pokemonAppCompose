package com.example.pokemonapp.listPokemons.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize


data class PokemonModel(val id: Int, val name: String, val picture: String, val color : Color?)
