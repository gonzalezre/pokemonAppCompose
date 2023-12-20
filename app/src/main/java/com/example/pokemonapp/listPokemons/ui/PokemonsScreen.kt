package com.example.pokemonapp.listPokemons.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.ui.model.Routes
import com.example.pokemonapp.ui.theme.BottomNav
import com.example.pokemonapp.ui.theme.ButtonsBackground
import com.example.pokemonapp.ui.theme.Creme
import com.example.pokemonapp.ui.theme.DarkGray
import com.example.pokemonapp.ui.theme.PrimaryBackground
import com.example.pokemonapp.ui.theme.TopBarColor
import java.util.Locale


@Composable
fun PokemonsScreen(pokemonsViewModel: PokemonsViewModel, navigationController: NavHostController) {
        val pokemonsList: List<PokemonModel> by pokemonsViewModel.pokemons.observeAsState(initial = emptyList())
        val isLoading: Boolean by pokemonsViewModel.isLoading.observeAsState(initial = false)
        val isErrorConnection: Boolean by pokemonsViewModel.isErrorConnection.observeAsState(initial = false)
        //val lazyListState = rememberLazyGridState()

        val lazyListState = rememberLazyListState()
        LaunchedEffect(Unit) {
            if (pokemonsList.isEmpty()) {
                pokemonsViewModel.onGettingPokemons()
            }
        }

        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(PrimaryBackground, ButtonsBackground)))
            ) {
                CircularProgressIndicator(Modifier.align(Alignment.Center), color = TopBarColor)
            }
        }
        else if (isErrorConnection){
            NetworkErrorComposable { pokemonsViewModel.onGettingPokemons() }
        }
        else {
            //oldScreen(pokemonsList, pokemonsViewModel, lazyListState, navigationController)
            MainList(pokemonsList, pokemonsViewModel, lazyListState, navigationController)
        }

}


@Composable
fun MainList(
    pokemonsList: List<PokemonModel>,
    pokemonsViewModel: PokemonsViewModel,
    lazyListState: LazyListState,
    navigationController: NavHostController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PrimaryBackground, ButtonsBackground)))
    ){
        
        //Header
        Box (modifier = Modifier){
            Image(
                painter = painterResource(id = R.drawable.main_banner),
                contentDescription = "main_banner",
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.30f)
                    .alpha(0.50f)

                ,
                contentScale = ContentScale.FillBounds,

                )


                Text(
                    text = "Welcome again!",
                    color = TopBarColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .align(Alignment.BottomStart),
                    fontFamily = FontFamily.Cursive)

        }

//        LazyColumn{
//
//            items(pokemonsList) { pokemon ->
//                Item(pokemon = pokemon,  navigationController)
//            }
//
//        }
        LazyColumn(
            state = lazyListState,
            content = {
                itemsIndexed(
                    items = pokemonsList,
                    key = { _: Int, item: PokemonModel ->
                        item.id
                    }
                ){ index, pokemon ->
                    Item(pokemon = pokemon, navigationController = navigationController )

                    if (index == pokemonsList.size - 1) {
                        // Load more data here, e.g., call a function to fetch the next page
                        pokemonsViewModel.onGettingPokemons()
                    }
                }
            }
        )


        /*LazyVerticalGrid(
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
        )*/
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Item(pokemon: PokemonModel, navigationController: NavHostController, pokemonViewModel : PokemonsViewModel = hiltViewModel()) {
    val defaultDominantColor = Color.White
    var dominantColor by remember { mutableStateOf(defaultDominantColor)}


    Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable {
                    pokemonViewModel.onSelectingPokemon(pokemon)
                    navigationController.navigate(Routes.DetailScreen.createRoute(pokemon.id))
                },
            elevation = CardDefaults.cardElevation(12.dp),
            shape = MaterialTheme.shapes.small

    ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    //.background(color = dominantColor ?: Color.Black),
                    .background(
                        Brush.radialGradient(
                            colors = listOf(dominantColor, PrimaryBackground),
                            center = Offset(300 / 2.0f, 500 / 2.0f),
                            radius = 500 / 2.0f,
                            tileMode = TileMode.Clamp
                        )
                    )

                ) {
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
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight()
                        .padding(4.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit,
                    onSuccess = { success ->
                        pokemonViewModel.calcDominantColor(success.result.drawable) { color ->
                            dominantColor = color
                        }
                    }

                )

                Column (modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight()
                , verticalArrangement = Arrangement.Center) {
                    val capitalizedText = pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }

                    Column(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(dominantColor)
                            .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Id: ${pokemon.id}",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(R.font.robotocondensed_bold, FontWeight.Bold)
                            )
                        )
                    }

                    Text(
                        text = "$capitalizedText",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        color = dominantColor,
                        modifier = Modifier.padding(start = 4.dp),
                        fontFamily = FontFamily(
                            Font(R.font.robotocondensed_regular, FontWeight.Normal)
                        )
                    )
                }


            }
        }
    
    Spacer(modifier = Modifier.height(8.dp))



}

@Composable
fun oldScreen(
    pokemonsList: List<PokemonModel>,
    pokemonsViewModel: PokemonsViewModel,
    lazyListState: LazyGridState,
    navigationController: NavHostController
) {
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


