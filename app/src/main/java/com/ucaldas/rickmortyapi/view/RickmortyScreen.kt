package com.ucaldas.rickmortyapi.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ucaldas.rickmortyapi.model.Personaje

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickmortyScreen(
    viewModel: RickmortyViewModel,
    onPersonajeClick: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Rick & Morty API") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val currentState = state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = currentState.message)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = viewModel::cargar) {
                            Text("Reintentar")
                        }
                    }
                }
                is UiState.Success -> {
                    Column {
                        Button(
                            onClick = viewModel::cargar,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text("Recargar Lista")
                        }
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(currentState.items, key = { it.id }) { personaje ->
                                ItemPersonaje(
                                    personaje = personaje,
                                    onClick = { onPersonajeClick(personaje.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemPersonaje(personaje: Personaje, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = personaje.image,
                contentDescription = personaje.name,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = personaje.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Estado: ${personaje.status}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun DetallePersonajeScreen(personajeId: Int, viewModel: RickmortyViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state is UiState.Success) {
        val personaje = (state as UiState.Success).items.find { it.id == personajeId }

        personaje?.let { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = item.name,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = item.name, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Especie: ${item.species}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Estado: ${item.status}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}