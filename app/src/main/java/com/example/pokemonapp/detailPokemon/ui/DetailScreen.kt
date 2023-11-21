package com.example.pokemonapp.detailPokemon.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokemonapp.R
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.listPokemons.ui.PokemonsViewModel
import com.example.pokemonapp.ui.theme.BottomCardShape
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.sin

@Composable
fun DetailScreen( pokemonsViewModel: PokemonsViewModel, navigationController: NavHostController, id : Int?) {
    val selectedPokemon: PokemonModel by pokemonsViewModel.selectedPokemon.observeAsState(initial = PokemonModel(id = 0, name = "", color = null))
    val svgPainter: Painter = painterResource(id = R.drawable.wave)

//    LaunchedEffect(Unit) {
//        detailsViewModel.onGettingPokemonByInfo(id)
//    }

    // Use the SystemUiController to set the status bar color
    val systemUiController = rememberSystemUiController()
    DisposableEffect(selectedPokemon.color) {
        systemUiController.setStatusBarColor(selectedPokemon.color!!)
        onDispose {
            // Reset status bar color on dispose
            systemUiController.setStatusBarColor(Color.White)
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(selectedPokemon.color!!)
    ){
        Column (modifier = Modifier.padding(8.dp).zIndex(999f)) {
            Text(text = "#${selectedPokemon.id} ${selectedPokemon.name}", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model =  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${selectedPokemon.id}.png",
                    contentDescription = "$id" ,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    contentScale = ContentScale.Fit,
                )
            }
        }

        Box (modifier = Modifier.align(Alignment.BottomCenter)) {
            androidx.compose.material.Card (
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                elevation = 0.dp,
                shape = BottomCardShape.large
            ) {
                Box {
                    Column {
                        Text(text = "test")
                    }
                }
            }
        }

    }
}
