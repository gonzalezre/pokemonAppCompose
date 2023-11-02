package com.example.pokemonapp.listPokemons.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.pokemonapp.R
import com.example.pokemonapp.listPokemons.data.model.PokemonModel


@Composable
fun PokemonsScreen(pokemonsViewModel: PokemonsViewModel) {
    val pokemonsList : List<PokemonModel> by pokemonsViewModel.pokemons.observeAsState(initial = emptyList())
    val isLoading: Boolean by pokemonsViewModel.isLoading.observeAsState(initial = false)
    LaunchedEffect(Unit) {
        pokemonsViewModel.onGettingPokemons()
    }

    if (isLoading){
        Box(
            Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
    else {

        Box (modifier = Modifier.fillMaxSize()){

            Image(
                painter = painterResource(id = R.drawable.pokebola),
                contentDescription = "pokebola",
                Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .alpha(0.6f)
                    .align(Alignment.TopEnd)
                    .offset(x = (30).dp)
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, start = 8.dp)) {
                //header()
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(200.dp),
                    content = {

                        itemsIndexed(items = pokemonsList,
                            key = { _: Int, item: PokemonModel ->
                                item.id
                            },
                            span = { _: Int, item: PokemonModel ->

                                if (item.id == 0){
                                    GridItemSpan(2)
                                }
                                else{
                                    GridItemSpan(1)
                                }


                            }
                        )
                        { index, pokemon ->

                            if (pokemon.id == 0){
                                header()
                            }

                            PokemonItem2(pokemon = pokemon)


                        }
                    }
                )
            }
        }
    }

}


@Composable
fun header() {
    Text(text = "Pokedex", color = Color.Black , fontWeight = FontWeight.Bold , fontSize = 40.sp)
}


@Composable
fun PokemonItem(pokemon : PokemonModel) {
    Card(

        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .padding(8.dp, 8.dp)
            .background(Color.White)
    ) {
        Box(modifier =
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .zIndex(1f)
            .graphicsLayer(
                translationX = 30f, // Adjust the translation for the X-axis
                translationY = 30f  // Adjust the translation for the Y-axis
            )) {
            AsyncImage(
                model = pokemon.picture ,
                contentDescription = pokemon.name ,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .zIndex(1f)
                    .offset(x = 30.dp, y = 30.dp),
                contentScale = ContentScale.Crop,
            )
            Text(text = pokemon.id.toString())
            Text(text = pokemon.name)
        }



    }
}

@Composable
fun PokemonItem2(pokemon : PokemonModel) {
        Box(modifier =
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .zIndex(1f)
            .graphicsLayer(
                translationX = 30f, // Adjust the translation for the X-axis
                translationY = 30f  // Adjust the translation for the Y-axis
            )) {
            AsyncImage(
                model = pokemon.picture ,
                contentDescription = pokemon.name ,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .zIndex(1f)
                    .offset(x = 30.dp, y = 30.dp),
                contentScale = ContentScale.Crop,
            )
            Text(text = pokemon.id.toString())
            Text(text = pokemon.name)
        }
}

@Preview(showBackground = true)
@Composable
fun PokemonItem() {
    Card(
        border = BorderStroke(2.dp, Color.Red ),
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
    ) {
        Column() {
            AsyncImage(model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png" , contentDescription = "test" , modifier = Modifier
                .fillMaxWidth()
                .height(100.dp), contentScale = ContentScale.Crop)
            Text(text = "#1", modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "pokemon.name", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

