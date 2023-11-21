package com.example.pokemonapp.searchPokemons.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.listPokemons.domain.GetPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(@ApplicationContext context: Context, private val getPokemonsUseCase : GetPokemonsUseCase) :  ViewModel(){

    private val _pokemons = MutableLiveData<List<PokemonModel>>()
    val pokemons : LiveData<List<PokemonModel>> = _pokemons

    private val _selectedPokemon = MutableLiveData<PokemonModel>()
    val selectedPokemon : LiveData<PokemonModel> = _selectedPokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isErrorConnection = MutableLiveData<Boolean>()
    val isErrorConnection : LiveData<Boolean> = _isErrorConnection

    private val _limit = MutableLiveData<Int>(1200)
    val limit : LiveData<Int> = _limit

    private val _filteredPokemons = MutableLiveData<List<PokemonModel>>()
    val filteredPokemons : LiveData<List<PokemonModel>> = _filteredPokemons

    private val searchQuery = MutableStateFlow("")
    //private val _searchQuery = MutableLiveData<String>()
    //val searchQuery: LiveData<String> = _searchQuery

    init {
        observeSearchQuery(context)
    }

    fun onGettingPokemons(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = getPokemonsUseCase(limit.value!!)
                if (result != null){
                    _pokemons.value = result
                }
                _isLoading.value = false
            }
            catch (e: Exception){
                Log.e("SearchViewModel", e.toString())
                _isLoading.value = false
                _isErrorConnection.value = true
            }
        }
    }

    /*fun searchPokemons(query: String) {

        viewModelScope.launch {
            query.debounce(2000)
        }

        // Filter the results based on the search query
        val filteredPokemons = _pokemons.value!!.filter {
            it.name.contains(query, ignoreCase = true)
        }

        _filteredPokemons.value = filteredPokemons
    }*/

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }
    private fun observeSearchQuery(context: Context) {
        viewModelScope.launch {
                searchQuery.debounce(2000)
                    .collect { query ->
                        // Perform the search logic here
                        // Update filteredPokemons accordingly
                        if (searchQuery.value != "") {
                            _isLoading.value = true
                            val filteredPokemons = _pokemons.value!!.filter {
                                it.name.contains(query, ignoreCase = true)
                            }

                            val updatedResult = filteredPokemons.map { it->
                                val bitmap =  convertImageUrlToBitmap("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${it.id}.png", context)
                                val palette = bitmap?.let { Palette.from(it).generate() }
                                val darkVibrantSwatch = palette?.dominantSwatch
                                it.copy(color = darkVibrantSwatch?.let { Color(it.rgb) } ?: Color.Transparent)
                            }


                            //_filteredPokemons.value = filteredPokemons
                            _filteredPokemons.value = updatedResult
                            _isLoading.value = false
                        }


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