package com.example.pokemonapp.searchPokemons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.ui.model.Routes

@Composable
fun SearchScreen() {
    Box (modifier = Modifier.fillMaxSize().padding(20.dp).background(Color.Red)){
        Text(text = "Search screen")
    }

}