package com.example.pokemonapp.listPokemons.ui


import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import com.example.pokemonapp.listPokemons.domain.GetPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(@ApplicationContext context: Context, private val getPokemonsUseCase : GetPokemonsUseCase): ViewModel() {

    //val getPokemonsUseCase = GetPokemonsUseCase()
    private val _pokemons = MutableLiveData<List<PokemonModel>>()
    val pokemons : LiveData<List<PokemonModel>> = _pokemons

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isErrorConnection = MutableLiveData<Boolean>()
    val isErrorConnection : LiveData<Boolean> = _isErrorConnection

    private val _limit = MutableLiveData<Int>(40)
    val limit : LiveData<Int> = _limit

    fun onGettingPokemons(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = getPokemonsUseCase(limit.value!!)

                Log.i("PokemonsViewModel",result.toString())
                if (result != null){
                    //load results

                    _pokemons.value = result
                    //_pokemons.value = (_pokemons.value ?: emptyList()) + result
                   // _pokemons.value = _pokemons.value?.plus(result)

                    Log.i("_pokemons", _pokemons.toString())
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
}