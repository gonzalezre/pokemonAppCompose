package com.example.pokemonapp.detailPokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.listPokemons.ui.PokemonsViewModel

@Composable
fun DetailScreen( pokemonsViewModel: PokemonsViewModel, navigationController: NavHostController, id : Int?) {
    val selectedPokemon: PokemonModel by pokemonsViewModel.selectedPokemon.observeAsState(initial = PokemonModel(id = 0, name = "", color = null))


//    LaunchedEffect(Unit) {
//        detailsViewModel.onGettingPokemonByInfo(id)
//    }

    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Text(text =  "id")
        Box (
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(Color.Black),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model =  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${selectedPokemon.id}.png",
                contentDescription = "$id" ,
                modifier = Modifier
                    .fillMaxSize(0.75f)
                    .padding(4.dp),
                contentScale = ContentScale.Crop,
            )
        }

    }
}