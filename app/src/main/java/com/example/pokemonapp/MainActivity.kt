package com.example.pokemonapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonapp.listPokemons.ui.PokemonsScreen
import com.example.pokemonapp.listPokemons.ui.PokemonsViewModel
import com.example.pokemonapp.searchPokemons.ui.SearchScreen
import com.example.pokemonapp.ui.BottomNavigation
import com.example.pokemonapp.ui.model.Routes
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pokemonsViewModel : PokemonsViewModel by viewModels()


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


                        ) {
                        val navigationController = rememberNavController()
                            androidx.compose.material.Scaffold(
                                bottomBar = { BottomNavigation(navigationController) }
                            ) { it

                                NavHost(navController = navigationController, startDestination = Routes.PokemonsScreen.route){
                                    composable(Routes.PokemonsScreen.route){ PokemonsScreen(pokemonsViewModel, navigationController)}
                                    composable(Routes.DetailScreen.route, arguments = listOf(navArgument("id") {type = NavType.IntType})) { backStackEntry ->
                                        //PokemonsScreen(pokemonsViewModel, navigationController, backStackEntry.arguments?.getInt("id")) ?: 0
                                        //clickable{navController.navigate("pantalla4/12121"}
                                        //clickable{navController.navigate(Routes.DetailScreen.createRoute(12121))}
                                        //Screen(parametro : Int)
                                    }
                                    composable(Routes.SearchScreen.route) { SearchScreen() }
                                }


                            }


                    }
                }
            }
        }
        catch (e : Exception){
            Log.e("MainActivity", e.toString())
        }
    }
}

