package com.kaleksandra.collector.presentation.collection.model

sealed class LoadingState
object Loading : LoadingState()
object Success : LoadingState()
object Error: LoadingState()