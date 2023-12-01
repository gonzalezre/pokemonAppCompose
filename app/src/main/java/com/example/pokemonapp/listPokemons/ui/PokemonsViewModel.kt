package com.example.pokemonapp.listPokemons.ui


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(@ApplicationContext context: Context, private val getPokemonsUseCase : GetPokemonsUseCase): ViewModel() {

    //val getPokemonsUseCase = GetPokemonsUseCase()
    private val _pokemons = MutableLiveData<List<PokemonModel>>()
    val pokemons : LiveData<List<PokemonModel>> = _pokemons

    private val _selectedPokemon = MutableLiveData<PokemonModel>()
    val selectedPokemon : LiveData<PokemonModel> = _selectedPokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isErrorConnection = MutableLiveData<Boolean>()
    val isErrorConnection : LiveData<Boolean> = _isErrorConnection

    private val _limit = MutableLiveData<Int>(40)
    val limit : LiveData<Int> = _limit

    //search
    private val _pokemonsSearch = MutableLiveData<List<PokemonModel>>()
    val pokemonsSearch : LiveData<List<PokemonModel>> = _pokemonsSearch

    private val _filteredPokemons = MutableLiveData<List<PokemonModel>>()
    val filteredPokemons : LiveData<List<PokemonModel>> = _filteredPokemons

    private val searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery(context)
    }

    fun onGettingPokemons(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = getPokemonsUseCase(limit.value!!)

                Log.i("PokemonsViewModel",result.toString())
                if (result != null){
                    //load results

                    _pokemons.value = result

                    //Log.i("_pokemons", _pokemons.toString())
                }
                _isLoading.value = false
                _isErrorConnection.value = false

                _limit.value = limit.value!! + 40

            }
            catch (e: Exception){
                Log.e("PokemonsViewModel", e.toString())
                _isLoading.value = false
                _isErrorConnection.value = true
            }

        }
    }

    fun onGettingSearchPokemons(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = getPokemonsUseCase(1200)
                if (result != null){
                    _pokemonsSearch.value = result
                }
                _isLoading.value = false
            }
            catch (e: Exception){
                Log.e("PokemonsViewModel", e.toString())
                _isLoading.value = false
                _isErrorConnection.value = true
            }
        }
    }

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
                        val filteredPokemons = _pokemonsSearch.value!!.filter {
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

    fun onSelectingPokemon(pokemon: PokemonModel) {
        _selectedPokemon.value = pokemon
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


    fun calcDominantColor(drawable : Drawable, onFinish : (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}