package com.example.pokemonapp.listPokemons.ui


import android.content.Context
import android.util.Log
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

    fun onGettingPokemons(){
        viewModelScope.launch {
            _isLoading.value = true
            val result = getPokemonsUseCase()

            Log.i("PokemonsViewModel",result.toString())
            if (result != null){
                //load results

                _pokemons.value = result

                Log.i("_pokemons", _pokemons.toString())
            }
            _isLoading.value = false
        }
    }
}