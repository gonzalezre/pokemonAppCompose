package com.example.pokemonapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonapp.detailPokemon.ui.DetailScreen
import com.example.pokemonapp.detailPokemon.ui.DetailsViewModel
import com.example.pokemonapp.listPokemons.ui.PokemonsScreen
import com.example.pokemonapp.listPokemons.ui.PokemonsViewModel
import com.example.pokemonapp.searchPokemons.ui.SearchScreen
import com.example.pokemonapp.searchPokemons.ui.SearchViewModel
import com.example.pokemonapp.ui.BottomNavigation
import com.example.pokemonapp.ui.model.Routes
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pokemonsViewModel : PokemonsViewModel by viewModels()
    private val detailsViewModel : DetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                PokemonAppTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp),
                        color = MaterialTheme.colorScheme.background,
                        content = {
                            val navigationController: NavHostController = rememberNavController()
                            Scaffold  (
                                bottomBar = { BottomNavigation(navigationController) },
                                content = { it

                                    NavHost(navController = navigationController, startDestination = Routes.PokemonsScreen.route){
                                        composable(Routes.PokemonsScreen.route){ PokemonsScreen(pokemonsViewModel, navigationController)}
                                        composable(Routes.DetailScreen.route, arguments = listOf(navArgument("id") { type = NavType.IntType})) { backStackEntry->
                                            DetailScreen(detailsViewModel, navigationController, backStackEntry.arguments?.getInt("id")?: 0)
                                        }
                                        composable(Routes.SearchScreen.route) { SearchScreen(pokemonsViewModel, navigationController) }
                                    }
                                }
                            )
                        },


                        )
                }
            }
        }
        catch (e : Exception){
            Log.e("MainActivity", e.toString())
        }
    }
}

