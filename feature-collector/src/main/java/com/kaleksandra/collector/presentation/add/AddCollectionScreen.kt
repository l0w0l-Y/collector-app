package com.kaleksandra.collector.presentation.add

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AddCollectionScreen(
    navController: NavController,
    viewModel: AddCollectionViewModel = hiltViewModel()
) {
    AddCollectionScreen({})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCollectionScreen(onSaveClick: () -> Unit) {
    var title by remember { mutableStateOf("") }
    TextField(title, { title = it })
}

@Composable
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun CollectionPreview() {
    AddCollectionScreen({})
}