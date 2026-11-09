package com.ucaldas.rickmortyapi.repository

import com.ucaldas.rickmortyapi.model.Personaje
import com.ucaldas.rickmortyapi.model.PersonajesResponse
import kotlinx.coroutines.CancellationException

class RickmortyRepository(private val api: RickmortyApiService = RetrofitInstance.api) {

    suspend fun obtenerPersonajes(): Result<PersonajesResponse> =
        try {
            Result.success(api.obtenerPersonajes())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun obtenerPersonaje(id: Int): Result<Personaje> =
        try {
            Result.success(api.obtenerPersonaje(id))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}