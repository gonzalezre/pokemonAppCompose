package com.example.pokemonapp.detailPokemon.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokemonapp.detailPokemon.data.model.FullPokemonModel
import com.example.pokemonapp.detailPokemon.domain.GetPokemonByIdUseCase
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(@ApplicationContext context: Context, private val getPokemonByIdUseCase : GetPokemonByIdUseCase) : ViewModel() {

    private val _pokemon = MutableLiveData<FullPokemonModel>()
    val pokemon : LiveData<FullPokemonModel> = _pokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isErrorConnection = MutableLiveData<Boolean>()
    val isErrorConnection : LiveData<Boolean> = _isErrorConnection

    fun onGettingPokemonByInfo(id: Int?, context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = getPokemonByIdUseCase(id!!)

                val bitmap =  convertImageUrlToBitmap("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png", context)
                val palette = bitmap?.let { Palette.from(it).generate() }
                val darkVibrantSwatch = palette?.dominantSwatch
                result.color =  darkVibrantSwatch?.let { Color(it.rgb) } ?: Color.Transparent

                _pokemon.value = result
                _isLoading.value = false

            } catch (e: Exception) {

                Log.e("DetailsViewModel", e.toString())
                _isLoading.value = false
                _isErrorConnection.value = true
            }
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