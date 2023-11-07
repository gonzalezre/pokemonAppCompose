package com.example.pokemonapp.listPokemons.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.example.pokemonapp.core.network.PokemonService
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PokemonRepository @Inject constructor(private val api : PokemonService) {
    //val api = PokemonService()

    suspend fun getPokemons() : List<PokemonModel>{
        val response = api.getPokemons()
        val model : List<PokemonModel> = response.map { response ->

            val urlParts = response.url.split('/')
            val id = urlParts[urlParts.size - 2].toInt()
            val picture = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"

            //val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            val bitmap = loadImageBitmap(picture)

            val palette = Palette.from(bitmap!!).generate()
            val darkVibrantSwatch = palette.dominantSwatch

            PokemonModel(
                id = id,
                name = response.name,
                picture = picture,
                color = darkVibrantSwatch?.let { Color(it.rgb) } ?: Color.Transparent
            )
        }

        // Create a new PokemonModel with id = 0
        val modelHeader = PokemonModel(
            id = 0,
            name = "Pokemon",
            picture = "",
            color = null
        )

        val models = listOf(modelHeader) + model

       return models
    }

    /*suspend fun getPokemons() : Int{
        return api.getPokemons()
    }*/

    // Load the image in a background thread using a coroutine
    private suspend fun loadImageBitmap(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream: InputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}