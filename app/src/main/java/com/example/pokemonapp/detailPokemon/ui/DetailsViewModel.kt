package com.example.pokemonapp.detailPokemon.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.detailPokemon.domain.GetPokemonByIdUseCase
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(@ApplicationContext context: Context, private val getPokemonByIdUseCase : GetPokemonByIdUseCase) : ViewModel() {

    private val _pokemon = MutableLiveData<PokemonModel>()
    val pokemon : LiveData<PokemonModel> = _pokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isErrorConnection = MutableLiveData<Boolean>()
    val isErrorConnection : LiveData<Boolean> = _isErrorConnection

    fun onGettingPokemonByInfo(id: Int?) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                //val result = getPokemonByIdUseCase(id)
            } catch (e: Exception) {

                Log.e("DetailsViewModel", e.toString())
                _isLoading.value = false
                _isErrorConnection.value = true
            }
        }
    }
}