package com.example.pokemonapp.detailPokemon.ui

import android.widget.ScrollView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.detailPokemon.data.model.AbilityModel
import com.example.pokemonapp.detailPokemon.data.model.FullPokemonModel
import com.example.pokemonapp.detailPokemon.data.model.GameIndexModel
import com.example.pokemonapp.detailPokemon.data.model.MoveModel
import com.example.pokemonapp.detailPokemon.data.model.SpeciesModel
import com.example.pokemonapp.detailPokemon.data.model.SpritesModel
import com.example.pokemonapp.detailPokemon.data.model.StatModel
import com.example.pokemonapp.detailPokemon.data.model.TypeModel
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.listPokemons.ui.NetworkErrorComposable
import com.example.pokemonapp.listPokemons.ui.PokemonsViewModel
import com.example.pokemonapp.ui.theme.BottomCardShape
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.sin

@Composable
fun DetailScreen( detailsViewModel: DetailsViewModel, navigationController: NavHostController, id : Int?) {
    val selectedPokemon: FullPokemonModel by detailsViewModel.pokemon.observeAsState(initial = FullPokemonModel(
         abilities = listOf<AbilityModel>(),
         baseExperience = 0,
        forms = listOf<SpeciesModel>(),
        gameIndices = listOf<GameIndexModel>(),
        height = 0,
        heldItems = listOf<Any?>(),
        id = 0,
        isDefault = false,
        locationAreaEncounters = "",
        moves =  listOf<MoveModel>(),
        name = "",
        order = 0,
        pastAbilities = listOf<Any?>(),
        pastTypes = listOf<Any?>(),
        species = SpeciesModel("",""),
        sprites = SpritesModel("","", "", "", "", "", ""),
        stats = listOf<StatModel>(),
        types = listOf<TypeModel>(),
        weight = 0,
        color = Color.Transparent
    ))
    val isLoading: Boolean by detailsViewModel.isLoading.observeAsState(initial = false)
    val isErrorConnection: Boolean by detailsViewModel.isErrorConnection.observeAsState(initial = false)
    val svgPainter: Painter = painterResource(id = R.drawable.wave)

    val defaultDominantColor = Color.White
    var dominantColor by remember { mutableStateOf(defaultDominantColor) }
    val scrollState = rememberScrollState()
    val endReached by remember {
        derivedStateOf {
            scrollState.value == scrollState.maxValue
        }
    }

    LaunchedEffect(Unit) {
        detailsViewModel.onGettingPokemonByInfo(id)
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
        NetworkErrorComposable { detailsViewModel.onGettingPokemonByInfo(id) }
    }
    else {
        // Use the SystemUiController to set the status bar color
        val systemUiController = rememberSystemUiController()
        DisposableEffect(dominantColor) {
            systemUiController.setStatusBarColor(dominantColor)
            onDispose {
                // Reset status bar color on dispose
                systemUiController.setStatusBarColor(Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(dominantColor)
                .verticalScroll(scrollState)
        ) {
//                top section
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .zIndex(999f)
                        //.height(height = if (endReached) 300.dp else 600.dp) //600dp
                        .height(600.dp)
                        .graphicsLayer{
                            //alpha = 1f - (scrollState.value.toFloat()/scrollState.maxValue)
                            translationY = 0.5f * scrollState.value
                        }
                        //.animateContentSize(animationSpec = tween(durationMillis = 1000, easing = EaseIn))

                ) {
                    Text(
                        text = "#${selectedPokemon.id} ${selectedPokemon.name}",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .animateContentSize(animationSpec = tween(durationMillis = 1000, easing = EaseIn)),
                        contentAlignment = Alignment.BottomCenter,

                    ) {
//                    AsyncImage(
//                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${selectedPokemon.id}.png",
//                        contentDescription = "$id",
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(4.dp),
//                        contentScale = ContentScale.Fit,
//                    )
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png")
                                .crossfade(true)
                                .build(),
                            contentDescription = selectedPokemon.name ,
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.BottomCenter)
                                .animateContentSize(animationSpec = tween(durationMillis = 1000, easing = EaseOut)),
                            //contentScale = (if (endReached) ContentScale.Fit else ContentScale.Crop),
                            //contentScale = ContentScale.Crop,
                            onSuccess = { success ->
                                detailsViewModel.calcDominantColor(success.result.drawable) { color ->
                                    dominantColor = color
                                }
                            },

                            )
                    }
                }

//                info section
                Box(modifier = Modifier
                    .animateContentSize(animationSpec = tween(durationMillis = 600))
                    .height(height = if (endReached) 550.dp else 550.dp) //550.dp
                ) {
                    androidx.compose.material.Card(
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                        //.verticalScroll(rememberScrollState())
                        ,
                        elevation = 8.dp,
                        shape = BottomCardShape.large,

                        ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp, bottom = 10.dp, start = 8.dp, end = 8.dp)
                        ) {
                            Column (modifier = Modifier.fillMaxSize()){
                                Text(text = "Types", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp))
                                LazyRow {
                                    items(selectedPokemon.types) { type ->
                                        ItemType(type)
                                    }
                                }
                                Text(text = "Sprites" , fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp))
                                LazyRow(modifier = Modifier){
                                    item {
                                        AsyncImage(
                                            model = selectedPokemon.sprites.frontDefault,
                                            contentDescription = "frontDefault",
                                            modifier = Modifier
                                                .height(100.dp)
                                                .width(100.dp),
                                            //contentScale = ContentScale.Fit
                                        )
                                    }
                                    item {
                                        AsyncImage(
                                            model = selectedPokemon.sprites.backDefault,
                                            contentDescription = "backDefault",
                                            modifier = Modifier
                                                .height(100.dp)
                                                .width(100.dp),
                                        )
                                    }
                                    item {
                                        AsyncImage(
                                            model = selectedPokemon.sprites.frontShiny,
                                            contentDescription = "frontShiny",
                                            modifier = Modifier
                                                .height(100.dp)
                                                .width(100.dp),
                                        )
                                    }
                                    item {
                                        AsyncImage(
                                            model = selectedPokemon.sprites.backShiny,
                                            contentDescription = "backShiny",
                                            modifier = Modifier
                                                .height(100.dp)
                                                .width(100.dp),
                                        )
                                    }
                                }
                                Text(text = "Base skills", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp))
                                LazyRow {
                                    items(selectedPokemon.abilities) { ability ->
                                        AbilitiesItem(ability)
                                    }
                                }
                                Text(text = "Base moves", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp))
                                LazyRow {
                                    items(selectedPokemon.moves) { move ->
                                        MovesItem(move)
                                    }
                                }
                                Text(text = "Stats", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp))
                                LazyRow {
                                    items(selectedPokemon.stats) { stat ->
                                        StatItem(stat)
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    AsyncImage(
                                        model = selectedPokemon.sprites.frontDefault,
                                        contentDescription = "frontDefault",
                                        modifier = Modifier
                                            .height(100.dp)
                                            .width(100.dp)
                                        ,
                                    )
                                }


                            }
                        }
                    }
                }


        }
    }
}



@Composable
fun StatItem(stat: StatModel) {
    Text(text = stat.stat.name, modifier = Modifier.padding(start = 10.dp))
}

@Composable
fun MovesItem(move: MoveModel) {
    Text(text = move.move.name, modifier = Modifier.padding(start = 10.dp))
}

@Composable
fun AbilitiesItem(abilityModel: AbilityModel) {
    Text(text = abilityModel.ability.name, modifier = Modifier.padding(start = 10.dp))
}

@Composable
fun ItemType(typeModel :TypeModel) {
    Text(text = typeModel.type.name, modifier = Modifier.padding(start = 10.dp))
}