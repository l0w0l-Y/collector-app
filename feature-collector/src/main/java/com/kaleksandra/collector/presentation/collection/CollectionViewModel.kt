package com.kaleksandra.collector.presentation.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleksandra.collector.data.updateCardInCollection
import com.kaleksandra.collector.domain.CollectionInteractor
import com.kaleksandra.collector.domain.models.CollectionModel
import com.kaleksandra.collector.presentation.collection.model.Error
import com.kaleksandra.collector.presentation.collection.model.Loading
import com.kaleksandra.collector.presentation.collection.model.LoadingState
import com.kaleksandra.collector.presentation.collection.model.Success
import com.kaleksandra.coredata.network.doOnError
import com.kaleksandra.coredata.network.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val interactor: CollectionInteractor
) : ViewModel() {
    private var _collectionState = MutableStateFlow<List<CollectionModel>>(emptyList())
    val collectionsState: StateFlow<List<CollectionModel>> = _collectionState

    private var _loadingState = MutableStateFlow<LoadingState>(Loading)
    val loadingState: StateFlow<LoadingState> = _loadingState

    init {
        getAllCollections()
    }

    private fun getAllCollections() {
        viewModelScope.launch {
            _loadingState.emit(Loading)
            interactor.getAllCollection()
                .doOnSuccess {
                    _collectionState.emit(it)
                    _loadingState.emit(Success)
                }
                .doOnError {
                    _loadingState.emit(Error)
                }
        }
    }

    fun onAddCardInCollection(cardId: Long) {
        viewModelScope.launch {
            interactor.addCardInCollection(cardId)
                .doOnSuccess {
                    _collectionState.update { currentState ->
                        currentState.updateCardInCollection(cardId)
                    }
                }
                .doOnError {
                    _loadingState.emit(Error)
                }
        }
    }
}