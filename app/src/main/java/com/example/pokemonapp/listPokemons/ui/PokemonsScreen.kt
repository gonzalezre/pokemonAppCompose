package com.example.pokemonapp.listPokemons.ui

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.example.pokemonapp.R
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.ui.model.Routes
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun PokemonsScreen(pokemonsViewModel: PokemonsViewModel, navigationController: NavHostController) {
        val pokemonsList: List<PokemonModel> by pokemonsViewModel.pokemons.observeAsState(initial = emptyList())
        val isLoading: Boolean by pokemonsViewModel.isLoading.observeAsState(initial = false)
        val isErrorConnection: Boolean by pokemonsViewModel.isErrorConnection.observeAsState(initial = false)
        val lazyListState = rememberLazyGridState()
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            if (pokemonsList.isEmpty()) {
                pokemonsViewModel.onGettingPokemons()
            }
        }

        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        else if (isErrorConnection){
            NetworkErrorComposable { pokemonsViewModel.onGettingPokemons() }
        }
        else {
            Box(modifier = Modifier.fillMaxSize()) {

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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //header()
                    LazyVerticalGrid(
                        state = lazyListState,
                        columns = GridCells.Adaptive(150.dp),
                        content = {
                            itemsIndexed(items = pokemonsList,
                                key = { _: Int, item: PokemonModel ->
                                    item.id
                                },
                                span = { _: Int, item: PokemonModel ->

                                    if (item.id == 0) {
                                        GridItemSpan(2)
                                    } else {
                                        GridItemSpan(1)
                                    }


                                }
                            )
                            { index, pokemon ->

                                if (pokemon.id == 0) {
                                    header("Pokedex")
                                } else {
                                    PokemonItem(pokemon = pokemon,  navigationController)
                                }

                                // Load more data when reaching the last item
                                if (index == pokemonsList.size - 1) {
                                    // Load more data here, e.g., call a function to fetch the next page
                                    pokemonsViewModel.onGettingPokemons()
                                }

                            }
                        }
                    )
                }
            }
        }

}


@Composable
fun header(title: String) {
    Text(
        text = title,
        color = Color.Black ,
        fontWeight = FontWeight.Bold ,
        fontSize = 40.sp,
        modifier = Modifier.padding(start = 10.dp)
    )
}

@Composable
fun PokemonItem(pokemon : PokemonModel, navigationController : NavHostController, pokemonViewModel : PokemonsViewModel = hiltViewModel()) {
    val defaultDominantColor = Color.White
    var dominantColor by remember { mutableStateOf(defaultDominantColor)}

    Box(modifier =
    Modifier
        .fillMaxSize()
        .padding(4.dp)
        ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .width(200.dp)
                .height(200.dp)
                .padding(8.dp, 8.dp)
                .clickable {
                    pokemonViewModel.onSelectingPokemon(pokemon)
                    navigationController.navigate(Routes.DetailScreen.createRoute(pokemon.id))
                },
            elevation = CardDefaults.cardElevation(12.dp),
            shape = MaterialTheme.shapes.small

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .background(color = dominantColor ?: Color.Black),

            ) {
                val capitalizedText = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                Text(
                    text = "#${pokemon.id.toString()}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                )
                Text(
                    text = capitalizedText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        /*AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png")
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name ,
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .offset(x = 35.dp, y = 35.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop,
                onSuccess = { success ->
                    pokemonViewModel.calcDominantColor(success.result.drawable) { color ->
                        dominantColor = color
                    }
                },

            )*/
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png")
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator()
            },
            contentDescription = pokemon.name,
            modifier = Modifier
                .fillMaxSize(0.9f)
                .offset(x = 35.dp, y = 35.dp)
                .padding(4.dp),
            contentScale = ContentScale.Crop,
            onSuccess = { success ->
                pokemonViewModel.calcDominantColor(success.result.drawable) { color ->
                    dominantColor = color
                }
            }

        )

    }
}



@Composable
fun NetworkErrorComposable(onGettingPokemons: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Add your illustration (e.g., an image or icon) here
            Image(
                painter = painterResource(id = R.drawable.networkerror),
                contentDescription = "networkerror",
                Modifier
            )
            Text("Network Error", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Sorry, we couldn't load the data. Please check your internet connection and try again.")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onGettingPokemons) {
                Text("Retry")
            }
        }
    }
}


@Composable
fun PokemonItem2(pokemon : PokemonModel) {
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
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png" ,
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


