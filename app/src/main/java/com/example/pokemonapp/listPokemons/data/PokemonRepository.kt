package com.example.pokemonapp.listPokemons.data

import android.util.Log
import com.example.pokemonapp.core.network.PokemonService
import com.example.pokemonapp.detailPokemon.data.model.FullPokemonModel
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PokemonRepository @Inject constructor(private val api : PokemonService) {
    //val api = PokemonService()

    suspend fun getPokemons(limit: Int): List<PokemonModel>{
        var models = listOf<PokemonModel>()
        try {
            val response = api.getPokemons(limit)
            val model : List<PokemonModel> = response.map { response ->

                val urlParts = response.url.split('/')
                val id = urlParts[urlParts.size - 2].toInt()

                //val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                //val bitmap = loadImageBitmap(picture)



                //val palette = bitmap?.let { Palette.from(it).generate() }
                //val darkVibrantSwatch = palette?.dominantSwatch

                PokemonModel(
                    id = id,
                    name = response.name,
                    //color = darkVibrantSwatch?.let { Color(it.rgb) } ?: Color.Transparent
                    color = null,
                )
            }

            // Create a new PokemonModel with id = 0
            val modelHeader = PokemonModel(
                id = 0,
                name = "Pokemon",
                color = null,
            )

             models = listOf(modelHeader) + model
        }
        catch (e: Exception){
            Log.e("PokemonRepository", e.toString())
        }
       return models
    }


    suspend fun getPokemonById(id: Int): FullPokemonModel{
        lateinit var model: FullPokemonModel
        try {
            val response = api.getPokemonById(id)

            model = FullPokemonModel(
                abilities = response.abilities,
                baseExperience = response.baseExperience,
                forms = response.forms,
                gameIndices =  response.gameIndices,
                height = response.height,
                heldItems = response.heldItems,
                id = response.id,
                isDefault = response.isDefault,
                locationAreaEncounters = response.locationAreaEncounters,
                moves = response.moves,
                name = response.name,
                order = response.order,
                pastAbilities = response.pastAbilities,
                pastTypes = response.pastTypes,
                species = response.species,
                sprites = response.sprites,
                stats = response.stats,
                types = response.types,
                weight = response.weight,
                color = null
            )
        }
        catch (e: Exception){
            Log.e("PokemonRepository", e.toString())
        }
        return model
    }

    /*suspend fun getPokemons() : Int{
        return api.getPokemons()
    }*/



}