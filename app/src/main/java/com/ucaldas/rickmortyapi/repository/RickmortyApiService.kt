package com.ucaldas.rickmortyapi.repository

import com.ucaldas.rickmortyapi.model.Personaje
import com.ucaldas.rickmortyapi.model.PersonajesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickmortyApiService {
    @GET("character")
    suspend fun obtenerPersonajes(): PersonajesResponse

    @GET("character/{id}")
    suspend fun obtenerPersonajes(@Path("id") id: Int): Personaje
    fun obtenerPersonaje(id: Int): Personaje
}