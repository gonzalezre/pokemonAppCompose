package com.example.pokemonapp.listPokemons.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
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

    suspend fun getPokemons(limit: Int): List<PokemonModel>{
        var models = listOf<PokemonModel>()
        try {
            val response = api.getPokemons(limit)
            val model : List<PokemonModel> = response.map { response ->

                val urlParts = response.url.split('/')
                val id = urlParts[urlParts.size - 2].toInt()
                val picture = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"

                //val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                //val bitmap = loadImageBitmap(picture)



                //val palette = bitmap?.let { Palette.from(it).generate() }
                //val darkVibrantSwatch = palette?.dominantSwatch

                PokemonModel(
                    id = id,
                    name = response.name,
                    picture = picture,
                    //color = darkVibrantSwatch?.let { Color(it.rgb) } ?: Color.Transparent
                    color = null
                )
            }

            // Create a new PokemonModel with id = 0
            val modelHeader = PokemonModel(
                id = 0,
                name = "Pokemon",
                picture = "",
                color = null
            )

             models = listOf(modelHeader) + model
        }
        catch (e: Exception){
            Log.e("PokemonRepository", e.toString())
        }



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

    private suspend fun convertImageUrlToBitmap(imageUrl: String, context: Context) : Bitmap?{
        val loader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context = context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val imageResult = loader.execute(request = request)
        return if (imageResult is SuccessResult){
            (imageResult.drawable as BitmapDrawable).bitmap
        }
        else{
            null
        }
    }

}