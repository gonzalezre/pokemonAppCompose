package com.example.pokemonapp.core.network

import androidx.lifecycle.LiveData
import com.example.pokemonapp.core.network.response.PokemonsResponse
import com.example.pokemonapp.listPokemons.data.network.PokemonClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(private val pokemonClient: PokemonClient) {
    //val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPokemons(limit: Int) : List<PokemonsResponse>{
        return withContext(Dispatchers.IO){
           val response = pokemonClient.getPokemons(limit = limit)
            response.body()!!.results
        }
    }

//    suspend fun getPokemonById(id: Int) : List<PokemonsResponse>{
//        return withContext(Dispatchers.IO){
//            val response = pokemonClient.getPokemons(limit = limit)
//            response.body()!!.results
//        }
//    }

    /*suspend fun getPokemons() : NetworkResult<List<PokemonsResponse>>{
        return try {
            withContext(Dispatchers.IO) {

                val response = pokemonClient.getPokemons()
                if (response.isSuccessful){
                    NetworkResult.Success(response.body()!!.results)
                }
                else{
                    val errorBody = response.errorBody()
                    val errorMessage = try {
                        errorBody?.string()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }

                    // Extract error code and message from the response
                    val errorCode = response.code()
                    val errorMessageString = errorMessage ?: "Unknown error"

                    NetworkResult.Error(errorCode, errorMessageString)
                }
            }
        }

        catch (e: Exception){
            Log.e("PokemonService", e.toString())
            NetworkResult.NetworkError
        }
    }*/

    /*suspend fun getPokemons() : Int {
        try {
            return withContext(Dispatchers.IO){
                val response = pokemonClient.getPokemons()
                response.body()!!.count
            }
        }
        catch (e: Exception){
            e.toString()
        }
        return 0
    }*/
}