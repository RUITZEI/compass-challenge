package com.ruitzei.compass.challenge.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tentchCharUiState by viewModel.leftScreenUiState.collectAsStateWithLifecycle()
    val wordCountUiState by viewModel.rightScreenUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeScreenContent(
                title = "Tenth Character",
                state = tentchCharUiState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            VerticalDivider()
            HomeScreenContent(
                title = "Word Count",
                state = wordCountUiState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
        Button(
            onClick = viewModel::onButtonClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Text(text = "Click me")
        }
    }
}

@Composable
fun HomeScreenContent(
    title: String,
    state: HomeState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }

        when (val uiState = state) {
            is HomeState.Error -> {
                item {
                    Text(text = "Error: :${uiState.cause}")
                }
            }

            HomeState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is HomeState.Success -> {
                item {
                    Text(text = uiState.string)
                }
            }

            HomeState.Idle -> {
                // show nothing
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        title = "Tenth Character",
        state = HomeState.Loading,
    )
}