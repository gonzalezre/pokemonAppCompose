package com.example.pokemonapp.listPokemons.domain

import android.util.Log
import com.example.pokemonapp.listPokemons.data.PokemonRepository
import com.example.pokemonapp.listPokemons.data.model.PokemonModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetPokemonsUseCaseTest{

    @RelaxedMockK
    private lateinit var repository: PokemonRepository

    lateinit var getPokemonsUseCase: GetPokemonsUseCase


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getPokemonsUseCase = GetPokemonsUseCase(repository)
    }

    @Test
    fun `when Api returns 40 records`() = runBlocking{
        //given -- we need to provide a mockked repository
        val limit = 1
        val myList = listOf(
            PokemonModel(1, "test", null),
            PokemonModel(2, "test", null),
            PokemonModel(3, "test", null),
        )
        coEvery { repository.getPokemons(any()) } returns myList

        //when
        val response = getPokemonsUseCase(limit)

        //then
        //coVerify(exactly = 1) { repository.getPokemons(any())}
        assert(myList == response)
    }

    @Test
    fun `when Api returns null`() = runBlocking{
        //given -- we need to provide a mockked repository
        val limit = 1
        val myList = listOf(
            PokemonModel(0, "title", null),
        )
        coEvery { repository.getPokemons(limit) } returns myList

        //when
        val response = getPokemonsUseCase(limit)

        //then
        //coVerify(exactly = 1) { repository.getPokemons(any())}
        assert(response == myList)
    }

    @Test
    fun `invoke should return expected list`() = runBlocking{
        // Define the behavior of the mock repository
        val expectedPokemons = listOf(
            PokemonModel(1, "Bulbasur", null),
            PokemonModel(2, "Charmander", null),
            PokemonModel(3, "Squirtle", null),
            PokemonModel(4, "Pikachu", null),
        )

        //Given
        coEvery { repository.getPokemons(4)} returns expectedPokemons


        // When
        val response =  getPokemonsUseCase(4)

        // Then
        assertEquals(expectedPokemons, response)
    }
}