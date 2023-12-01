package com.example.pokemonapp.searchPokemons.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.listPokemons.ui.NetworkErrorComposable
import com.example.pokemonapp.listPokemons.ui.PokemonItem
import com.example.pokemonapp.listPokemons.ui.PokemonsViewModel
import com.example.pokemonapp.listPokemons.ui.header

@Composable
fun SearchScreen(searchViewModel: PokemonsViewModel, navigationController: NavHostController) {
    val pokemonsList: List<PokemonModel> by searchViewModel.filteredPokemons.observeAsState(initial = emptyList())
    val isLoading: Boolean by searchViewModel.isLoading.observeAsState(initial = false)
    val isErrorConnection: Boolean by searchViewModel.isErrorConnection.observeAsState(initial = false)
    val lazyListState = rememberLazyGridState()
    var searchText by remember { mutableStateOf("")}
    var searchPokemon by remember { mutableStateOf<PokemonModel?>(null) }
    LaunchedEffect(Unit) {
        if (pokemonsList.isEmpty()) {
            searchViewModel.onGettingSearchPokemons()
        }
    }


    if (isErrorConnection){
        NetworkErrorComposable { searchViewModel.onGettingSearchPokemons() }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {

            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it

                    searchViewModel.onSearchQueryChanged(searchText)
                },
                enabled = !isLoading,
                placeholder = { Text(text = "Search by pokemon name or ID", fontSize = 16.sp) },
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = Color.DarkGray,
                    backgroundColor = Color.LightGray,

                    ),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle.Default.copy(fontSize = 16.sp)
            )

            if (isLoading) {
                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            else{
                Spacer(modifier = Modifier.size(16.dp))
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
                                header(searchText)
                            } else {
                                PokemonItem(pokemon = pokemon, navigationController)
                            }

                            // Load more data when reaching the last item
//                    if (index == pokemonsList.size - 1) {
//                        // Load more data here, e.g., call a function to fetch the next page
//                        pokemonsViewModel.onGettingPokemons()
//                    }

                        }
                    }
                )
            }

        }
    }

}