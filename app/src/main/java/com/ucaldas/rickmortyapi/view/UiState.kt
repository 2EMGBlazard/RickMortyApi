package com.ucaldas.rickmortyapi.view

import com.ucaldas.rickmortyapi.model.Personaje

sealed interface UiState {
    data object Loading : UiState
    data class Success(val items: List<Personaje>) : UiState
    data class Error(val message: String) : UiState
}