package com.example.pokemonapp.ui.model

sealed class Routes(val route : String){
    object PokemonsScreen: Routes("pokemonsScreen")
    object DetailScreen: Routes("detailScreen/{id}"){
        fun createRoute(id : Int) = "detailScreen/$id"
    }
    object SearchScreen: Routes("searchScreen")
}
