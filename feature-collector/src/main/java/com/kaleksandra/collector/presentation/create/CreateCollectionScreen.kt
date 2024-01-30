package com.kaleksandra.collector.presentation.create

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kaleksandra.collector.R
import com.kaleksandra.collector.domain.models.Group
import com.kaleksandra.collector.domain.models.Member
import com.kaleksandra.collector.presentation.create.model.OnCreateCollectionEvent
import com.kaleksandra.collector.presentation.photo.CameraScreen
import com.kaleksandra.corecommon.ext.event.observe
import com.kaleksandra.coredata.network.models.GroupResponse
import com.kaleksandra.coredata.network.models.MemberResponse
import com.kaleksandra.corenavigation.CollectionDirection
import com.kaleksandra.corenavigation.navigate
import com.kaleksandra.coretheme.Dimen
import com.kaleksandra.coreui.compose.string

@Composable
fun CreateCollectionScreen(
    navController: NavController, viewModel: CreateCollectionViewModel = hiltViewModel()
) {
    val groups by viewModel.groupState.collectAsState()
    val members by viewModel.membersState.collectAsState()
    viewModel.eventChannel.observe {
        when (it) {
            OnCreateCollectionEvent -> {
                navController.navigate(CollectionDirection) {
                    popUpTo(CollectionDirection.path) {
                        inclusive = true
                    }
                }
            }
        }
    }
    CreateCollectionScreen(
        groups,
        members,
        viewModel::getAllMembersGroup,
        viewModel::sendCollection,
        viewModel::onValueChange,
        navController::popBackStack,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateCollectionScreen(
    groups: List<Group>,
    members: List<Member>,
    onGroupSelect: (Long) -> Unit,
    onCreateClick: (Map<Long, Uri>, String, Long) -> Unit,
    onGroupChange: (String) -> Unit,
    onBackClicked: () -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var group by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }
    val isAddImageVisible = !(title == "" || id == 0L)
    val uris = remember { mutableStateMapOf<Long, Uri>() }
    var selectedId by remember { mutableLongStateOf(-1L) }
    val context = LocalContext.current
    var isCameraOpened by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimen.padding_12)
    ) {
        if (isCameraOpened) {
            CameraScreen {
                uris[selectedId] = it
                selectedId = -1
                isCameraOpened = false
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimen.padding_16,
                        vertical = Dimen.padding_8,
                    )
            ) {
                IconButton(
                    onBackClicked,
                    modifier = Modifier.align(CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
                Text(
                    string(id = R.string.top_bar_create_collection),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Center)
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = Dimen.padding_16,
                        vertical = Dimen.padding_8
                    )
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(Dimen.radius_12)
                    )
                    .padding(
                        horizontal = Dimen.padding_20,
                        vertical = Dimen.padding_12
                    )
                    .animateContentSize()
            ) {
                if (!isAddImageVisible) {
                    Text(
                        string(id = R.string.text_field_title_collection),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                BasicTextField(
                    title, {
                        title = it
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.onSurfaceVariant),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Dimen.padding_4)
                        ) {
                            if (title.isEmpty()) {
                                Text(
                                    text = string(id = R.string.text_field_placeholder_collection),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            innerTextField()
                        }
                    },
                    enabled = !isAddImageVisible
                )
                if (title.isNotEmpty()) {
                    if (!isAddImageVisible) {
                        Text(
                            string(id = R.string.text_field_title_group),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = Dimen.padding_12)
                        )
                    }
                    BasicTextField(
                        group, {
                            group = it
                            onGroupChange(it)
                        },
                        textStyle = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.onSurfaceVariant),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Dimen.padding_4)
                            ) {
                                if (group.isEmpty()) {
                                    Text(
                                        text = string(id = R.string.text_field_placeholder_group),
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                                innerTextField()
                            }
                        },
                        enabled = !isAddImageVisible
                    )
                    if (!isAddImageVisible) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Dimen.padding_4),
                        ) {
                            groups.forEach {
                                Chip(
                                    onClick = {
                                        id = it.id
                                        group = it.name
                                        onGroupSelect(it.id)
                                    },
                                    colors = ChipDefaults.chipColors(
                                        backgroundColor = MaterialTheme.colorScheme.background
                                    ),
                                    border = BorderStroke(
                                        ChipDefaults.OutlinedBorderSize,
                                        MaterialTheme.colorScheme.primary
                                    ),
                                ) {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.labelMedium,
                                    )
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = isAddImageVisible,
                enter = slideInVertically(
                    initialOffsetY = {
                        it / 2
                    },
                ),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (members.isNotEmpty()) {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(Dimen.padding_16),
                            horizontalArrangement = Arrangement.spacedBy(Dimen.padding_8),
                            contentPadding = PaddingValues(
                                start = Dimen.padding_16,
                                end = Dimen.padding_16,
                                top = Dimen.padding_4,
                                bottom = Dimen.padding_60
                            )
                        ) {
                            items(members) { item ->
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(Dimen.padding_8),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val isPhotoTaken = uris.contains(item.id)
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (!isPhotoTaken) {
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                } else {
                                                    Color.Transparent
                                                },
                                                RoundedCornerShape(Dimen.padding_20)
                                            )
                                            .height(200.dp)
                                            .width(128.dp)
                                            .clip(RoundedCornerShape(Dimen.padding_20))
                                            .clickable {
                                                isCameraOpened = true
                                                selectedId = item.id
                                            },
                                        contentAlignment = Center
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                if (isPhotoTaken) uris[item.id] else ""
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.clip(
                                                RoundedCornerShape(Dimen.padding_20)
                                            ),
                                            contentScale = ContentScale.FillHeight,
                                        )
                                        if (!isPhotoTaken) {
                                            Icon(Icons.Filled.Add, contentDescription = null)
                                        }
                                    }
                                    Text(
                                        text = item.nameEn ?: item.nameRu ?: item.nameKor ?: "",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        onClick = { onCreateClick(uris, title, id) },
                        enabled = isAddImageVisible,
                        modifier = Modifier
                            .padding(bottom = Dimen.padding_8)
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            text = string(id = R.string.button_create_collection),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun CollectionPreview() {
    //CreateCollectionScreen(listOf()) { _, _ -> }
}