package com.kaleksandra.collector.presentation.create

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kaleksandra.collector.R
import com.kaleksandra.collector.presentation.create.model.OnCreateCollectionEvent
import com.kaleksandra.corecommon.ext.observe
import com.kaleksandra.coredata.network.models.GroupResponse
import com.kaleksandra.corenavigation.CollectionDirection
import com.kaleksandra.corenavigation.navigate
import com.kaleksandra.coreui.compose.string

@Composable
fun CreateCollectionScreen(
    navController: NavController,
    viewModel: CreateCollectionViewModel = hiltViewModel()
) {
    val groups by viewModel.groupState.collectAsState()
    viewModel.eventChannel.observe {
        when (it) {
            OnCreateCollectionEvent -> {
                navController.navigate(CollectionDirection)
            }
        }
    }
    CreateCollectionScreen(groups, viewModel::createCollection)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCollectionScreen(
    groups: List<GroupResponse>,
    onCreateClick: (String, Long) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }
    var groupName by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    Column {
        TextField(title, { title = it })
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }) {
            TextField(
                value = groupName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier.menuAnchor()
            )
            DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                groups.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            id = it.id
                            groupName = it.name
                            isExpanded = false
                        })
                }
            }
        }
        Button(onClick = { onCreateClick(title, id) }) {
            Text(text = string(id = R.string.button_create_collection))
        }
    }
}

@Composable
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun CollectionPreview() {
    CreateCollectionScreen(listOf()) { _, _ -> }
}