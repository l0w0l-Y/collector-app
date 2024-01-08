package com.kaleksandra.collector.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleksandra.collector.domain.CollectionInteractor
import com.kaleksandra.coredata.network.doOnSuccess
import com.kaleksandra.coredata.network.models.CollectionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    val interactor: CollectionInteractor
) : ViewModel() {
    private var _collectionState = MutableStateFlow<List<CollectionResponse>>(emptyList())
    val collectionsState: StateFlow<List<CollectionResponse>> = _collectionState

    init {
        getAllCollections()
    }

    private fun getAllCollections() {
        viewModelScope.launch {
            interactor.getAllCollection().doOnSuccess {
                _collectionState.emit(it)
            }
        }
    }

    private fun getCollection(id: Long) {
        viewModelScope.launch {
            interactor.getCollection(id).doOnSuccess {
                //_collectionState.emit(it)
            }
        }
    }

    fun onAddCardInCollection(cardId: Long) {
        viewModelScope.launch {
            interactor.addCardInCollection(cardId).doOnSuccess {
                _collectionState.update { currentState ->
                    currentState.map { collection ->
                        if (cardId in collection.list.map { it.id }) {
                            collection.copy(
                                list = collection.list.map {
                                    if (it.id == cardId) {
                                        it.copy(inCollection = !it.inCollection)
                                    } else {
                                        it
                                    }
                                }
                            )
                        } else {
                            collection
                        }
                    }
                }
            }
        }
    }
}