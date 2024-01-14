package com.kaleksandra.collector.presentation.collection

import android.content.res.Configuration
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kaleksandra.collector.R
import com.kaleksandra.collector.presentation.collection.model.Error
import com.kaleksandra.collector.presentation.collection.model.Loading
import com.kaleksandra.collector.presentation.collection.model.LoadingState
import com.kaleksandra.collector.presentation.collection.model.Success
import com.kaleksandra.coredata.network.models.CollectionItem
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.corenavigation.CreateCollectionDirection
import com.kaleksandra.corenavigation.navigate
import com.kaleksandra.coretheme.Dimen
import com.kaleksandra.coreui.compose.painter

@Composable
fun CollectionScreen(
    navController: NavController,
    viewModel: CollectionViewModel = hiltViewModel(),
) {
    val collections by viewModel.collectionsState.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    CollectionScreen(
        collections,
        loadingState,
        {},
        viewModel::onAddCardInCollection,
        { navController.navigate(CreateCollectionDirection) },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionScreen(
    collections: List<CollectionResponse>,
    loadingState: LoadingState,
    onCardClick: (Long) -> Unit,
    onAddCardInCollection: (Long) -> Unit,
    onAddCollection: () -> Unit,
) {
    var selectedCollection by remember { mutableIntStateOf(0) }
    when (loadingState) {
        Loading, Error -> {
            LoadingLogo()
        }

        Success -> Row {
            if (collections.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .systemBarsPadding(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        horizontal = Dimen.padding_18,
                        vertical = Dimen.padding_18,
                    )
                ) {
                    items(collections[selectedCollection].list) {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            var isLoading by remember { mutableStateOf(false) }
                            Column {
                                AsyncImage(model = it.link,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    colorFilter = if (!it.inCollection) {
                                        ColorFilter.tint(
                                            color = Color(0xBF06010B), blendMode = BlendMode.Darken
                                        )
                                    } else {
                                        null
                                    },
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(Dimen.radius_10))
                                        .fillMaxSize()
                                        .combinedClickable(
                                            onClick = { onCardClick(it.id) },
                                            onLongClick = { onAddCardInCollection(it.id) },
                                        ),
                                    onLoading = {
                                        isLoading = true
                                    },
                                    onSuccess = {
                                        isLoading = false
                                    },
                                    onError = {
                                        isLoading = false
                                    })
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.padding(top = Dimen.padding_4)
                                )
                            }
                            if (isLoading) {
                                LoadingAnimation(
                                    Modifier
                                        .height(200.dp)
                                        .fillMaxWidth()
                                        .background(
                                            Color(0xFFF5F5F5), RoundedCornerShape(Dimen.padding_20)
                                        )
                                )
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.width(60.dp)) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Dimen.padding_16),
                ) {
                    items(collections) {
                        Column(modifier = Modifier
                            .clickable {
                                selectedCollection = collections.indexOf(it)
                            }
                            .padding(
                                vertical = Dimen.padding_12, horizontal = Dimen.padding_8
                            )) {
                            Text(
                                text = it.title, style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = "${it.cardInCollection} / ${it.cards}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
                IconButton(
                    onClick = onAddCollection, modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun LoadingLogo() {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painter(id = R.drawable.ic_glitz),
            contentDescription = null,
            modifier = Modifier.height(164.dp)
        )
        LoadingAnimation(modifier = Modifier.fillMaxSize(), speed = 1000, height = 106.dp)
    }
}

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier, speed: Int = 600, height: Dp = 60.dp) {
    val infiniteTransition = rememberInfiniteTransition("RotationTransition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -20f, targetValue = 20f, animationSpec = infiniteRepeatable(
            animation = tween(speed, easing = EaseInOutBack),
            repeatMode = RepeatMode.Reverse,
        ), label = "RotationCompletion"
    )
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = rotation
                }
                .height(height),
            painter = painterResource(id = R.drawable.ic_loading),
            contentDescription = "",
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun CollectionPreview() {
    val collection = CollectionResponse(
        1, "Nacific 2022", listOf(
            CollectionItem(1, "Banchan", null, "", false),
            CollectionItem(2, "Chanbin", null, "", false),
        ), 4, 6
    )
    //CollectionScreen(collection, {})
}