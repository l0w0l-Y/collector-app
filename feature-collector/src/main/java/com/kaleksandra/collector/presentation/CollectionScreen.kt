package com.kaleksandra.collector.presentation

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.graphics.translationMatrix
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kaleksandra.collector.R
import com.kaleksandra.coredata.network.models.CollectionItem
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.coretheme.Dimen

@Composable
fun CollectionScreen(viewModel: CollectionViewModel = hiltViewModel()) {
    val collections by viewModel.collectionsState.collectAsState()
    CollectionScreen(
        collections,
        {},
        viewModel::onAddCardInCollection,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionScreen(
    collections: List<CollectionResponse>,
    onCardClick: (Long) -> Unit,
    onAddCardInCollection: (Long) -> Unit,
) {
    var selectedCollection by remember { mutableIntStateOf(0) }
    Row {
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
                        modifier = Modifier.combinedClickable(
                            onClick = { onCardClick(it.id) },
                            onLongClick = { onAddCardInCollection(it.id) },
                        )
                    ) {
                        var isLoading by remember { mutableStateOf(false) }
                        AsyncImage(
                            model = it.link,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            colorFilter = if (!it.inCollection) {
                                ColorFilter.tint(
                                    color = Color(0xBF06010B),
                                    blendMode = BlendMode.Darken
                                )
                            } else {
                                null
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(Dimen.radius_10))
                                .fillMaxSize(),
                            onLoading = {
                                isLoading = true
                            },
                            onSuccess = {
                                isLoading = false
                            },
                            onError = {
                                isLoading = false
                            }
                        )
                        if (isLoading) {
                            LoadingAnimation(
                                Modifier
                                    .height(280.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp),
            verticalArrangement = Arrangement.spacedBy(Dimen.padding_16),
        ) {
            items(collections) {
                Column(
                    modifier = Modifier
                        .clickable {
                            selectedCollection = collections.indexOf(it)
                        }
                        .padding(
                            vertical = Dimen.padding_12,
                            horizontal = Dimen.padding_8
                        )
                ) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "${it.cardInCollection} / ${it.cards}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier, size: Dp = 60.dp) {
    val infiniteTransition = rememberInfiniteTransition("RotationTransition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutBack),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "RotationCompletion"
    )
    Box(
        modifier = modifier.background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = rotation
                }
                .height(size),
            painter = painterResource(id = R.drawable.ic_loading),
            contentDescription = "",
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